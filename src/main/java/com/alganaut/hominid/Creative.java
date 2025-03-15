package com.alganaut.hominid;

import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import static com.alganaut.hominid.Config.items;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Hominid.MODID)
public class Creative {
    private final BuildCreativeModeTabContentsEvent event;

    @SubscribeEvent
    public static void creativeTabs(BuildCreativeModeTabContentsEvent event) {
        new Creative(event);
    }

    Creative(BuildCreativeModeTabContentsEvent event) {
        this.event = event;

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            insertItems(
                    Items.MUSIC_DISC_PRECIPICE,
                    HominidItems.CHARRED_MUSIC_DISC
            );
            insertItems(
                    Items.FLINT_AND_STEEL,
                    HominidItems.GASOLINE_TANK
            );
        }

        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            insertItems(
                    Items.EVOKER_SPAWN_EGG,
                    HominidItems.FAMISHED_SPAWN_EGG
            );
            insertItems(
                    Items.HUSK_SPAWN_EGG,
                    HominidItems.INCENDIARY_SPAWN_EGG
            );
            insertItems(
                    Items.IRON_GOLEM_SPAWN_EGG,
                    HominidItems.JUGGERNAUT_SPAWN_EGG
            );
            insertItems(
                    Items.MAGMA_CUBE_SPAWN_EGG,
                    HominidItems.MELLIFIED_SPAWN_EGG
            );
        }
    }

    private void insertItems(ItemLike start, ItemLike... items) {
        var previous = start;

        for (var next : items) {
            event.insertAfter(
                    new ItemStack(previous),
                    new ItemStack(next),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            previous = next;
        }
    }
}