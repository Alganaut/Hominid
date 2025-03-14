package com.alganaut.hominid.registry.event;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.resources.IoSupplier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.server.packs.PackType;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.moddiscovery.ModFile;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;

public class ModPackFinder implements RepositorySource {
    private final String modId;

    public ModPackFinder(String modId) {
        this.modId = modId;
    }
    @Override
    public void loadPacks(Consumer<Pack> consumer) {
        Path packPath = getPackPath();
        PackLocationInfo packInfo = new PackLocationInfo(
                "builtin_hominid",
                Component.literal("Hominid Retextures"),
                PackSource.BUILT_IN,
                Optional.empty()
        );
        PackResources resources = new PathPackResources(packInfo, packPath);

        PackSelectionConfig selectionConfig = new PackSelectionConfig(false, Pack.Position.TOP, false);
        Pack.ResourcesSupplier resourceSupplier = new Pack.ResourcesSupplier() {
            @Override
            public PackResources openPrimary(PackLocationInfo packLocationInfo) {
                return resources;
            }

            @Override
            public PackResources openFull(PackLocationInfo packLocationInfo, Pack.Metadata metadata) {
                return resources;
            }
        };

        Pack pack = Pack.readMetaAndCreate(packInfo, resourceSupplier, PackType.CLIENT_RESOURCES, selectionConfig);


        if (pack == null) {
            return;
        }

        consumer.accept(pack);
    }

    private static Path getPackPath() {
        Optional<ModFileInfo> modFileInfo = ModList.get().getModContainerById("hominid").map(m -> (ModFileInfo) m.getModInfo().getOwningFile());
        if (modFileInfo.isPresent()) {
            ModFile modFile = modFileInfo.get().getFile();
            return modFile.findResource("resourcepacks/builtin_hominid");
        }

        return Path.of("");
    }
}
