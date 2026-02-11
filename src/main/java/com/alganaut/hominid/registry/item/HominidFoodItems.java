package com.alganaut.hominid.registry.item;

import com.alganaut.hominid.registry.effect.HominidEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class HominidFoodItems {
    public static final FoodProperties FAMISHED_STOMACH = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f)
            .effect(() -> new MobEffectInstance(HominidEffects.ENDURANCE, 2400, 0), 1f).build();
}
