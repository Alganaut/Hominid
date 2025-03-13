package com.alganaut.hominid.registry.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Incendiary extends Monster {
    private static final EntityDataAccessor<Boolean> IGNITING =
            SynchedEntityData.defineId(Incendiary.class, EntityDataSerializers.BOOLEAN);
    public final Set<UUID> ignitedCreepers = new HashSet<>();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState igniteAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private boolean isEnraged = false;
    private static final double NORMAL_SPEED = 0.2;
    private static final double ENRAGED_SPEED = 0.35;
    private static final int FIRE_DURATION = 500;
    private static final int IGNITION_DELAY = 60;
    private int ignitionTimer = 0;

    public Incendiary(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MAX_HEALTH, 15.0)
                .add(Attributes.MOVEMENT_SPEED, NORMAL_SPEED)
                .add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IGNITING, false);
    }

    public void setIgniting(boolean attacking) {
        this.entityData.set(IGNITING, attacking);
    }

    public boolean isIgniting() {
        return this.entityData.get(IGNITING);
    }

    public void aiStep() {
        if (this.isAlive()) {
            boolean flag = this.isSunSensitive() && this.isSunBurnTick();
            if (flag) {
                ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
                if (!itemstack.isEmpty()) {
                    if (itemstack.isDamageableItem()) {
                        Item item = itemstack.getItem();
                        itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                        if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                            this.onEquippedItemBroken(item, EquipmentSlot.HEAD);
                            this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag) {
                    this.igniteForSeconds(8.0F);
                }
            }
        }

        super.aiStep();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.getTarget();

        if (target != null) {
            if (!isIgniting()) {
                ignitionTimer = IGNITION_DELAY;
                setIgniting(true);
                if(!level().isClientSide){
                    this.level().broadcastEntityEvent(this, (byte) 70);
                }
            }
        } else {
            if (isEnraged) {
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(NORMAL_SPEED);
                isEnraged = false;
            }

            if (isIgniting()) {
                setIgniting(false);
                ignitionTimer = 0;
            }
        }

        if (isIgniting()) {
            if(!level().isClientSide){
                this.level().broadcastEntityEvent(this, (byte) 70);
            }
            if (ignitionTimer > 0) {
                ignitionTimer--;
                if (ignitionTimer == 0) {
                    if (!isEnraged) {
                        isEnraged = true;
                        this.setRemainingFireTicks(FIRE_DURATION);
                        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(ENRAGED_SPEED);
                        setIgniting(false);
                    }
                }
            }
        }
        igniteNearbyMobs();
    }

    private void igniteNearbyMobs() {
        if (this.isOnFire() && this.isAggressive()) {
            List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(
                    LivingEntity.class,
                    this.getBoundingBox().inflate(0.5),
                    entity -> entity instanceof Creeper
            );

            for (LivingEntity entity : nearbyEntities) {
                if (entity instanceof Creeper creeper) {
                    creeper.setRemainingFireTicks(200);
                    creeper.ignite();
                    ignitedCreepers.add(creeper.getUUID());
                }
            }
        }
        if (this.isOnFire() && this.isAggressive()) {
            List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(
                    LivingEntity.class,
                    this.getBoundingBox().inflate(0.5),
                    entity -> entity != this
            );

            for (LivingEntity entity : nearbyEntities) {
                entity.setRemainingFireTicks(200);
                if (entity instanceof Creeper creeper) {
                    creeper.setRemainingFireTicks(200);
                    creeper.ignite();
                    ignitedCreepers.add(creeper.getUUID());
                }
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        fallDistance = 0;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public boolean isInvertedHealAndHarm() {
        return true;
    }

    @Override
    public void handleEntityEvent(byte state) {
        if (state == 70) this.igniteAnimationState.startIfStopped(this.tickCount);
        if (state == 60) this.attackAnimationState.startIfStopped(this.tickCount);
        else super.handleEntityEvent(state);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if(!level().isClientSide){
            this.level().broadcastEntityEvent(this, (byte) 60);
        }
        return super.doHurtTarget(entity);
    }
}