package com.alganaut.hominid;

import com.alganaut.hominid.registry.HominidEntityCreator;
import com.alganaut.hominid.registry.HominidBlocks;
import com.alganaut.hominid.registry.HominidEffects;
import com.alganaut.hominid.registry.event.HominidClientEvents;
import com.alganaut.hominid.registry.item.HominidItems;
import com.alganaut.hominid.registry.misc.HominidCreativeModeTab;
import com.alganaut.hominid.registry.HominidSounds;
import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.List;

@Mod(Hominid.MODID)
public class Hominid {
    public static final String MODID = "hominid";

    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MODID));

    public static final Logger LOGGER = LogUtils.getLogger();

    public Hominid(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Elk_Hastiness");


        HominidBlocks.register();
        HominidItems.register(modEventBus);
        HominidEffects.register();
        HominidSounds.register();
        HominidEntityCreator.register();
        HominidClientEvents.register();
        HominidCreativeModeTab.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
