package com.alganaut.hominid.registry.effect;

import com.alganaut.hominid.Hominid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;
import java.util.function.Supplier;

public class HominidEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Hominid.MODID);

    @SuppressWarnings("unused")
    public static final DeferredHolder<MobEffect, MobEffect>
            HONEYED = MOB_EFFECTS.register("honeyed", () -> new HoneyedEffect());


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}
