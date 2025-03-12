package com.alganaut.hominid.registry.item;

import com.alganaut.hominid.registry.block.HominidBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class GasTank extends BlockItem {
    int burntime = 0;
    public GasTank(Properties properties, int burnTime) {
        super(HominidBlocks.GAS.get(), properties);
        this.burntime = burnTime;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemStack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Direction facing = context.getClickedFace();

        if (itemStack.getDamageValue() < itemStack.getMaxDamage()) {
            BlockPos targetPos = pos.relative(facing);
            level.setBlock(targetPos, HominidBlocks.GAS.get().defaultBlockState(), 3);
            itemStack.setDamageValue(itemStack.getDamageValue() + 1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType){
        return this.burntime;
    }
}
