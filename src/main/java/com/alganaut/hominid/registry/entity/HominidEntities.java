package com.alganaut.hominid.registry.entity;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.custom.Mellified;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class HominidEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            BuiltInRegistries.ENTITY_TYPE,
           Hominid.MODID
    );

    public static final Supplier<EntityType<Mellified>> MELLIFIED = registerEntity(
            "mellified",
            EntityType.Builder.of(Mellified::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.6F)
    );

    private static <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(
                name,
                () -> builder.build(name)
        );
    }
}
