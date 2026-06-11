package com.alganaut.hominid.registry.datagen;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.block.HominidBlocks;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class HominidItemModelProvider extends ItemModelProvider {
    public HominidItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Hominid.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(HominidItems.MUSIC_DISC_HEMATOMA.get());
        basicItem(HominidItems.REMAINS_SMITHING_TEMPLATE.get());
        basicItem(HominidItems.GASOLINE_TANK.get());
        basicItem(HominidItems.FAMISHED_STOMACH.get());

        wallItem(HominidBlocks.FOSSILIZED_STONE_WALL, HominidBlocks.FOSSILIZED_STONE);
        wallItem(HominidBlocks.POLISHED_FOSSILIZED_STONE_WALL, HominidBlocks.POLISHED_FOSSILIZED_STONE);
        wallItem(HominidBlocks.FOSSILIZED_STONE_BRICK_WALL, HominidBlocks.FOSSILIZED_STONE_BRICKS);

        withExistingParent(HominidItems.JUGGERNAUT_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(HominidItems.INCENDIARY_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(HominidItems.BELLMAN_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(HominidItems.FAMISHED_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(HominidItems.VAMPIRE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(HominidItems.FOSSILIZED_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(HominidItems.MELLIFIED_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", ResourceLocation.fromNamespaceAndPath(Hominid.MODID,
                        "block/" + baseBlock.getId().getPath()));
    }
}