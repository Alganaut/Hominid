package com.alganaut.hominid.registry.item;

import net.minecraft.client.Minecraft;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.event.level.NoteBlockEvent;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class GasTank extends Item {
    private static final int MAX_USES = 3;
    private static final int BURN_TIME_PER_USE = 6400;

    public GasTank(Properties properties) {
        super(properties.durability(MAX_USES));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        if (itemStack.getDamageValue() < MAX_USES) {
            return BURN_TIME_PER_USE;
        }
        return 0;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.getDamageValue() > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13.0F - (13.0F * stack.getDamageValue() / (float) MAX_USES));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xFFAA00;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return stack.getDamageValue() < MAX_USES - 1;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        if (stack.getDamageValue() < MAX_USES - 1) {
            ItemStack newStack = stack.copy();
            newStack.setDamageValue(stack.getDamageValue() + 1);
            return newStack;
        } else {
            return ItemStack.EMPTY;
        }
    }
}