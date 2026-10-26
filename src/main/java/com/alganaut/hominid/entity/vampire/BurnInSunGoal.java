package com.alganaut.hominid.entity.vampire;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;


class BurnInSunGoal extends Goal {
    private final Vampire entity;
    private int sunTimer = 0;
    private static final int SUN_VANISH_TIME = 20;

    public BurnInSunGoal(Vampire entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return entity.level().isDay() && isInDirectSunlight();
    }

    @Override
    public void tick() {
        if (!isInDirectSunlight()) {
            return;
        }

        if (sunTimer == 0) {
            entity.level().broadcastEntityEvent(entity, Vampire.DIE_ANIMATION_EVENT);
            entity.setRemainingFireTicks(100);
            triggerEvent();
        }

        if (sunTimer < SUN_VANISH_TIME) {
            sunTimer++;
        } else {
            this.entity.remove(Entity.RemovalReason.DISCARDED);
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
