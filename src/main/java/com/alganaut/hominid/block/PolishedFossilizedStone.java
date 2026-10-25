package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PolishedFossilizedStone extends Block {
    public static final String id = "polished_fossilized_stone";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public PolishedFossilizedStone(Properties unused) {
        super(properties);
    }
}
