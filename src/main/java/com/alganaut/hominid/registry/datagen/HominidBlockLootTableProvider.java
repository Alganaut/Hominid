package com.alganaut.hominid.registry.datagen;

import com.alganaut.hominid.registry.block.HominidBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class HominidBlockLootTableProvider extends BlockLootSubProvider {
    protected HominidBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(HominidBlocks.FOSSILIZED_STONE.get());
        dropSelf(HominidBlocks.POLISHED_FOSSILIZED_STONE.get());
        dropSelf(HominidBlocks.FOSSILIZED_STONE_BRICKS.get());
        dropSelf(HominidBlocks.CRACKED_FOSSILIZED_STONE_BRICKS.get());

        dropSelf(HominidBlocks.FOSSILIZED_STONE_STAIRS.get());
        dropSelf(HominidBlocks.POLISHED_FOSSILIZED_STONE_STAIRS.get());
        dropSelf(HominidBlocks.FOSSILIZED_STONE_BRICK_STAIRS.get());

        add(HominidBlocks.FOSSILIZED_STONE_SLAB.get(),
                block -> createSlabItemTable(HominidBlocks.FOSSILIZED_STONE_SLAB.get()));
        add(HominidBlocks.POLISHED_FOSSILIZED_STONE_SLAB.get(),
                block -> createSlabItemTable(HominidBlocks.POLISHED_FOSSILIZED_STONE_SLAB.get()));
        add(HominidBlocks.FOSSILIZED_STONE_BRICK_SLAB.get(),
                block -> createSlabItemTable(HominidBlocks.FOSSILIZED_STONE_BRICK_SLAB.get()));

        dropSelf(HominidBlocks.FOSSILIZED_STONE_WALL.get());
        dropSelf(HominidBlocks.POLISHED_FOSSILIZED_STONE_WALL.get());
        dropSelf(HominidBlocks.FOSSILIZED_STONE_BRICK_WALL.get());

        dropSelf(HominidBlocks.FOSSILIZED_STONE_PILLAR.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return HominidBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
