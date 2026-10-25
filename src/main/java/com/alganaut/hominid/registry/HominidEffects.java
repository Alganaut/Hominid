package com.alganaut.hominid.registry;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.effect.EnduranceEffect;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HominidEffects {
    private HominidEffects() {

    }

    public static final RegistryEntry<MobEffect,EnduranceEffect> enduranceEffect = Hominid.REGISTRATE.get().simple("endurance",Registries.MOB_EFFECT, EnduranceEffect::new);

    // Loads this class so we can registrate it.
    public static void register() {
    }
}
