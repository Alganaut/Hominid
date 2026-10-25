package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FossilizedStoneSlab extends SlabBlock {
    public static final String id = "fossilized_stone_slab";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public FossilizedStoneSlab(Properties unused) {
        super(properties);
    }
}
