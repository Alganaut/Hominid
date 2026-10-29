package com.alganaut.hominid.block;

import com.alganaut.hominid.registry.HominidBlocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FossilizedStonePillar extends RotatedPillarBlock {
    public static final String id = "fossilized_stone_pillar";

    public static final Properties properties = BlockBehaviour.Properties.ofFullCopy(HominidBlocks.FOSSILIZED_STONE.get());

    public FossilizedStonePillar(Properties unused) {
        super(properties);
    }
}
