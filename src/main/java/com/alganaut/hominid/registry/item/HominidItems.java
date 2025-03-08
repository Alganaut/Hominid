package com.alganaut.hominid.registry.item;


import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.HominidEntityCreator;
import com.alganaut.hominid.registry.sound.HominidSounds;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HominidItems {
    public static final DeferredRegister.Items  ITEMS = DeferredRegister.createItems(Hominid.MODID);

    public static final DeferredItem<Item> MELLIFIED_SPAWN_EGG = ITEMS.register("mellified_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.MELLIFIED, 0xefe9d1, 0xf3b34a,
                    new Item.Properties()));

    public static final DeferredItem<Item> INCENDIARY_SPAWN_EGG = ITEMS.register("incendiary_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.INCENDIARY, 0x272f27, 0x624337,
                    new Item.Properties()));

    public static final DeferredItem<Item> FAMISHED_SPAWN_EGG = ITEMS.register("famished_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.FAMISHED, 0x7a725e, 0x413935,
                    new Item.Properties()));

    public static final DeferredItem<Item> JUGGERNAUT_SPAWN_EGG = ITEMS.register("juggernaut_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.JUGGERNAUT, 0x6f625d, 0x541635,
                    new Item.Properties()));

    public static final DeferredItem<Item> CHARRED_MUSIC_DISC = ITEMS.register("charred_music_disc",
            () -> new Item(new Item.Properties().jukeboxPlayable(HominidSounds.CHARRED_KEY).stacksTo(1)));

    public static void register (IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
