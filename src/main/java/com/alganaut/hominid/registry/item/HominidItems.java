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
            () -> new DeferredSpawnEggItem(HominidEntityCreator.MELLIFIED, 0xefe9d1, 0xf3b34a,
                    new Item.Properties()));

    public static final DeferredItem<Item> INCENDIARY_SPAWN_EGG = ITEMS.register("incendiary_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.INCENDIARY, 0x624337, 0x862a25,
                    new Item.Properties()));

    public static final DeferredItem<Item> FAMISHED_SPAWN_EGG = ITEMS.register("famished_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.FAMISHED, 0x7a725e, 0x413935,
                    new Item.Properties()));

    public static final DeferredItem<Item> JUGGERNAUT_SPAWN_EGG = ITEMS.register("juggernaut_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.JUGGERNAUT, 0x6f625d, 0x541635,
                    new Item.Properties()));

    public static final DeferredItem<Item> BELLMAN_SPAWN_EGG = ITEMS.register("bellman_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.BELLMAN, 0x211f1b, 0x898b82,
                    new Item.Properties()));

    public static final DeferredItem<Item> FOSSLISED_SPAWN_EGG = ITEMS.register("fossilised_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.FOSSILIZED, 0x9a9c7a, 0x5b5344,
                    new Item.Properties()));
    public static final DeferredItem<Item> VAMPIRE_SPAWN_EGG = ITEMS.register("vampire_spawn_egg",
            () -> new DeferredSpawnEggItem(HominidEntityCreator.VAMPIRE, 0x6e211f, 0x4d4129,
                    new Item.Properties()));

    public static final DeferredItem<Item> FAMISHED_STOMACH = ITEMS.register("famished_stomach",
            () -> new Item(new Item.Properties().food(HominidFoodItems.FAMISHED_STOMACH)));

    public static final DeferredItem<Item> GASOLINE_TANK = ITEMS.register("gasoline_tank",
            () -> new GasTank(new Item.Properties().durability(3).stacksTo(1)));

    public static final DeferredItem<Item> MUSIC_DISC_CHARRED = ITEMS.register("music_disc_charred",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).jukeboxPlayable(HominidSounds.CHARRED_KEY).stacksTo(1)));

    public static final DeferredItem<Item> REMAINS_SMITHING_TEMPLATE = ITEMS.register("remains_armor_trim_smithing_template",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "remains")));



    // DONT EDIT
    public static final DeferredItem<Item> SLAB = ITEMS.register("slab",
            () -> new Item(new Item.Properties().durability(3).stacksTo(1)));

    public static void register (IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
