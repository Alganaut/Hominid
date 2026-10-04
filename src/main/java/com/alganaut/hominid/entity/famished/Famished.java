package com.alganaut.hominid.entity.famished;

import com.alganaut.hominid.entity.animation.IdleAnimationController;
import com.alganaut.hominid.entity.behavior.SunlightBurning;
import com.alganaut.hominid.entity.goal.AttackTurtleEggGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

public class Famished extends Monster {
    private static final byte ATTACK_ANIMATION_EVENT = 100;
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private final IdleAnimationController idleAnimationController = new IdleAnimationController(40);

    public Famished(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MAX_HEALTH, 12.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 5.0);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new AttackAnimalGoal(this, 1.2, false));
        goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false));
        goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        goalSelector.addGoal(4, new AttackTurtleEggGoal(this, 1.0, 3, 1.14));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Animal.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
    }

    @Override
    public void aiStep() {
        if (isAlive() && isSunSensitive() && isSunBurnTick()) {
            SunlightBurning.apply(this);
        }
        super.aiStep();
    }

    @Override
    public void tick() {
        if (level().isClientSide()) {
            idleAnimationController.tick(this, idleAnimationState);
        }
        super.tick();
        if(getTarget() == null){
            return;
        }

        if(getTarget() instanceof Player player){
            if(player.getMainHandItem().is(ItemTags.MEAT)){
                getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.27);
            } else{
                getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.17);
            }
        }
    }

    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HUSK_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.HUSK_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.HUSK_DEATH;
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
        if (state == ATTACK_ANIMATION_EVENT){
            attackAnimationState.stop();
            attackAnimationState.startIfStopped(tickCount);
        }
        else super.handleEntityEvent(state);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if(!level().isClientSide){
            level().broadcastEntityEvent(this, ATTACK_ANIMATION_EVENT);
        }
        return super.doHurtTarget(entity);
    }

}
