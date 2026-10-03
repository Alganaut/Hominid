package com.alganaut.hominid.registry;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.entity.bellman.Bellman;
import com.alganaut.hominid.entity.famished.Famished;
import com.alganaut.hominid.entity.fossilized.Fossilized;
import com.alganaut.hominid.entity.fossilized.FossilizedRock;
import com.alganaut.hominid.entity.incendiary.Incendiary;
import com.alganaut.hominid.entity.juggernaut.Juggernaut;
import com.alganaut.hominid.entity.mellified.Mellified;
import com.alganaut.hominid.entity.vampire.Vampire;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = Hominid.MODID, bus = EventBusSubscriber.Bus.MOD)
public class HominidEntityCreator {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            BuiltInRegistries.ENTITY_TYPE,
           Hominid.MODID
    );

    public static final Supplier<EntityType<Mellified>> MELLIFIED = registerEntity(
            "mellified",
            EntityType.Builder.of(Mellified::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.6F)
    );

    public static final Supplier<EntityType<Incendiary>> INCENDIARY = registerEntity(
            "incendiary",
            EntityType.Builder.of(Incendiary::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
    );

    public static final Supplier<EntityType<Famished>> FAMISHED = registerEntity(
            "famished",
            EntityType.Builder.of(Famished::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.99F)
    );

    public static final Supplier<EntityType<Juggernaut>> JUGGERNAUT = registerEntity(
            "juggernaut",
            EntityType.Builder.of(Juggernaut::new, MobCategory.MONSTER)
                    .sized(1F, 2.4F)
    );

    public static final Supplier<EntityType<Bellman>> BELLMAN = registerEntity(
            "bellman",
            EntityType.Builder.of(Bellman::new, MobCategory.MONSTER)
                    .sized(1F, 2.4F)
    );

    public static final Supplier<EntityType<Fossilized>> FOSSILIZED = registerEntity(
            "fossilized",
            EntityType.Builder.of(Fossilized::new, MobCategory.MONSTER)
                    .sized(0.65F, 2.3F)
    );

    public static final Supplier<EntityType<FossilizedRock>> ROCK = registerEntity(
            "rock",
            EntityType.Builder.<FossilizedRock>of(FossilizedRock::new, MobCategory.MISC)
                    .sized(0.8F, 0.3F)
    );

    public static final Supplier<EntityType<Vampire>> VAMPIRE = registerEntity(
            "vampire",
            EntityType.Builder.of(Vampire::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.9F)
    );

    private static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(
                name,
                () -> builder.build(name)
        );
    }

    // ATTRIBUTES

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(HominidEntityCreator.MELLIFIED.get(), Mellified.createAttributes().build());
        event.put(HominidEntityCreator.INCENDIARY.get(), Incendiary.createAttributes().build());
        event.put(HominidEntityCreator.FAMISHED.get(), Famished.createAttributes().build());
        event.put(HominidEntityCreator.JUGGERNAUT.get(), Juggernaut.createAttributes().build());
        event.put(HominidEntityCreator.BELLMAN.get(), Bellman.createAttributes().build());
        event.put(HominidEntityCreator.FOSSILIZED.get(), Fossilized.createAttributes().build());
        event.put(HominidEntityCreator.VAMPIRE.get(), Vampire.createAttributes().build());
    }
}
