package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PolishedFossilizedStoneStairs extends StairBlock {
    public static final String id = "polished_fossilized_stone_stairs";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public PolishedFossilizedStoneStairs(Properties unused) {
        super(HominidBlocks.POLISHED_FOSSILIZED_STONE.get().defaultBlockState(), properties);
    }
}
