package com.alganaut.hominid.registry.datagen;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HominidItemTagProvider extends ItemTagsProvider {
    public HominidItemTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            CompletableFuture<TagLookup<Block>> blockTags,
            @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, blockTags, Hominid.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(ItemTags.TRIM_TEMPLATES).add(
                HominidItems.REMAINS_SMITHING_TEMPLATE.get()
        );
    }
}

