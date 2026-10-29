package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FossilizedStoneBrickStairs extends StairBlock {
    public static final String id = "fossilized_stone_brick_stairs";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public FossilizedStoneBrickStairs(Properties unused) {
        super(HominidBlocks.FOSSILIZED_STONE_BRICKS.get().defaultBlockState(), properties);
    }
}
