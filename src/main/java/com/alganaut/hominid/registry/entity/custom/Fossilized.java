package com.alganaut.hominid.registry.entity.custom;

import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Nullable;

public class Fossilized extends Monster {
    public int idleAnimationTimeout = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState throwAnimationState = new AnimationState();
    public int attackState;
    private static final EntityDataAccessor<Integer> BRUSH_COOLDOWN = SynchedEntityData.defineId(Fossilized.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_BEEN_BRUSHED = SynchedEntityData.defineId(Fossilized.class, EntityDataSerializers.BOOLEAN);
    private static final int COOLDOWN_TICKS = 200;
    private static final double DROP_CHANCE = 0.1;

    public Fossilized(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 35.0)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.STONE_PLACE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.STONE_FALL;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.STONE_BREAK;
    }


    @Nullable
    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.01F;
    }

    protected boolean isSunSensitive() {
        return false;
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FossilizedRangedAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    private void setupAnimationStates() {
        if (this.getDeltaMovement().horizontalDistance() <= 0.001F) {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = 120;
                this.idleAnimationState.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
        } else {
            this.idleAnimationTimeout = 0;
            this.idleAnimationState.stop();
        }
    }
    @Override
    public void tick() {
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
        super.tick();

        if (this.level() == null || this.level().isClientSide) {
            return;
        }

        if (isMoving()) {
            walkAnimationState.startIfStopped(tickCount);
            idleAnimationState.stop();
        } else {
            idleAnimationState.startIfStopped(tickCount);
            walkAnimationState.stop();
        }
        if (this.entityData.get(BRUSH_COOLDOWN) > 0) {
            this.entityData.set(BRUSH_COOLDOWN, this.entityData.get(BRUSH_COOLDOWN) - 1);
        }
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public boolean isInvertedHealAndHarm() {
        return true;
    }


    public class FossilizedRangedAttackGoal extends Goal {
        private final Fossilized fossilized;
        private int attackCooldown;
        private int animationTimer;

        public FossilizedRangedAttackGoal(Fossilized fossilized) {
            this.fossilized = fossilized;
            this.attackCooldown = 0;
            this.animationTimer = 0;
            this.fossilized.attackState = 0;
        }

        @Override
        public boolean canUse() {
            return this.fossilized.getTarget() != null;
        }

        @Override
        public void tick() {
            LivingEntity target = this.fossilized.getTarget();
            if (target == null) return;
            this.fossilized.getLookControl().setLookAt(target, 30.0F, 30.0F);

            if (this.attackCooldown > 0) {
                this.attackCooldown--;
                return;
            }

            switch (attackState) {
                case 0:
                    this.fossilized.attackState = 1;
                    this.animationTimer = 18;
                    break;

                case 1:
                    if (animationTimer > 0) {
                        animationTimer--;
                    } else {
                        this.fossilized.attackState = 2;
                        this.animationTimer = 18;
                        if (!this.fossilized.level().isClientSide) {
                            this.fossilized.level().broadcastEntityEvent(this.fossilized, (byte) 70);
                        }
                        this.fossilized.getNavigation().stop();
                        this.fossilized.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    }
                    break;

                case 2:
                    if (animationTimer > 0) {
                        animationTimer--;
                    } else {
                        this.fossilized.attackState = 3;
                        this.fossilized.getLookControl().setLookAt(target, 30.0F, 30.0F);
                        this.fossilized.getNavigation().stop();
                        if (!this.fossilized.level().isClientSide) {
                            this.fossilized.level().broadcastEntityEvent(this.fossilized, (byte) 90);
                        }
                        this.throwRock(target);
                        this.attackCooldown = 20;
                        this.fossilized.attackState = 0;
                    }
                    break;
            }
        }

        private void throwRock(LivingEntity target) {
            Level level = this.fossilized.level();
            FossilizedRock rock = new FossilizedRock(this.fossilized.level(), this.fossilized);

            double dx = target.getX() - this.fossilized.getX();
            double dy = target.getY() - this.fossilized.getEyeY() + 0.5;
            double dz = target.getZ() - this.fossilized.getZ();

            rock.setPos(this.fossilized.getX(), this.fossilized.getEyeY(), this.fossilized.getZ());
            rock.setOwner(this.fossilized);
            rock.shoot(dx, dy, dz, 2.5F, 0.2F);

            rock.setInvisible(false);

            if (!level.isClientSide()) {
                level.addFreshEntity(rock);
            }
        }
    }

    @Override
    public void handleEntityEvent(byte state) {
        if (state == 70) {
            this.throwAnimationState.stop();
            this.idleAnimationState.stop();
            this.throwAnimationState.startIfStopped(this.tickCount);
        }
        if (state == 90) {
            this.throwAnimationState.stop();
        }
        super.handleEntityEvent(state);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BRUSH_COOLDOWN, 0);
        builder.define(HAS_BEEN_BRUSHED, false);
    }
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.canPerformAction(ItemAbilities.BRUSH_BRUSH)) {
            if (this.entityData.get(HAS_BEEN_BRUSHED)) {
                return InteractionResult.PASS;
            }

            if (this.brushOffScute()) {
                this.entityData.set(HAS_BEEN_BRUSHED, true);
                itemstack.hurtAndBreak(32, player, getSlotForHand(hand));
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }

        return super.mobInteract(player, hand);
    }

    public boolean brushOffScute() {
        this.spawnAtLocation(new ItemStack(HominidItems.REMAINS_SMITHING_TEMPLATE.get()));
        this.gameEvent(GameEvent.ENTITY_INTERACT);
        this.playSound(SoundEvents.BRUSH_GENERIC);
        return true;
    }

    private void setBrushCooldown() {
        this.entityData.set(BRUSH_COOLDOWN, COOLDOWN_TICKS);
    }

    private void dropLoot() {
        ItemStack reward = new ItemStack(HominidItems.REMAINS_SMITHING_TEMPLATE.get());
        this.spawnAtLocation(reward);
    }
}
