package com.alganaut.hominid.registry.effect;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.EffectCures;

import java.util.List;

public class HoneyedEffect extends MobEffect {
    public HoneyedEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xE3A857);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        System.out.println("[DEBUG] HoneyedEffect applied to: " + entity.getName().getString());
        if(entity.getHealth() < entity.getMaxHealth()){
            if (entity.isInvertedHealAndHarm()) {
                entity.heal(1);
            } else {
                entity.heal(1);
            }
        }
        List<Holder<MobEffect>> negativeEffects = List.of(
                MobEffects.POISON, MobEffects.WEAKNESS, MobEffects.MOVEMENT_SLOWDOWN,
                MobEffects.BLINDNESS, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.WITHER,
                MobEffects.HUNGER, MobEffects.DIG_SLOWDOWN, MobEffects.LEVITATION,
                MobEffects.BAD_OMEN, MobEffects.DARKNESS, MobEffects.INFESTED
        );

        for (Holder<MobEffect> effect : negativeEffects) {
            if (entity.hasEffect(effect)) {
                entity.removeEffect(effect);
            }
        }
        return true;
    }

    public boolean shouldApplyEffectTickThisTick(int p_295368_, int p_294232_) {
        int i = 50 >> p_294232_;
        return i > 0 ? p_295368_ % i == 0 : true;
    }
}
