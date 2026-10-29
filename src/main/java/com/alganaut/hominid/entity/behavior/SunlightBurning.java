package com.alganaut.hominid.entity.behavior;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;

public final class SunlightBurning {
    private SunlightBurning() {}

    public static void apply(Mob mob) {
        ItemStack helmet = mob.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty()) {
            mob.igniteForSeconds(8.0F);
            return;
        }

        if (!helmet.isDamageableItem()) {
            return;
        }

        helmet.setDamageValue(helmet.getDamageValue() + mob.getRandom().nextInt(2));
        if (helmet.getDamageValue() >= helmet.getMaxDamage()) {
            mob.onEquippedItemBroken(helmet.getItem(), EquipmentSlot.HEAD);
            mob.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
        }
    }
}