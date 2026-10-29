package com.alganaut.hominid.registry;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.item.HominidItems;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.neoforged.neoforge.registries.DeferredItem;

public class HominidTrimPatterns {
    public static final ResourceKey<TrimPattern> REMAINS = ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "remains"));

    public static final ResourceKey<TrimPattern> FOSSIL_TRIM =
            ResourceKey.create(Registries.TRIM_PATTERN, Hominid.id("fossil"));

    static {
        Hominid.REGISTRATE.get().getDataGenInitializer().add(
                Registries.TRIM_PATTERN,
                ctx -> ctx.register(
                        FOSSIL_TRIM, new TrimPattern(FOSSIL_TRIM.location(), BuiltInRegistries.ITEM.wrapAsHolder(HominidItems.REMAINS_SMITHING_TEMPLATE.get()), Component.translatable(Util.makeDescriptionId("trim_pattern", FOSSIL_TRIM.location())), false
                        )));
    }

    public static void bootstrap(BootstrapContext<TrimPattern> context) {
        register(context, HominidItems.REMAINS_SMITHING_TEMPLATE, REMAINS);
    }

    private static void register(BootstrapContext<TrimPattern> context, DeferredItem<Item> item, ResourceKey<TrimPattern> key) {
        TrimPattern trimPattern = new TrimPattern(key.location(), item.getDelegate(), Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())), false);
        context.register(key, trimPattern);
    }}
