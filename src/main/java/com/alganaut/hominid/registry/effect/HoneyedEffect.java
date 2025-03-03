package com.alganaut.hominid.registry.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;

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
        return true;
    }

    public boolean shouldApplyEffectTickThisTick(int p_295368_, int p_294232_) {
        int i = 50 >> p_294232_;
        return i > 0 ? p_295368_ % i == 0 : true;
    }
}
