package com.alganaut.hominid;

import com.alganaut.hominid.registry.block.HominidBlocks;
import com.alganaut.hominid.registry.effect.HominidEffects;
import com.alganaut.hominid.registry.entity.HominidEntityCreator;
import com.alganaut.hominid.registry.event.HominidClientEvents;
import com.alganaut.hominid.registry.item.HominidItems;
import com.alganaut.hominid.registry.misc.HominidCreativeModeTab;
import com.alganaut.hominid.registry.sound.HominidSounds;
import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Hominid.MODID)
public class Hominid {
    public static final String MODID = "hominid";
    public static final Logger LOGGER = LogUtils.getLogger();

    // Contains all the registries
    private static final ImmutableList<DeferredRegister<?>> REGISTRIES = ImmutableList.of(
            HominidItems.ITEMS,
            HominidBlocks.BLOCKS,
            HominidSounds.SOUND_EVENTS,
            HominidEntityCreator.ENTITY_TYPES
    );

    public Hominid(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Elk_Hastiness");

        // Registration, loops through all the registries defined in the REGISTRIES constant
        for (var registry : REGISTRIES){
            registry.register(modEventBus);
        }

        HominidClientEvents.register();
        HominidCreativeModeTab.register(modEventBus);
        HominidEffects.register(modEventBus);


        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
