package com.alganaut.hominid.block;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.entity.fossilized.Fossilized;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public class FossilizedStone extends Block {
    public static final String id = "fossilized_stone";

    public static final Properties properties = BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_GRAY)
            .strength(3.0f, 6.0f)
            .requiresCorrectToolForDrops();

    public FossilizedStone(Properties unused) {
        super(properties);
    }
}
