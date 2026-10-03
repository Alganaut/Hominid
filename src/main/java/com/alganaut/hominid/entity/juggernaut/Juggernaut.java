package com.alganaut.hominid.entity.juggernaut;

import com.alganaut.hominid.entity.animation.IdleAnimationController;
import com.alganaut.hominid.entity.goal.AttackTurtleEggGoal;
import com.alganaut.hominid.registry.HominidEntityCreator;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

public class Juggernaut extends Monster {
    private static final byte ATTACK_ANIMATION_EVENT = 100;
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private final IdleAnimationController idleAnimationController = new IdleAnimationController(250);

    public Juggernaut(Level level) {
        super(HominidEntityCreator.JUGGERNAUT.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(4, new AttackTurtleEggGoal(this, 1.0, 3, 1.14));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false));
        goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
    }
    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    public void tick() {
        if (level().isClientSide()) {
            idleAnimationController.tick(this, idleAnimationState);
        }
        super.tick();
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
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public boolean isInvertedHealAndHarm() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.ARROW) || source.is(DamageTypes.MOB_PROJECTILE)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void handleEntityEvent(byte state) {
        if (state == ATTACK_ANIMATION_EVENT) {
            attackAnimationState.stop();
            attackAnimationState.startIfStopped(tickCount);
        } else {
            super.handleEntityEvent(state);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if(!level().isClientSide){
            level().broadcastEntityEvent(this, ATTACK_ANIMATION_EVENT);
        }
        return super.doHurtTarget(entity);
    }

}
