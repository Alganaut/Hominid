package com.alganaut.hominid.registry.effect;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParanoiaEffect extends MobEffect {
    private static final Map<UUID, Float> lastYawMap = new HashMap<>();
    private static final Map<UUID, Float> lastPitchMap = new HashMap<>();
    private static final Map<UUID, Integer> soundTimerMap = new HashMap<>();

    public ParanoiaEffect() {
        super(MobEffectCategory.HARMFUL, 0x6e211f);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        System.out.println("Ticking on side: " + (entity.level().isClientSide ? "Client" : "Server"));
        if (entity.level().isClientSide && entity instanceof Player player) {
            UUID uuid = player.getUUID();
            float currentYaw = player.getYRot();
            float currentPitch = player.getXRot();

            float lastYaw = lastYawMap.getOrDefault(uuid, currentYaw);
            float lastPitch = lastPitchMap.getOrDefault(uuid, currentPitch);

            boolean isCameraStill = Math.abs(currentYaw - lastYaw) < 0.1F && Math.abs(currentPitch - lastPitch) < 0.1F;

            int timer = soundTimerMap.getOrDefault(uuid, 0);

            if (isCameraStill) {
                if (timer <= 0) {
                    player.playSound(SoundEvents.GRASS_STEP, 0.5F, 1.0F);
                    System.out.println("uhhh playing noises");
                    timer = 60;
                } else {
                    timer--;
                }
            } else {
                timer = 0;
            }

            lastYawMap.put(uuid, currentYaw);
            lastPitchMap.put(uuid, currentPitch);
            soundTimerMap.put(uuid, timer);
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

}