package com.alganaut.hominid.registry.effect;

import com.alganaut.hominid.Hominid;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HominidEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Hominid.MODID);

    public static final Holder<MobEffect> ENDURANCE = MOB_EFFECTS.register("endurance",
            EnduranceEffect::new);

    public static void register(IEventBus bus){
        MOB_EFFECTS.register(bus);
    }
}
