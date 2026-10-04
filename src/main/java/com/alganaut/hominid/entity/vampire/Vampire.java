package com.alganaut.hominid.entity.vampire;

import com.alganaut.hominid.entity.animation.IdleAnimationController;
import com.alganaut.hominid.registry.sound.HominidSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

public class Vampire extends Monster {
    private static final byte ATTACK_ANIMATION_EVENT = 100;
    static final byte DIE_ANIMATION_EVENT = 101;
    static final byte STOP_BURNING_ANIMATION_EVENT = 99;
    private final IdleAnimationController idleAnimationController = new IdleAnimationController(120);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState dieAnimationState = new AnimationState();

    public Vampire(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.FOLLOW_RANGE, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 12.0);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return HominidSounds.VAMPIRE_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return HominidSounds.VAMPIRE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return HominidSounds.VAMPIRE_DEATH.get();
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false));
        targetSelector.addGoal(2, new TargetDamagedEntityGoal(this));
        targetSelector.addGoal(2, new BurnInSunGoal(this));
        targetSelector.addGoal(2, new FollowPlayerGoal(this) {
            @Override
            public boolean canUse() {
                return !Vampire.this.isAggressive() && super.canUse();
            }
        });
        goalSelector.addGoal(9, new AvoidEntityGoal<>(this, Player.class, 6.0F, 1.0, 1.0) {
            @Override
            public boolean canUse() {
                return !Vampire.this.isAggressive() && super.canUse();
            }
        });
    }

    @Override
    public void tick() {
        if (level().isClientSide()) {
            idleAnimationController.tick(this, idleAnimationState);
        }
        super.tick();
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (!level().isClientSide) {
            level().broadcastEntityEvent(this, ATTACK_ANIMATION_EVENT);
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public void setRemainingFireTicks(int remainingFireTicks) {
        if (remainingFireTicks == 0 && !level().isClientSide) {
            level().broadcastEntityEvent(this, STOP_BURNING_ANIMATION_EVENT);
        }
        super.setRemainingFireTicks(remainingFireTicks);
    }

    @Override
    public boolean isInvertedHealAndHarm() {
        return true;
    }

    void dieAndPerish() {
        if (!isAlive()) {
            return;
        }

        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SMOKE, getX(), getY() + 1.0, getZ(), 20, 0.5, 0.5, 0.5, 0.1);
        }
        scream();
    }

    @Override
    public void handleEntityEvent(byte state) {
        if (state == ATTACK_ANIMATION_EVENT) {
            attackAnimationState.stop();
            attackAnimationState.startIfStopped(tickCount);
        }
        if (state == DIE_ANIMATION_EVENT) {
            dieAnimationState.stop();
            dieAnimationState.startIfStopped(tickCount);
        }
        if (state == STOP_BURNING_ANIMATION_EVENT) {
            dieAnimationState.stop();
        }
        super.handleEntityEvent(state);
    }

    public void scream() {
        level().playSound(null, getX(), getY(), getZ(), HominidSounds.VAMPIRE_SCREAM.get(), getSoundSource(), 0.5F, 1.0F);
    }
}