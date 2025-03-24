package com.alganaut.hominid.registry.datagen;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.trim.HominidTrimPatterns;
import com.alganaut.hominid.registry.world.HominidBiomeModifiers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class HominidDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()

            .add(Registries.TRIM_PATTERN, HominidTrimPatterns::bootstrap)

            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, HominidBiomeModifiers::bootstrap);

    public HominidDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Hominid.MODID));
    }
}
