package com.alganaut.hominid.registry.effect;

import com.alganaut.hominid.Hominid;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HominidEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Hominid.MODID);

    public static final Holder<MobEffect> HONEYED = MOB_EFFECTS.register("honeyed",
            () -> new HoneyedEffect());

    public static void register(IEventBus bus){
        MOB_EFFECTS.register(bus);
    }
}
