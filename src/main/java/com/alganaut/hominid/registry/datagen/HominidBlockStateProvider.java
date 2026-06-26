package com.alganaut.hominid.registry.datagen;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.block.HominidBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class HominidBlockStateProvider extends BlockStateProvider {
    public HominidBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Hominid.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(HominidBlocks.FOSSILIZED_STONE);
        blockWithItem(HominidBlocks.POLISHED_FOSSILIZED_STONE);
        blockWithItem(HominidBlocks.FOSSILIZED_STONE_BRICKS);
        blockWithItem(HominidBlocks.CRACKED_FOSSILIZED_STONE_BRICKS);

        stairsBlock(HominidBlocks.FOSSILIZED_STONE_STAIRS.get(), blockTexture(HominidBlocks.FOSSILIZED_STONE.get()));
        stairsBlock(HominidBlocks.POLISHED_FOSSILIZED_STONE_STAIRS.get(), blockTexture(HominidBlocks.POLISHED_FOSSILIZED_STONE.get()));
        stairsBlock(HominidBlocks.FOSSILIZED_STONE_BRICK_STAIRS.get(), blockTexture(HominidBlocks.FOSSILIZED_STONE_BRICKS.get()));

        slabBlock(HominidBlocks.FOSSILIZED_STONE_SLAB.get(), blockTexture(HominidBlocks.FOSSILIZED_STONE.get()), blockTexture(HominidBlocks.FOSSILIZED_STONE.get()));
        slabBlock(HominidBlocks.POLISHED_FOSSILIZED_STONE_SLAB.get(), blockTexture(HominidBlocks.POLISHED_FOSSILIZED_STONE.get()), blockTexture(HominidBlocks.POLISHED_FOSSILIZED_STONE.get()));
        slabBlock(HominidBlocks.FOSSILIZED_STONE_BRICK_SLAB.get(), blockTexture(HominidBlocks.FOSSILIZED_STONE_BRICKS.get()), blockTexture(HominidBlocks.FOSSILIZED_STONE_BRICKS.get()));

        blockItem(HominidBlocks.FOSSILIZED_STONE_STAIRS);
        blockItem(HominidBlocks.POLISHED_FOSSILIZED_STONE_STAIRS);
        blockItem(HominidBlocks.FOSSILIZED_STONE_BRICK_STAIRS);

        blockItem(HominidBlocks.FOSSILIZED_STONE_SLAB);
        blockItem(HominidBlocks.POLISHED_FOSSILIZED_STONE_SLAB);
        blockItem(HominidBlocks.FOSSILIZED_STONE_BRICK_SLAB);

        wallBlock(HominidBlocks.FOSSILIZED_STONE_WALL.get(), blockTexture(HominidBlocks.FOSSILIZED_STONE.get()));
        wallBlock(HominidBlocks.POLISHED_FOSSILIZED_STONE_WALL.get(), blockTexture(HominidBlocks.POLISHED_FOSSILIZED_STONE.get()));
        wallBlock(HominidBlocks.FOSSILIZED_STONE_BRICK_WALL.get(), blockTexture(HominidBlocks.FOSSILIZED_STONE_BRICKS.get()));
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));

    }
    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("hominid:block/" + deferredBlock.getId().getPath()));
    }
    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("hominid:block/" + deferredBlock.getId().getPath() + appendix));
    }
}