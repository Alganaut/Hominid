package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FossilizedStoneBrickWall extends WallBlock {
    public static final String id = "fossilized_stone_brick_wall";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public FossilizedStoneBrickWall(Properties unused) {
        super(properties);
    }
}
