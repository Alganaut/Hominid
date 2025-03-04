package com.alganaut.hominid.registry.event;

import com.alganaut.hominid.registry.effect.HominidEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

public class HominidClientEvents {
    public static void register() {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, HominidClientEvents::onPlayerDrinkHoney);
    }

    @SubscribeEvent
    public static void onPlayerDrinkHoney(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack itemStack = event.getItem();
            Level world = player.level();

            if (!world.isClientSide && itemStack.getItem() == Items.HONEY_BOTTLE) {
                player.addEffect(new MobEffectInstance(Holder.direct(HominidEffects.HONEYED.get()), 400, 0));
            }
        }
    }
}
