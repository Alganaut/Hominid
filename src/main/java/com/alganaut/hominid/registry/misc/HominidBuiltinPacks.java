package com.alganaut.hominid.registry.misc;

import com.alganaut.hominid.Hominid;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class HominidBuiltinPacks {

    public static void rpHominidRetextures(AddPackFindersEvent event) {
        Path path = ModList.get().getModFileById(Hominid.MODID).getFile().findResource("resourcepacks/hominid_retextures");
        PackMetadataSection metadata = new PackMetadataSection(Component.literal("Allows vanilla mobs to work with Hominid correctly"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
        event.addRepositorySource(source -> source.accept(new Pack(
                new PackLocationInfo("hominid:hominid_retextures", Component.literal("Hominid Retextures"), PackSource.BUILT_IN, Optional.empty()),
                new PathPackResources.PathResourcesSupplier(path),
                new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                new PackSelectionConfig(true, Pack.Position.TOP, false)
        )));
    }

    public static void rpHominidZombieVariation(AddPackFindersEvent event) {
        Path path = ModList.get().getModFileById(Hominid.MODID).getFile().findResource("resourcepacks/hominid_zombie_variation");
        PackMetadataSection metadata = new PackMetadataSection(Component.literal("Requires EMF!"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
        event.addRepositorySource(source -> source.accept(new Pack(
                new PackLocationInfo("hominid:hominid_zombie_variation", Component.literal("Hominid Zombie Variation"), PackSource.BUILT_IN, Optional.empty()),
                new PathPackResources.PathResourcesSupplier(path),
                new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                new PackSelectionConfig(false, Pack.Position.TOP, false)
        )));
    }
}