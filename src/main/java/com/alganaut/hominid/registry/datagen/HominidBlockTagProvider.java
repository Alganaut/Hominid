package com.alganaut.hominid.registry.datagen;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.block.HominidBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HominidBlockTagProvider extends BlockTagsProvider {
    public HominidBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Hominid.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(HominidBlocks.FOSSILIZED_STONE.get())
                .add(HominidBlocks.POLISHED_FOSSILIZED_STONE.get())
                .add(HominidBlocks.FOSSILIZED_STONE_BRICKS.get())
                .add(HominidBlocks.CRACKED_FOSSILIZED_STONE_BRICKS.get())
                .add(HominidBlocks.FOSSILIZED_STONE_STAIRS.get())
                .add(HominidBlocks.POLISHED_FOSSILIZED_STONE_STAIRS.get())
                .add(HominidBlocks.FOSSILIZED_STONE_BRICK_STAIRS.get())
                .add(HominidBlocks.FOSSILIZED_STONE_SLAB.get())
                .add(HominidBlocks.POLISHED_FOSSILIZED_STONE_SLAB.get())
                .add(HominidBlocks.FOSSILIZED_STONE_BRICK_SLAB.get())
                .add(HominidBlocks.FOSSILIZED_STONE_PILLAR.get());
        tag(BlockTags.STAIRS)
                .add(HominidBlocks.FOSSILIZED_STONE_STAIRS.get())
                .add(HominidBlocks.POLISHED_FOSSILIZED_STONE_STAIRS.get())
                .add(HominidBlocks.FOSSILIZED_STONE_BRICK_STAIRS.get());
        tag(BlockTags.SLABS)
                .add(HominidBlocks.FOSSILIZED_STONE_SLAB.get())
                .add(HominidBlocks.POLISHED_FOSSILIZED_STONE_SLAB.get())
                .add(HominidBlocks.FOSSILIZED_STONE_BRICK_SLAB.get());
        tag(BlockTags.WALLS)
                .add(HominidBlocks.FOSSILIZED_STONE_WALL.get())
                .add(HominidBlocks.POLISHED_FOSSILIZED_STONE_WALL.get())
                .add(HominidBlocks.FOSSILIZED_STONE_BRICK_WALL.get());
    }
}
