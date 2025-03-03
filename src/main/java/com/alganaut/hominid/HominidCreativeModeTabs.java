package com.alganaut.hominid;

import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class HominidCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB,
            Hominid.MODID
    );

    public static final Supplier<CreativeModeTab> ELEMENTALIS_TAB = CREATIVE_MODE_TAB.register(
            "elementalis_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.ZOMBIE_HEAD))
                    .title(Component.translatable("creativetab.hominid.hominid"))
                    .displayItems((itemDisplayParameters, output) -> acceptItems(
                            output,

                            HominidItems.MELLIFIED_SPAWN_EGG,
                            HominidItems.INCENDIARY_SPAWN_EGG
                    ))
                    .build()
    );

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            acceptItems(
                    event
            );
        }

        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            acceptItems(
                    event
            );
        }

        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            acceptItems(
                    event,
                    HominidItems.MELLIFIED_SPAWN_EGG,
                    HominidItems.INCENDIARY_SPAWN_EGG
            );
        }

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            acceptItems(
                    event
            );
        }

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            acceptItems(
                    event
            );
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            acceptItems(
                    event
            );
        }
    }

    private static void acceptItems(CreativeModeTab.Output output, ItemLike... items) {
        for (var item : items)
            output.accept(item);
    }

    private static void acceptItems(BuildCreativeModeTabContentsEvent event, ItemLike... items) {
        for (var item : items)
            event.accept(item);
    }
}