package com.alganaut.hominid.registry.entity.custom;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.effect.HominidEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class Mellified extends Monster {
    public int idleAnimationTimeout = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();

    public Mellified(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.DROWNED_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.DROWNED_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DROWNED_DEATH;
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
        this.goalSelector.addGoal(1, new FollowPlayerGoal(this, 1.0, 1.0F, 10.0F));
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
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        spawnHoneyParticles(this);
        if (source.getEntity() != null) {
            healZombies();
        }

        return hurt;
    }

    private void spawnHoneyParticles(LivingEntity entity) {
        if(!this.level().isClientSide){
            ServerLevel serverLevel = (ServerLevel) entity.level();

            for (int i = 0; i < 40; i++) {
                double offsetX = (this.random.nextDouble() - 0.5) * 0.8;
                double offsetY = this.random.nextDouble() * 0.5 + 0.5;
                double offsetZ = (this.random.nextDouble() - 0.5) * 0.8;
                double velocityX = (this.random.nextDouble() - 0.5) * 0.2;
                double velocityY = this.random.nextDouble() * 0.3 + 0.2;
                double velocityZ = (this.random.nextDouble() - 0.5) * 0.2;

                serverLevel.sendParticles(ParticleTypes.LANDING_HONEY,
                        entity.getX() + offsetX, entity.getY() + offsetY, entity.getZ() + offsetZ,
                        1, velocityX, velocityY, velocityZ, 0);
            }
        }
    }


    private void healZombies() {
        if (this.level() == null) {
            return;
        }

        List<Monster> nearbyZombies = this.level().getEntitiesOfClass(
                Monster.class, this.getBoundingBox().inflate(5));

        for (Monster zombie : nearbyZombies) {
            if (!zombie.equals(this)) {
                zombie.addEffect(new MobEffectInstance(Holder.direct(HominidEffects.HONEYED.get()), 200, 1));
            }
        }
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


    public class FollowPlayerGoal extends Goal {
        private final Monster entity;
        private final double speedModifier;
        private final float minDistance;
        private final float maxDistance;
        private Player targetPlayer;

        public FollowPlayerGoal(Monster entity, double speedModifier, float minDistance, float maxDistance) {
            this.entity = entity;
            this.speedModifier = speedModifier;
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {

            if (this.entity.level() == null || this.entity.level().isClientSide) {
                return false;
            }

            this.targetPlayer = this.entity.level().getNearestPlayer(this.entity, maxDistance);

            return this.targetPlayer != null && !targetPlayer.isCreative() && this.targetPlayer.distanceTo(this.entity) >= minDistance && this.targetPlayer.distanceTo(this.entity) <= maxDistance;
        }

        @Override
        public boolean canContinueToUse() {
            return this.targetPlayer != null && this.targetPlayer.isAlive() && this.targetPlayer.distanceTo(this.entity) > minDistance && this.targetPlayer.distanceTo(this.entity) <= maxDistance;
        }

        @Override
        public void start() {
            if (this.targetPlayer != null) {
                PathNavigation navigation = this.entity.getNavigation();
                if (navigation != null) {
                    navigation.moveTo(this.targetPlayer, this.speedModifier);
                }
            }
        }

        @Override
        public void stop() {
            this.targetPlayer = null;
        }

        @Override
        public void tick() {
            if (this.targetPlayer != null) {
                double distance = this.targetPlayer.distanceTo(this.entity);
                if (distance > minDistance && distance <= maxDistance) {
                    PathNavigation navigation = this.entity.getNavigation();
                    if (navigation != null) {
                        navigation.moveTo(this.targetPlayer, this.speedModifier);
                    }
                }
            }
        }
    }
}