package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FossilizedStoneBrickSlab extends SlabBlock {
    public static final String id = "fossilized_stone_brick_slab";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public FossilizedStoneBrickSlab(Properties unused) {
        super(properties);
    }
}
