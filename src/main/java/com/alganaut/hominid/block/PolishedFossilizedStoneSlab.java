package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PolishedFossilizedStoneSlab extends SlabBlock {
    public static final String id = "polished_fossilized_stone_slab";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public PolishedFossilizedStoneSlab(Properties unused) {
        super(properties);
    }
}
