package com.alganaut.hominid.registry.item;


import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.item.GasTank;
import com.alganaut.hominid.registry.HominidEntityCreator;
import com.alganaut.hominid.registry.HominidSounds;
import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class HominidItems {
    public static final DeferredRegister.Items  ITEMS = DeferredRegister.createItems(Hominid.MODID);

    public static final ItemEntry<Item> MELLIFIED_SPAWN_EGG = spawnEggOf(HominidEntityCreator.MELLIFIED);
    public static final ItemEntry<Item> INCENDIARY_SPAWN_EGG = spawnEggOf(HominidEntityCreator.INCENDIARY);
    public static final ItemEntry<Item> FAMISHED_SPAWN_EGG = spawnEggOf(HominidEntityCreator.FAMISHED);
    public static final ItemEntry<Item> JUGGERNAUT_SPAWN_EGG = spawnEggOf(HominidEntityCreator.JUGGERNAUT);
    public static final ItemEntry<Item> BELLMAN_SPAWN_EGG = spawnEggOf(HominidEntityCreator.BELLMAN);
    public static final ItemEntry<Item> FOSSILIZED_SPAWN_EGG = spawnEggOf(HominidEntityCreator.FOSSILIZED);
    public static final ItemEntry<Item> VAMPIRE_SPAWN_EGG = spawnEggOf(HominidEntityCreator.VAMPIRE);

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

    // Egg itself is registered by .spawnEgg() in HominidEntityCreator
    private static ItemEntry<Item> spawnEggOf(EntityEntry<?> entity) {
        return ItemEntry.cast(Hominid.REGISTRATE.get().get(entity.getId().getPath() + "_spawn_egg", Registries.ITEM));
    }
}
