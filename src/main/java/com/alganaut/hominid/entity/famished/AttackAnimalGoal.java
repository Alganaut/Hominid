package com.alganaut.hominid.entity.famished;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

// todo revisit needs cleanup
class AttackAnimalGoal extends MeleeAttackGoal {
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private Path path;
    private LastTargetPos pos;
    private int ticksUntilPathRecalculation;
    private int ticksUntilNextAttack;
    private LivingEntity target;

    public AttackAnimalGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.pos = new LastTargetPos(0.0, 0.0, 0.0);
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity nearest = PreySelection.findNearest(mob);
        if (nearest != null) {
            target = nearest;
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (target == null) {
            return false;
        }
        if (!target.isAlive()) {
            return false;
        }
        if (!followingTargetEvenIfNotSeen) {
            return !mob.getNavigation().isDone();
        }
        return mob.isWithinRestriction(target.blockPosition()) && (!(target instanceof Player player) || !player.isSpectator() && !player.isCreative());
    }

    @Override
    public void start() {
        if (target != null) {
            mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.27);
        }
        mob.getNavigation().moveTo(path, speedModifier);
        mob.setAggressive(true);
        ticksUntilPathRecalculation = 0;
        ticksUntilNextAttack = 0;
    }

    @Override
    public void stop() {
        LivingEntity livingentity = mob.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            mob.setTarget(null);
            target = null;
        }

        mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.17);
        mob.setAggressive(false);
        mob.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingentity = mob.getTarget();
        if (livingentity == null) {
            return;
        }
        updateCooldowns();
        mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
        if (shouldRecalculatePath(livingentity)) {
            calculatePath(livingentity);
        }
        checkAndPerformAttack(livingentity);
    }

    private void updateCooldowns() {
        ticksUntilNextAttack = Math.max(ticksUntilNextAttack - 1, 0);
        ticksUntilPathRecalculation = Math.max(ticksUntilPathRecalculation - 1, 0);
    }

    private void calculatePath(LivingEntity target) {
        pos = new LastTargetPos(target.getX(), target.getY(), target.getZ());
        ticksUntilPathRecalculation = 4 + mob.getRandom().nextInt(7);
        double distanceToTargetSqr = mob.distanceToSqr(target);

        if (distanceToTargetSqr > 1024.0) {
            ticksUntilPathRecalculation += 10;
        } else if (distanceToTargetSqr > 256.0) {
            ticksUntilPathRecalculation += 5;
        }

        if (!mob.getNavigation().moveTo(target, speedModifier)) {
            ticksUntilPathRecalculation += 15;
        }

        ticksUntilPathRecalculation = adjustedTickDelay(ticksUntilPathRecalculation);
    }

    private boolean shouldRecalculatePath(LivingEntity target) {
        if (ticksUntilPathRecalculation > 0) {
            return false;
        }

        if (!followingTargetEvenIfNotSeen && !mob.getSensing().hasLineOfSight(target)) {
            return false;
        }
        boolean noPath = pos.x == 0.0 && pos.y == 0.0 && pos.z == 0.0;
        boolean farEnough = target.distanceToSqr(pos.x, pos.y, pos.z) >= 1.0;
        boolean randomRefresh = mob.getRandom().nextFloat() < 0.05F;
        return noPath || farEnough || randomRefresh;
    }



    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        if (canPerformAttack(target)) {
            resetAttackCooldown();
            mob.swing(InteractionHand.MAIN_HAND);
            mob.doHurtTarget(target);
        }

    }

    @Override
    protected void resetAttackCooldown() {
        ticksUntilNextAttack = adjustedTickDelay(20);
    }

    @Override
    protected boolean isTimeToAttack() {
        return ticksUntilNextAttack <= 0;
    }

    @Override
    protected boolean canPerformAttack(LivingEntity entity) {
        return isTimeToAttack() && mob.isWithinMeleeAttackRange(entity) && mob.getSensing().hasLineOfSight(entity);
    }

    @Override
    protected int getTicksUntilNextAttack() {
        return ticksUntilNextAttack;
    }

    @Override
    protected int getAttackInterval() {
        return adjustedTickDelay(20);
    }

    private record LastTargetPos(double x, double y, double z) {}
}
