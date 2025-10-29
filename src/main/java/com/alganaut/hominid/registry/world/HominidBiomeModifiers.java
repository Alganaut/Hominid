package com.alganaut.hominid.registry.world;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.HominidEntityCreator;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class HominidBiomeModifiers {
    public static final ResourceKey<BiomeModifier> SPAWN_MELLIFIED = registerKey("spawn_mellified");
    public static final ResourceKey<BiomeModifier> SPAWN_INCENDIARY = registerKey("spawn_incendiary");
    public static final ResourceKey<BiomeModifier> SPAWN_FAMISHED = registerKey("spawn_famished");
    public static final ResourceKey<BiomeModifier> SPAWN_FOSSILISED = registerKey("spawn_fossilised");
    public static final ResourceKey<BiomeModifier> SPAWN_VAMPIRE = registerKey("spawn_vampire");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);
        context.register(SPAWN_MELLIFIED, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.FLOWER_FOREST), biomes.getOrThrow(Biomes.MEADOW), biomes.getOrThrow(Biomes.CHERRY_GROVE), biomes.getOrThrow(Biomes.BIRCH_FOREST)),
                List.of(new MobSpawnSettings.SpawnerData(HominidEntityCreator.MELLIFIED.get(), 100, 1, 3))));
        context.register(SPAWN_INCENDIARY, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.TAIGA), biomes.getOrThrow(Biomes.SNOWY_TAIGA), biomes.getOrThrow(Biomes.OLD_GROWTH_SPRUCE_TAIGA), biomes.getOrThrow(Biomes.OLD_GROWTH_PINE_TAIGA), biomes.getOrThrow(Biomes.WINDSWEPT_HILLS), biomes.getOrThrow(Biomes.GROVE)),
                List.of(new MobSpawnSettings.SpawnerData(HominidEntityCreator.INCENDIARY.get(), 100, 1, 3))));
        context.register(SPAWN_FAMISHED, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.DRIPSTONE_CAVES), biomes.getOrThrow(Biomes.STONY_PEAKS), biomes.getOrThrow(Biomes.JAGGED_PEAKS), biomes.getOrThrow(Biomes.FROZEN_PEAKS), biomes.getOrThrow(Biomes.SNOWY_SLOPES), biomes.getOrThrow(Biomes.DESERT), biomes.getOrThrow(Biomes.BADLANDS), biomes.getOrThrow(Biomes.SNOWY_PLAINS), biomes.getOrThrow(Biomes.ICE_SPIKES), biomes.getOrThrow(Biomes.WINDSWEPT_GRAVELLY_HILLS), biomes.getOrThrow(Biomes.WINDSWEPT_HILLS)),
                List.of(new MobSpawnSettings.SpawnerData(HominidEntityCreator.FAMISHED.get(), 75, 1, 6))));
        context.register(SPAWN_FOSSILISED, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.DESERT), biomes.getOrThrow(Biomes.SWAMP), biomes.getOrThrow(Biomes.DRIPSTONE_CAVES), biomes.getOrThrow(Biomes.LUSH_CAVES), biomes.getOrThrow(Biomes.MANGROVE_SWAMP), biomes.getOrThrow(Biomes.BADLANDS), biomes.getOrThrow(Biomes.ERODED_BADLANDS), biomes.getOrThrow(Biomes.WOODED_BADLANDS)),
                List.of(new MobSpawnSettings.SpawnerData(HominidEntityCreator.FOSSILISED.get(), 25, 1, 1))));
        context.register(SPAWN_VAMPIRE, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.DARK_FOREST)),
                List.of(new MobSpawnSettings.SpawnerData(HominidEntityCreator.VAMPIRE.get(), 50, 1, 1))));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Hominid.MODID, name));
    }
}
