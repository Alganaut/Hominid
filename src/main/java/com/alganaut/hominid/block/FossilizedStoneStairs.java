package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FossilizedStoneStairs extends StairBlock {
    public static final String id = "fossilized_stone_stairs";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public FossilizedStoneStairs(Properties unused) {
        super(HominidBlocks.FOSSILIZED_STONE.get().defaultBlockState(), properties);
    }
}
