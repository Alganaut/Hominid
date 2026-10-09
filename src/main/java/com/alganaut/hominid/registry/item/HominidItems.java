package com.alganaut.hominid.registry.item;


import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.HominidEntityCreator;
import com.alganaut.hominid.registry.sound.HominidSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HominidItems {
    public static final DeferredRegister.Items  ITEMS = DeferredRegister.createItems(Hominid.MODID);

    public static final DeferredItem<Item> MELLIFIED_SPAWN_EGG = ITEMS.register("mellified_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.MELLIFIED, 0xab781e, 0xfdefd0,
                    new Item.Properties()));

    public static final DeferredItem<Item> INCENDIARY_SPAWN_EGG = ITEMS.register("incendiary_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.INCENDIARY, 0x645633, 0x130407,
                    new Item.Properties()));

    public static final DeferredItem<Item> FAMISHED_SPAWN_EGG = ITEMS.register("famished_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.FAMISHED, 0x88381b, 0xc9a681,
                    new Item.Properties()));

    public static final DeferredItem<Item> JUGGERNAUT_SPAWN_EGG = ITEMS.register("juggernaut_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.JUGGERNAUT, 0x673737, 0xb0b4a3,
                    new Item.Properties()));

    public static final DeferredItem<Item> BELLMAN_SPAWN_EGG = ITEMS.register("bellman_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.BELLMAN, 0x554739, 0xb0b4a3,
                    new Item.Properties()));

    public static final DeferredItem<Item> FOSSILIZED_SPAWN_EGG = ITEMS.register("fossilized_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.FOSSILIZED, 0x7e7a56, 0x5b5344,
                    new Item.Properties()));
    public static final DeferredItem<Item> VAMPIRE_SPAWN_EGG = ITEMS.register("vampire_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.VAMPIRE, 0xccc5ae, 0xdd563f,
                    new Item.Properties()));

    public static final DeferredItem<Item> FAMISHED_STOMACH = ITEMS.register("famished_stomach",
            () -> new Item(new Item.Properties().food(HominidFoodItems.FAMISHED_STOMACH)));

    public static final DeferredItem<Item> GASOLINE_TANK = ITEMS.register("gasoline_tank",
            () -> new GasTank(new Item.Properties().durability(3).stacksTo(1)));

    public static final DeferredItem<Item> MUSIC_DISC_HEMATOMA = ITEMS.register("music_disc_hematoma",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).jukeboxPlayable(HominidSounds.HEMATOMA_KEY).stacksTo(1)));

    public static final DeferredItem<Item> REMAINS_SMITHING_TEMPLATE = ITEMS.register("remains_armor_trim_smithing_template",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "remains")));



    // DONT EDIT
    public static final DeferredItem<Item> SLAB = ITEMS.register("slab",
            () -> new Item(new Item.Properties().durability(3).stacksTo(1)));

    public static void register (IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
