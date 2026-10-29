package com.alganaut.hominid.registry;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.block.ChiseledFossilizedStoneBricks;
import com.alganaut.hominid.block.CrackedFossilizedStoneBricks;
import com.alganaut.hominid.block.FossilizedStoneBricks;
import com.alganaut.hominid.block.FossilizedStoneBrickSlab;
import com.alganaut.hominid.block.FossilizedStoneBrickStairs;
import com.alganaut.hominid.block.FossilizedStoneBrickWall;
import com.alganaut.hominid.block.FossilizedStone;
import com.alganaut.hominid.block.FossilizedStonePillar;
import com.alganaut.hominid.block.FossilizedStoneSlab;
import com.alganaut.hominid.block.FossilizedStoneStairs;
import com.alganaut.hominid.block.FossilizedStoneWall;
import com.alganaut.hominid.block.PolishedFossilizedStone;
import com.alganaut.hominid.block.PolishedFossilizedStoneSlab;
import com.alganaut.hominid.block.PolishedFossilizedStoneStairs;
import com.alganaut.hominid.block.PolishedFossilizedStoneWall;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class HominidBlocks {

    //FOSSILIZED STONE

    public static final BlockEntry<FossilizedStone> FOSSILIZED_STONE = block(FossilizedStone.id, FossilizedStone::new)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    public static final BlockEntry<FossilizedStoneStairs> FOSSILIZED_STONE_STAIRS = block(FossilizedStoneStairs.id, FossilizedStoneStairs::new)
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(FOSSILIZED_STONE.get())))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.STAIRS)
            .simpleItem()
            .register();

    public static final BlockEntry<FossilizedStoneSlab> FOSSILIZED_STONE_SLAB = block(FossilizedStoneSlab.id, FossilizedStoneSlab::new)
            .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), prov.blockTexture(FOSSILIZED_STONE.get()), prov.blockTexture(FOSSILIZED_STONE.get())))
            .loot((tables, block) -> tables.add(block, tables.createSlabItemTable(block)))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.SLABS)
            .simpleItem()
            .register();

    public static final BlockEntry<FossilizedStoneWall> FOSSILIZED_STONE_WALL = block(FossilizedStoneWall.id, FossilizedStoneWall::new)
            .blockstate((ctx, prov) -> prov.wallBlock(ctx.getEntry(), prov.blockTexture(FOSSILIZED_STONE.get())))
            .tag(BlockTags.WALLS)
            .item()
            .model((ctx, prov) -> prov.wallInventory(ctx.getName(), prov.modLoc("block/" + FossilizedStone.id)))
            .build()
            .register();

    // Blockstate and models are hand-written in src/main/resources
    public static final BlockEntry<FossilizedStonePillar> FOSSILIZED_STONE_PILLAR = block(FossilizedStonePillar.id, FossilizedStonePillar::new)
            .blockstate(NonNullBiConsumer.noop())
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .model(NonNullBiConsumer.noop())
            .build()
            .register();

    public static final BlockEntry<PolishedFossilizedStone> POLISHED_FOSSILIZED_STONE = block(PolishedFossilizedStone.id, PolishedFossilizedStone::new)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    public static final BlockEntry<PolishedFossilizedStoneStairs> POLISHED_FOSSILIZED_STONE_STAIRS = block(PolishedFossilizedStoneStairs.id, PolishedFossilizedStoneStairs::new)
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(POLISHED_FOSSILIZED_STONE.get())))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.STAIRS)
            .simpleItem()
            .register();

    public static final BlockEntry<PolishedFossilizedStoneSlab> POLISHED_FOSSILIZED_STONE_SLAB = block(PolishedFossilizedStoneSlab.id, PolishedFossilizedStoneSlab::new)
            .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), prov.blockTexture(POLISHED_FOSSILIZED_STONE.get()), prov.blockTexture(POLISHED_FOSSILIZED_STONE.get())))
            .loot((tables, block) -> tables.add(block, tables.createSlabItemTable(block)))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.SLABS)
            .simpleItem()
            .register();

    public static final BlockEntry<PolishedFossilizedStoneWall> POLISHED_FOSSILIZED_STONE_WALL = block(PolishedFossilizedStoneWall.id, PolishedFossilizedStoneWall::new)
            .blockstate((ctx, prov) -> prov.wallBlock(ctx.getEntry(), prov.blockTexture(POLISHED_FOSSILIZED_STONE.get())))
            .tag(BlockTags.WALLS)
            .item()
            .model((ctx, prov) -> prov.wallInventory(ctx.getName(), prov.modLoc("block/" + PolishedFossilizedStone.id)))
            .build()
            .register();

    public static final BlockEntry<FossilizedStoneBricks> FOSSILIZED_STONE_BRICKS = block(FossilizedStoneBricks.id, FossilizedStoneBricks::new)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    public static final BlockEntry<CrackedFossilizedStoneBricks> CRACKED_FOSSILIZED_STONE_BRICKS = block(CrackedFossilizedStoneBricks.id, CrackedFossilizedStoneBricks::new)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    // Blockstate and models are hand-written in src/main/resources
    public static final BlockEntry<ChiseledFossilizedStoneBricks> CHISELED_FOSSILIZED_STONE_BRICKS = block(ChiseledFossilizedStoneBricks.id, ChiseledFossilizedStoneBricks::new)
            .blockstate(NonNullBiConsumer.noop())
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .model(NonNullBiConsumer.noop())
            .build()
            .register();

    public static final BlockEntry<FossilizedStoneBrickStairs> FOSSILIZED_STONE_BRICK_STAIRS = block(FossilizedStoneBrickStairs.id, FossilizedStoneBrickStairs::new)
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(FOSSILIZED_STONE_BRICKS.get())))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.STAIRS)
            .simpleItem()
            .register();

    public static final BlockEntry<FossilizedStoneBrickSlab> FOSSILIZED_STONE_BRICK_SLAB = block(FossilizedStoneBrickSlab.id, FossilizedStoneBrickSlab::new)
            .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), prov.blockTexture(FOSSILIZED_STONE_BRICKS.get()), prov.blockTexture(FOSSILIZED_STONE_BRICKS.get())))
            .loot((tables, block) -> tables.add(block, tables.createSlabItemTable(block)))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.SLABS)
            .simpleItem()
            .register();

    public static final BlockEntry<FossilizedStoneBrickWall> FOSSILIZED_STONE_BRICK_WALL = block(FossilizedStoneBrickWall.id, FossilizedStoneBrickWall::new)
            .blockstate((ctx, prov) -> prov.wallBlock(ctx.getEntry(), prov.blockTexture(FOSSILIZED_STONE_BRICKS.get())))
            .tag(BlockTags.WALLS)
            .item()
            .model((ctx, prov) -> prov.wallInventory(ctx.getName(), prov.modLoc("block/" + FossilizedStoneBricks.id)))
            .build()
            .register();

    // Loads this class so the entries above are registered with Registrate during mod construction
    public static void register() {
    }

    private static <T extends Block> BlockBuilder<T, Registrate> block(String id, NonNullFunction<BlockBehaviour.Properties, T> factory) {
        // Translations stay in the hand-written en_us.json; a generated one would collide with it
        return Hominid.REGISTRATE.get().block(id, factory)
                .setData(ProviderType.LANG, NonNullBiConsumer.noop());
    }
}
