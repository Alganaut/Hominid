package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PolishedFossilizedStoneWall extends WallBlock {
    public static final String id = "polished_fossilized_stone_wall";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public PolishedFossilizedStoneWall(Properties unused) {
        super(properties);
    }
}
