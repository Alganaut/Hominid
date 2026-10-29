package com.alganaut.hominid.entity.bellman;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

class FollowPlayerGoal extends Goal {
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
        if (entity.level().isClientSide) {
            return false;
        }

        targetPlayer = entity.level().getNearestPlayer(entity, maxDistance);

        return targetPlayer != null && !targetPlayer.isCreative() && targetPlayer.distanceTo(entity) >= minDistance && targetPlayer.distanceTo(entity) <= maxDistance;
    }

    @Override
    public boolean canContinueToUse() {
        return targetPlayer != null && targetPlayer.isAlive() && targetPlayer.distanceTo(entity) > minDistance && targetPlayer.distanceTo(entity) <= maxDistance;
    }

    @Override
    public void start() {
        if (targetPlayer == null) {
            return;
        }
        entity.getNavigation().moveTo(targetPlayer, speedModifier);
    }

    @Override
    public void stop() {
        targetPlayer = null;
    }

    @Override
    public void tick() {
        if (targetPlayer == null) {
            return;
        }
        double distance = targetPlayer.distanceTo(entity);
        if (distance > minDistance && distance <= maxDistance) {
            entity.getNavigation().moveTo(targetPlayer, speedModifier);
        }

    }
}
