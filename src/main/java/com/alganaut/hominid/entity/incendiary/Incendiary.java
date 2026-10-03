package com.alganaut.hominid.entity.incendiary;

import com.alganaut.hominid.entity.animation.IdleAnimationController;
import com.alganaut.hominid.entity.behavior.SunlightBurning;
import com.alganaut.hominid.entity.goal.AttackTurtleEggGoal;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Incendiary extends Monster {
    private static final EntityDataAccessor<Boolean> IGNITING =
            SynchedEntityData.defineId(Incendiary.class, EntityDataSerializers.BOOLEAN);
    public final Set<UUID> ignitedCreepers = new HashSet<>();
    private static final byte ATTACK_ANIMATION_EVENT = 100;
    private static final byte IGNITE_ANIMATION_EVENT = 70;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState igniteAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private static final double NORMAL_SPEED = 0.2;
    private static final double ENRAGED_SPEED = 0.35;
    private static final int FIRE_DURATION = 500;
    private static final int IGNITION_DELAY = 60;
    private int ignitionTimer = 0;
    private final IdleAnimationController idleAnimationController = new IdleAnimationController(120);
    private IncendiaryState state = IncendiaryState.IDLE;
    private enum IncendiaryState {
        IDLE,
        IGNITING,
        ENRAGED
    }

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


    public boolean isIgniting() {
        return entityData.get(IGNITING);
    }

    //todo fix goal pursuit  
    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(4, new AttackTurtleEggGoal(this, 1.0, 3));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, true));
        goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    public void tick() {
        if (level().isClientSide()) {
            idleAnimationController.tick(this, idleAnimationState);
            if (!isIgniting()) {
                igniteAnimationState.stop();
            }
        }
        switch (state) {
            case IDLE -> idling();
            case IGNITING ->  igniting();
            case ENRAGED -> raging();
        }
        igniteNearbyMobs();
        super.tick();
    }

    private void idling() {
        LivingEntity target = getTarget();
        if (target == null) {
            return;
        }

        setIgnition();
        if(!level().isClientSide){
            level().broadcastEntityEvent(this, IGNITE_ANIMATION_EVENT);
        }
    }

    private void igniting() {
        if(!level().isClientSide){
            level().broadcastEntityEvent(this, IGNITE_ANIMATION_EVENT);
        }
        if (getTarget() == null) {
           setIdle();
           return;
        }
        if (ignitionTimer != 0) {
            ignitionTimer--;
            return;
        }
        setEnraged();
    }

    private void raging() {
        if (getTarget() != null) {
              return;
        }
        setIdle();

    }

    private void setIdle() {
        state = IncendiaryState.IDLE;
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(NORMAL_SPEED);
        ignitionTimer = 0;
        entityData.set(IGNITING, false);
    }

    private void setIgnition() {
        state = IncendiaryState.IGNITING;
        ignitionTimer = IGNITION_DELAY;
        // is attacking
        entityData.set(IGNITING, true);
    }

    private void setEnraged() {
        state = IncendiaryState.ENRAGED;
        setRemainingFireTicks(FIRE_DURATION);
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(ENRAGED_SPEED);
        entityData.set(IGNITING, false);
    }

    private void igniteNearbyMobs() {
        if (!isOnFire() || !isAggressive()) {
            return;
        }
        List<LivingEntity> nearbyEntities = level().getEntitiesOfClass(
                LivingEntity.class,
                getBoundingBox().inflate(0.5),
                entity -> entity != this
        );

        for (LivingEntity entity : nearbyEntities) {
            entity.setRemainingFireTicks(200);
            if (entity instanceof Creeper creeper) {
                creeper.ignite();
                ignitedCreepers.add(creeper.getUUID());
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
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public boolean isInvertedHealAndHarm() {
        return true;
    }

    @Override
    public void handleEntityEvent(byte state) {
        if (state == IGNITE_ANIMATION_EVENT){
            igniteAnimationState.startIfStopped(tickCount);
        }
        if (state == ATTACK_ANIMATION_EVENT){
            attackAnimationState.stop();
            attackAnimationState.startIfStopped(tickCount);
        }
        super.handleEntityEvent(state);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if(!level().isClientSide){
            level().broadcastEntityEvent(this, ATTACK_ANIMATION_EVENT);
        }
        if(isOnFire()){
            entity.setRemainingFireTicks(100);
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public void aiStep() {
        if (isAlive() && isSunSensitive() && isSunBurnTick()) {
            SunlightBurning.apply(this);
        }
        super.aiStep();
    }
}
