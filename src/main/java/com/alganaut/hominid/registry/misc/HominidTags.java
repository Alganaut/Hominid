package com.alganaut.hominid.registry.misc;

import com.alganaut.hominid.Hominid;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class HominidTags {

    public static final TagKey<EntityType<?>>
            BELLMAN_SPAWNABLE = registerEntityTag("bellman_spawnable");

    private static TagKey<EntityType<?>> registerEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, Hominid.id(name));
    }
}
