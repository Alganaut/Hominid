package com.alganaut.hominid.registry.misc;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.block.HominidBlocks;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class HominidCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Hominid.MODID);

    public static final Supplier<CreativeModeTab> HOMINID_TAB = CREATIVE_MODE_TAB.register("hominid_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(HominidItems.GASOLINE_TANK.get()))
                    .title(Component.translatable("creativetab.hominid.hominid"))
                    .displayItems((displayParameters, output) -> {
                        HominidItems.ITEMS.getEntries().forEach(item -> {
                            if (item.get() != HominidItems.SLAB.get()) {
                                output.accept(item.get());
                            }
                        });
                        HominidBlocks.BLOCKS.getEntries().forEach(block -> output.accept(block.get()));
                    })
                    .build()
    );


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
