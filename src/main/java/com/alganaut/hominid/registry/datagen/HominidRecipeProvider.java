package com.alganaut.hominid.registry.datagen;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class HominidRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public HominidRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        trimSmithing(recipeOutput, HominidItems.REMAINS_SMITHING_TEMPLATE.get(), ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "remains"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HominidItems.REMAINS_SMITHING_TEMPLATE.get(), 2)
                .pattern("DRD")
                .pattern("DSD")
                .pattern("DDD")
                .define('S', Blocks.STONE)
                .define('D', Items.DIAMOND)
                .define('R', HominidItems.REMAINS_SMITHING_TEMPLATE)
                .unlockedBy("has_remains_smithing_template", has(HominidItems.REMAINS_SMITHING_TEMPLATE)).save(recipeOutput);
    }

}
