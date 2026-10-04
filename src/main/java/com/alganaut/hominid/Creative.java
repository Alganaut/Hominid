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

import java.util.LinkedHashMap;
import java.util.Map;

@EventBusSubscriber(modid = Hominid.MODID)
public class Creative {
    private Creative() {}

    @SubscribeEvent
    public static void buildCreativeTabs(BuildCreativeModeTabContentsEvent event) {

        CreativeBuilder builder = new CreativeBuilder();

        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            builder.put(
                    Items.MUSIC_DISC_PRECIPICE,
                    HominidItems.MUSIC_DISC_HEMATOMA
            );
            builder.put(
                    Items.FLINT_AND_STEEL,
                    HominidItems.GASOLINE_TANK
            );
            builder.build(event);
        }

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            builder.put(
                    Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE,
                    HominidItems.REMAINS_SMITHING_TEMPLATE
            );
            builder.build(event);
        }

        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            builder.put(
                    Items.ROTTEN_FLESH,
                    HominidItems.FAMISHED_STOMACH
            );
            builder.build(event);
        }

        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            builder.put(
                    Items.EVOKER_SPAWN_EGG,
                    HominidItems.FAMISHED_SPAWN_EGG
            );
            builder.put(
                    Items.HUSK_SPAWN_EGG,
                    HominidItems.INCENDIARY_SPAWN_EGG
            );
            builder.put(
                    Items.IRON_GOLEM_SPAWN_EGG,
                    HominidItems.JUGGERNAUT_SPAWN_EGG
            );
            builder.put(
                    Items.MAGMA_CUBE_SPAWN_EGG,
                    HominidItems.MELLIFIED_SPAWN_EGG
            );
            builder.put(
                    HominidItems.FAMISHED_SPAWN_EGG,
                    HominidItems.FOSSILIZED_SPAWN_EGG
            );
            builder.put(
                    Items.TURTLE_SPAWN_EGG,
                    HominidItems.VAMPIRE_SPAWN_EGG
            );
            builder.put(
                    Items.BEE_SPAWN_EGG,
                    HominidItems.BELLMAN_SPAWN_EGG
            );
            builder.build(event);
        }

    }

    private static class CreativeBuilder {
        private final Map<ItemLike, ItemLike[]> map = new LinkedHashMap<>();

        public void put(ItemLike item, ItemLike... items) {
            map.put(item, items);
        }

        public void build(BuildCreativeModeTabContentsEvent event) {
            for (var entry : map.entrySet()) {
                ItemLike previous = entry.getKey();

                for (var next : entry.getValue()) {
                    event.insertAfter(
                            new ItemStack(previous),
                            new ItemStack(next),
                            CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                    );

                    previous = next;
                }
            }
        }
    }
}