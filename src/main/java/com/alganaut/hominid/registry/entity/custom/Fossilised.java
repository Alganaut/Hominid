package com.alganaut.hominid.registry.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;

public class Fossilised extends Monster {
    public int idleAnimationTimeout = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState throwAnimationState = new AnimationState();
    public int attackState;

    public Fossilised(EntityType<? extends Monster> entityType, Level level) {
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
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
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
        this.goalSelector.addGoal(1, new FossilisedRangedAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 120;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
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
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public boolean isInvertedHealAndHarm() {
        return true;
    }


    public class FossilisedRangedAttackGoal extends Goal {
        private final Fossilised fossilised;
        private int attackCooldown;
        private int animationTimer;

        public FossilisedRangedAttackGoal(Fossilised fossilised) {
            this.fossilised = fossilised;
            this.attackCooldown = 0;
            this.animationTimer = 0;
            this.fossilised.attackState = 0;
        }

        @Override
        public boolean canUse() {
            return this.fossilised.getTarget() != null;
        }

        @Override
        public void tick() {
            LivingEntity target = this.fossilised.getTarget();
            if (target == null) return;
            this.fossilised.getLookControl().setLookAt(target, 30.0F, 30.0F);

            if (this.attackCooldown > 0) {
                this.attackCooldown--;
                return;
            }

            switch (attackState) {
                case 0:
                    this.fossilised.attackState = 1;
                    this.animationTimer = 20;
                    break;

                case 1:
                    if (animationTimer > 0) {
                        animationTimer--;
                    } else {
                        this.fossilised.attackState = 2;
                        this.animationTimer = 19;
                        if (!this.fossilised.level().isClientSide) {
                            this.fossilised.level().broadcastEntityEvent(this.fossilised, (byte) 80);
                            this.fossilised.level().broadcastEntityEvent(this.fossilised, (byte) 70);
                        }
                        this.fossilised.getNavigation().stop();
                        this.fossilised.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    }
                    break;

                case 2:
                    if (animationTimer > 0) {
                        animationTimer--;
                    } else {
                        this.fossilised.attackState = 3;
                        this.fossilised.getLookControl().setLookAt(target, 30.0F, 30.0F);
                        this.fossilised.getNavigation().stop();
                        if (!this.fossilised.level().isClientSide) {
                            this.fossilised.level().broadcastEntityEvent(this.fossilised, (byte) 90);
                        }
                        this.throwRock(target);
                        this.attackCooldown = 20;
                        this.fossilised.attackState = 0;
                    }
                    break;
            }
        }

        private void throwRock(LivingEntity target) {
            Level level = this.fossilised.level();
            FossilisedRock rock = new FossilisedRock(this.fossilised.level(), this.fossilised);

            double dx = target.getX() - this.fossilised.getX();
            double dy = target.getY() - this.fossilised.getEyeY() + 0.5;
            double dz = target.getZ() - this.fossilised.getZ();

            rock.setPos(this.fossilised.getX(), this.fossilised.getEyeY(), this.fossilised.getZ());
            rock.setOwner(this.fossilised);
            rock.shoot(dx, dy, dz, 2F, 0.2F);

            rock.setInvisible(false);

            if (!level.isClientSide()) {
                level.addFreshEntity(rock);
            }
        }
    }

    @Override
    public void handleEntityEvent(byte state) {
        if (state == 70){
            this.throwAnimationState.stop();
            this.idleAnimationState.stop();
            this.throwAnimationState.startIfStopped(this.tickCount);
            System.out.println("throwing anim");
        }
        if (state == 90){
            this.throwAnimationState.stop();
        }
        else super.handleEntityEvent(state);
    }
}
