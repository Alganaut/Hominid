package com.alganaut.hominid.registry.trim;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.neoforged.neoforge.registries.DeferredItem;

public class HominidTrimPatterns {
    public static final ResourceKey<TrimPattern> REMAINS = ResourceKey.create(Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "remains"));

    public static void bootstrap(BootstrapContext<TrimPattern> context) {
        register(context, HominidItems.REMAINS_SMITHING_TEMPLATE, REMAINS);
    }

    private static void register(BootstrapContext<TrimPattern> context, DeferredItem<Item> item, ResourceKey<TrimPattern> key) {
        TrimPattern trimPattern = new TrimPattern(key.location(), item.getDelegate(),
                Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())), false);
        context.register(key, trimPattern);
    }}
