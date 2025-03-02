package com.alganaut.hominid;

import com.alganaut.hominid.registry.block.HominidBlocks;
import com.alganaut.hominid.registry.entity.HominidEntities;
import com.alganaut.hominid.registry.item.HominidItems;
import com.alganaut.hominid.registry.sound.HominidSounds;
import com.google.common.collect.ImmutableList;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Hominid.MODID)
public class Hominid {
    public static final String MODID = "hominid";
    private static final Logger LOGGER = LogUtils.getLogger();

    // Contains all the registries
    private static final ImmutableList<DeferredRegister<?>> REGISTRIES = ImmutableList.of(
            HominidItems.ITEMS,
            HominidBlocks.BLOCKS,
            HominidSounds.SOUND_EVENTS,
            HominidEntities.ENTITY_TYPES,
            HominidCreativeModeTabs.CREATIVE_MODE_TAB
    );

    public Hominid(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Hello I am Zombie.");

        // Registration, loops through all the registries defined in the REGISTRIES constant
        for (var registry : REGISTRIES)
            registry.register(modEventBus);

        modEventBus.addListener(HominidCreativeModeTabs::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
