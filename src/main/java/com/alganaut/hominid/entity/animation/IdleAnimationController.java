package com.alganaut.hominid.entity.animation;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;

public final class IdleAnimationController {
    private final int interval;
    private int timeout;

    public IdleAnimationController(int interval) {
        this.interval = interval;
    }

    public void tick(Entity entity, AnimationState animation) {
        if (entity.getDeltaMovement().horizontalDistance() <= 0.001F) {
            if (timeout <= 0) {
                timeout = interval;
                animation.start(entity.tickCount);
            } else {
                --timeout;
            }
        } else {
            timeout = 0;
            animation.stop();
        }
    }
}
