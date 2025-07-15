package com.alganaut.hominid.registry.effect;

import com.alganaut.hominid.registry.sound.HominidSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class ParanoiaEffect extends MobEffect {
    private static final Map<UUID, Float> lastYawMap = new HashMap<>();
    private static final Map<UUID, Float> lastPitchMap = new HashMap<>();
    private static final Map<UUID, Integer> soundTimerMap = new HashMap<>();

    private static final Set<UUID> playingParanoia = new HashSet<>();


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
                    player.playSound(SoundEvents.GRASS_STEP, 0.3F, 1.0F);
                    player.playSound(SoundEvents.SAND_STEP, 0.3F, 1.0F);
                    player.playSound(SoundEvents.STONE_STEP, 0.3F, 1.0F);
                    timer = 40;
                } else {
                    timer--;
                }
            } else {
                timer = 0;
            }

            lastYawMap.put(uuid, currentYaw);
            lastPitchMap.put(uuid, currentPitch);
            soundTimerMap.put(uuid, timer);
            if (entity.level().isClientSide) {
                ParanoiaSoundManager.tryPlayParanoiaSound(player);
            }
        }

        return true;
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

}