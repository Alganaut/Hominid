package com.alganaut.hominid.registry.misc;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.item.HominidItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HominidCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Hominid.MODID);

    static {
        CREATIVE_MODE_TAB.register("hominid_tab",
                () -> CreativeModeTab.builder()
                        .icon(() -> new ItemStack(HominidItems.GASOLINE_TANK.get()))
                        .title(Component.translatable("creativetab.hominid.hominid"))
                        .displayItems((displayParameters, output) -> {
                            Hominid.REGISTRATE.get().getAll(Registries.ITEM).stream()
                                    .filter(item -> item.get() instanceof SpawnEggItem)
                                    .forEach(egg -> output.accept(egg.get()));
                            HominidItems.ITEMS.getEntries().forEach(item -> {
                                if (item.get() != HominidItems.SLAB.get()) {
                                    output.accept(item.get());
                                }
                            });
                            Hominid.REGISTRATE.get().getAll(Registries.BLOCK).forEach(block -> output.accept(block.get()));
                        })
                        .build()
        );
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
