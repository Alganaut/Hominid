package com.alganaut.hominid.registry.entity.custom;

import com.alganaut.hominid.registry.effect.HominidEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class Siren extends Monster {
    public int idleAnimationTimeout = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    public final AnimationState grabAnimationState = new AnimationState();
    public final AnimationState drownAnimationState = new AnimationState();
    private boolean wasHurt = false;
    public float grabProgress = 0;
    public float prevGrabProgress = 0;
    private int passengerTimer = 0;

    public Siren(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.1F, 0.1F, true);
        this.navigation = new WaterBoundPathNavigation(this, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 25.0)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 1.0);
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
    public boolean canRiderInteract() {
        return true;
    }

    public boolean shouldRiderSit() {
        return false;
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
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Pig.class, false){
            public boolean canUse() { return !Siren.this.isDrowning() && super.canUse(); }
        });
        //this.goalSelector.addGoal(1, new TargetAndDragGoal(this){
        //    public boolean canUse() { return !Siren.this.isDrowning() && super.canUse(); }
        //});
        this.goalSelector.addGoal(1, new GazeEffectGoal(this, 20));
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new FlopGoal(this, 1D, 5));
        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.0, 10));
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 120;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void tick() {
        this.prevGrabProgress = grabProgress;
        final boolean grabbing = !this.getPassengers().isEmpty();
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
        super.tick();

        if (this.level() == null || this.level().isClientSide) {
            return;
        }


        if (!this.level().isClientSide) {
            if (this.isAlive() && this.getTarget() != null) {
                final float f1 = this.getYRot() * Mth.DEG_TO_RAD;
                this.setDeltaMovement(this.getDeltaMovement().add(-Mth.sin(f1) * 0.02F, 0.0D, Mth.cos(f1) * 0.02F));
                if (this.distanceTo(this.getTarget()) < 3.5F && this.hasLineOfSight(this.getTarget())) {
                    boolean flag = this.getTarget().isBlocking();
                    if (!flag) {
                        if (this.getTarget().getBbWidth() < this.getBbWidth() && this.getPassengers().isEmpty() && !this.getTarget().isShiftKeyDown()) {
                            this.getTarget().startRiding(this, true);
                        }
                    }else {
                        this.getTarget().hurt(this.damageSources().mobAttack(this), (float) this.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue());
                    }

                }
            }
            if (this.isAlive() && this.getTarget() != null && this.isInWater()) {
                if (this.getTarget().getVehicle() != null && this.getTarget().getVehicle() == this) {
                    if(!this.level().isClientSide){
                        this.level().broadcastEntityEvent(this, (byte) 82);
                    }
                    Vec3 lookVec = this.getLookAngle().normalize();

                    BlockPos behindPos = BlockPos.containing(
                            this.getX() - lookVec.x,
                            this.getY(),
                            this.getZ() - lookVec.z
                    );

                    BlockState behindBlock = this.level().getBlockState(behindPos);
                    if (behindBlock.getBlock() == Blocks.WATER) {
                        Vec3 backwardDown = new Vec3(-lookVec.x * 0.2, -0.1, -lookVec.z * 0.2);
                        this.setDeltaMovement(backwardDown);
                        this.hurtMarked = true;
                    }
                }
            }
        }

        if (grabbing) {
            if (this.grabProgress < 100F)
                this.grabProgress++;
        } else {
            if (this.grabProgress > 0F)
                this.grabProgress--;
        }
        if (passengerTimer > 0 && this.getPassengers().isEmpty()) {
            passengerTimer = 0;
        }
    }

    @javax.annotation.Nullable
    public LivingEntity getControllingPassenger() {
        return null;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if(!level().isClientSide){
            this.level().broadcastEntityEvent(this, (byte) 82);
        }
        entity.startRiding(this);
        return super.doHurtTarget(entity);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public boolean isInvertedHealAndHarm() {
        return true;
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

        if(this.isInWater()){
            if (this.getDeltaMovement().y > 0) {
                if(!level().isClientSide){
                    this.level().broadcastEntityEvent(this, (byte) 70);
                    this.level().broadcastEntityEvent(this, (byte) 78);
                }
            }else{
                if(!level().isClientSide){
                    this.level().broadcastEntityEvent(this, (byte) 73);
                    this.level().broadcastEntityEvent(this, (byte) 75);
                }
            }
        }else if(!this.isInWater()){
            if(!this.level().isClientSide){
                this.level().broadcastEntityEvent(this, (byte) 80);
            }
        }

        if (isDrowning()) {
            this.getNavigation().stop();
            this.setDeltaMovement(0, -0.1, 0);
        } else if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4000000059604645, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.makeSound(SoundEvents.SALMON_FLOP);
        }

        super.aiStep();
    }

    public void positionRider(Entity passenger, Entity.MoveFunction moveFunc) {
        if (!this.getPassengers().isEmpty()) {
            this.yBodyRot = Mth.wrapDegrees(this.getYRot() - 180F);
        }
        if (this.hasPassenger(passenger)) {
            passenger.setPos(this.getX() - 1, this.getY(), this.getZ() - 1);
            passengerTimer++;
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        wasHurt = true;
        if (source.getEntity() instanceof Player player) {
            player.removeEffect(HominidEffects.PARANOIA);
        }
        if (this.hasPassenger(source.getEntity()) && source.getDirectEntity() instanceof AbstractArrow) {
            this.removePassenger(source.getEntity());
        }
        return super.hurt(source, amount);
    }

    public class FloatAtSurfaceGoal extends Goal {
        private final Mob mob;

        public FloatAtSurfaceGoal(Mob mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return mob.isInWater() && !mob.isPassenger();
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        @Override
        public void tick() {
            Vec3 motion = mob.getDeltaMovement();

            mob.setDeltaMovement(motion.x, 0.0, motion.z);

            BlockPos pos = mob.blockPosition();
            double waterSurfaceY = level().getSeaLevel();

            if (mob.getY() > waterSurfaceY - 2 || mob.getY() < waterSurfaceY) {
                mob.setPos(mob.getX(), waterSurfaceY - 2, mob.getZ());
            }

            mob.getNavigation().stop();
        }
    }

    public class GazeEffectGoal extends Goal {
        private final Mob entity;
        private final int effectDuration;
        private Player currentTarget;

        public GazeEffectGoal(Mob entity, int durationTicks) {
            this.entity = entity;
            this.effectDuration = durationTicks;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            wasHurt = false;
            List<Player> players = entity.level().getEntitiesOfClass(Player.class, entity.getBoundingBox().inflate(10.0));
            for (Player player : players) {
                if (isPlayerLookingAt(player, entity, 0.05f) && !player.isCreative()) {
                    currentTarget = player;
                    return true;
                }
            }
            currentTarget = null;
            return false;
        }

        @Override
        public void tick() {
            if (currentTarget == null || !currentTarget.isAlive() || wasHurt) return;

            if (isPlayerLookingAt(currentTarget, entity, 0.05f)) {
                MobEffectInstance current = currentTarget.getEffect(HominidEffects.PARANOIA);

                if (current == null || current.getDuration() < 3) {
                    currentTarget.addEffect(new MobEffectInstance(HominidEffects.PARANOIA, 5, 0, false, true));
                }
            } else {
                currentTarget.removeEffect(HominidEffects.PARANOIA);
            }
        }
        @Override
        public void stop() {
            if (currentTarget != null) {
                currentTarget.removeEffect(HominidEffects.PARANOIA);
                currentTarget = null;
            }
        }

        private boolean isPlayerLookingAt(Player player, Entity target, float tolerance) {
            Vec3 viewVec = player.getViewVector(1.0F).normalize();
            Vec3 toEntity = target.position().subtract(player.position()).normalize();
            double dot = viewVec.dot(toEntity);
            return dot > 1.0 - tolerance;
        }
    }

    public static class FlopGoal extends Goal{
        private final Mob mob;
        private final double jumpVelocity;
        private final int cooldownTicks;
        private int jumpCooldown;

        public FlopGoal(Mob mob, double jumpVelocity, int cooldownTicks) {
            this.mob = mob;
            this.jumpVelocity = jumpVelocity;
            this.cooldownTicks = cooldownTicks;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return mob.onGround() && !mob.isInWaterOrBubble() && jumpCooldown <= 0;
        }

        @Override
        public void start() {
            Vec3 current = mob.getDeltaMovement();
            mob.setDeltaMovement(current.x, jumpVelocity, current.z);
            mob.hasImpulse = true;
            jumpCooldown = cooldownTicks;
            if(!mob.level().isClientSide){
                mob.level().broadcastEntityEvent(mob, (byte) 80);
            }
        }

        @Override
        public void tick() {
            if (jumpCooldown > 0) {
                jumpCooldown--;
            }
            if(!mob.level().isClientSide){
                mob.level().broadcastEntityEvent(mob, (byte) 80);
            }
        }

        @Override
        public void stop(){
            if(!mob.level().isClientSide){
                mob.level().broadcastEntityEvent(mob, (byte) 85);
            }
        }

    }


    public boolean isDrowning() {
        return !this.canBreatheUnderwater() && this.isInWaterRainOrBubble() && this.getHealth() < this.getMaxHealth() * 0.25f;
    }

    @Override
    public void handleEntityEvent(byte state) {
        if (state == 73){
            this.flopAnimationState.stop();
        }
        if (state == 75){
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
        if (state == 78){
            this.idleAnimationState.stop();
        }
        if (state == 85){
            this.flopAnimationState.stop();
        }
        if (state == 80){
            this.flopAnimationState.startIfStopped(this.tickCount);
        }
        if (state == 82){
            this.grabAnimationState.startIfStopped(this.tickCount);
        }
        if (state == 83){
            this.grabAnimationState.stop();
        }else{
            super.handleEntityEvent(state);
        }
    }
}
