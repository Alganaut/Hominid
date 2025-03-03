package com.alganaut.hominid.registry.item;


import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.ModEntityCreator;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HominidItems {
    public static final DeferredRegister.Items  ITEMS = DeferredRegister.createItems(Hominid.MODID);

    public static final DeferredItem<Item> MELLIFIED_SPAWN_EGG = ITEMS.register("mellified_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntityCreator.MELLIFIED, 0xefe9d1, 0xf3b34a,
                    new Item.Properties()));

    public static void register (IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
