package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ChiseledFossilizedStoneBricks extends Block {
    public static final String id = "chiseled_fossilized_stone_bricks";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public ChiseledFossilizedStoneBricks(Properties unused) {
        super(properties);
    }
}
