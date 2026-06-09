package com.alganaut.hominid.registry.block;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class HominidBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Hominid.MODID);

    //FOSSILIZED STONE

    public static final DeferredBlock<Block> FOSSILIZED_STONE = registerBlock("fossilized_stone",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).strength(3.0f, 6.0f).requiresCorrectToolForDrops()));

    public static final DeferredBlock<StairBlock> FOSSILIZED_STONE_STAIRS = registerBlock("fossilized_stone_stairs",
            () -> new StairBlock(HominidBlocks.FOSSILIZED_STONE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_STONE.get())));

    public static final DeferredBlock<SlabBlock> FOSSILIZED_STONE_SLAB = registerBlock("fossilized_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_STONE.get())));

    public static final DeferredBlock<Block> POLISHED_FOSSILIZED_STONE = registerBlock("polished_fossilized_stone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_STONE.get())));

    public static final DeferredBlock<StairBlock> POLISHED_FOSSILIZED_STONE_STAIRS = registerBlock("polished_fossilized_stone_stairs",
            () -> new StairBlock(HominidBlocks.POLISHED_FOSSILIZED_STONE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_STONE.get())));

    public static final DeferredBlock<SlabBlock> POLISHED_FOSSILIZED_STONE_SLAB = registerBlock("polished_fossilized_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_STONE.get())));

    public static final DeferredBlock<Block> FOSSILIZED_STONE_BRICKS = registerBlock("fossilized_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_STONE.get())));

    public static final DeferredBlock<StairBlock> FOSSILIZED_STONE_BRICK_STAIRS = registerBlock("fossilized_stone_brick_stairs",
            () -> new StairBlock(HominidBlocks.FOSSILIZED_STONE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_STONE.get())));

    public static final DeferredBlock<SlabBlock> FOSSILIZED_STONE_BRICK_SLAB = registerBlock("fossilized_stone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(FOSSILIZED_STONE.get())));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        HominidItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> DeferredBlock<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
}