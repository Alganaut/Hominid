package com.alganaut.hominid.entity.vampire;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

class TargetDamagedEntityGoal extends Goal {
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