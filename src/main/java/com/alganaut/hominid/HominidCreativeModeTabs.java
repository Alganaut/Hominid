package com.alganaut.hominid;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
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

    public static final Supplier<CreativeModeTab> HOMINID_TAB = CREATIVE_MODE_TAB.register(
            "hominid_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Items.ROTTEN_FLESH))
                    .title(Component.translatable("creativetab.hominid.hominid"))
                    .displayItems((itemDisplayParameters, output) -> acceptItems(
                            output

                    ))
                    .build()
    );

    private static void acceptItems(CreativeModeTab.Output output, ItemLike... items) {
        for (var item : items)
            output.accept(item);
    }

    private static void acceptItems(BuildCreativeModeTabContentsEvent event, ItemLike... items) {
        for (var item : items)
            event.accept(item);
    }

    public static void addCreative(Event event) {
    }
}
