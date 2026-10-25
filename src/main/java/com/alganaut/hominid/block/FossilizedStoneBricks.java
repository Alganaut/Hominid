package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FossilizedStoneBricks extends Block {
    public static final String id = "fossilized_stone_bricks";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public FossilizedStoneBricks(Properties unused) {
        super(properties);
    }
}
