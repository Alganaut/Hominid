package com.alganaut.hominid.registry.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class GasTank extends Item {
    int burntime = 0;
    public GasTank(Properties properties, int burnTime) {
        super(properties);
        this.burntime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType){
        return this.burntime;
    }
}
