package com.alganaut.hominid.registry.entity.custom;

import com.alganaut.hominid.registry.effect.HominidEffects;
import com.alganaut.hominid.registry.sound.HominidSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class Vampire extends Monster {
    public int idleAnimationTimeout = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState dieAnimationState = new AnimationState();

    public Vampire(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.FOLLOW_RANGE, 50.0)
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


    @Nullable
    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistance() > 0.01F;
    }

    protected boolean isSunSensitive() {
        return true;
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false));
        this.targetSelector.addGoal(2, new TargetDamagedEntityGoal(this));
        this.targetSelector.addGoal(2, new BurnInSunGoal(this));
        this.targetSelector.addGoal(2, new FollowPlayerGoal(this){
            public boolean canUse() { return !Vampire.this.isAggressive() && super.canUse(); }
        });
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
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if(!level().isClientSide){
            this.level().broadcastEntityEvent(this, (byte) 65);
        }
        return super.doHurtTarget(entity);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public boolean isInvertedHealAndHarm() {
        return true;
    }


    private void dieAndPerish() {
        if (!this.isAlive()) return;

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 20, 0.5, 0.5, 0.5, 0.1);
        }
        this.scream();

        this.remove(Entity.RemovalReason.DISCARDED);
    }



    public class TargetDamagedEntityGoal extends Goal {
        private final Mob mob;
        private LivingEntity target;
        private static final double DETECTION_RANGE = 30.0;

        public TargetDamagedEntityGoal(Mob mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public boolean canUse() {
            if (mob.getTarget() != null && mob.getTarget().isAlive()) {
                return false;
            }

            List<LivingEntity> entities = mob.level().getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(DETECTION_RANGE),
                    e -> e != mob && e.hurtTime > 0);

            if (!entities.isEmpty()) {
                entities.sort(Comparator.comparingDouble(mob::distanceToSqr));
                target = entities.get(0);
                return true;
            }

            return false;
        }

        @Override
        public void start() {
            if (target != null) {
                mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3);
                mob.setTarget(target);
            }
        }

        @Override
        public void stop(){
            mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25);
        }
    }

    @Override
    public void handleEntityEvent(byte state) {
        if (state == 65){
            this.attackAnimationState.stop();
            this.attackAnimationState.startIfStopped(this.tickCount);
        }
        if (state == 85){
            this.dieAnimationState.stop();
            this.dieAnimationState.startIfStopped(this.tickCount);
        }
        else super.handleEntityEvent(state);
    }

    public class BurnInSunGoal extends Goal {
        private final Vampire entity;
        private int sunTimer = 0;
        private static final int SUN_VANISH_TIME = 10;

        public BurnInSunGoal(Vampire entity) {
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            return entity.level().isDay() && isInDirectSunlight();
        }

        @Override
        public void tick() {
            if (isInDirectSunlight()) {
                if (sunTimer == 0) {
                    entity.level().broadcastEntityEvent(entity, (byte) 85);
                    entity.setRemainingFireTicks(100);
                }

                if (sunTimer < SUN_VANISH_TIME) {
                    sunTimer++;
                } else {
                    triggerEvent();
                }
            } else {
                if (sunTimer > 0) {

                }
            }
        }

        private boolean isInDirectSunlight() {
            return entity.level().canSeeSky(entity.blockPosition());
        }

        private void triggerEvent() {
            entity.dieAndPerish();
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }
    }

    public class FollowPlayerGoal extends Goal {
        private final Vampire entity;
        private final double followDistance = 30.0;
        private final double stopDistance = 8.0;
        private Player targetPlayer;

        public FollowPlayerGoal(Vampire entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            Player player = entity.level().getNearestPlayer(entity, followDistance);
            if (player == null || entity.getTarget() == player || player.isCreative() || entity.getTarget() != null) {
                return false;
            }

            this.targetPlayer = player;
            return true;
        }

        @Override
        public void start(){
            entity.getNavigation().moveTo(targetPlayer, 1.0);
        }

        @Override
        public boolean canContinueToUse() {
            if (targetPlayer == null || targetPlayer.isCreative() || entity.getTarget() != null) {
                return false;
            }

            double distanceToPlayer = entity.distanceTo(targetPlayer);
            return distanceToPlayer <= followDistance && distanceToPlayer > stopDistance;
        }

        @Override
        public void tick() {
            if (targetPlayer != null) {
                targetPlayer.addEffect(new MobEffectInstance(HominidEffects.PARANOIA, 80, 0));
                double distanceToPlayer = entity.distanceTo(targetPlayer);

                if (distanceToPlayer <= stopDistance) {
                    entity.getNavigation().stop();
                } else if (distanceToPlayer > stopDistance && distanceToPlayer <= followDistance) {
                    entity.getNavigation().moveTo(targetPlayer, 1.0);
                }
            }
        }

        @Override
        public void stop() {
            entity.getNavigation().stop();
        }
    }


    public void scream(){
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITHER_DEATH, this.getSoundSource(), 1.0F, 1.0F);
    }
}
