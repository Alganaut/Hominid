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

        stairsBlock(HominidBlocks.FOSSILIZED_STONE_STAIRS.get(), blockTexture(HominidBlocks.FOSSILIZED_STONE.get()));
        stairsBlock(HominidBlocks.POLISHED_FOSSILIZED_STONE_STAIRS.get(), blockTexture(HominidBlocks.POLISHED_FOSSILIZED_STONE.get()));
        stairsBlock(HominidBlocks.FOSSILIZED_STONE_BRICK_STAIRS.get(), blockTexture(HominidBlocks.FOSSILIZED_STONE_BRICKS.get()));

        blockItem(HominidBlocks.FOSSILIZED_STONE_STAIRS);
        blockItem(HominidBlocks.POLISHED_FOSSILIZED_STONE_STAIRS);
        blockItem(HominidBlocks.FOSSILIZED_STONE_BRICK_STAIRS);
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