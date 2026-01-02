package com.alganaut.hominid.registry.misc;

import com.alganaut.hominid.Hominid;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class HominidTags {

    public static class EntityType {
        public static final TagKey<EntityType> BELLMAN_SPAWNABLE = createTag("bellman_spawnable");

        private static TagKey<EntityType> createTag(String name) {
            return EntityTypeTags.create(ResourceLocation.fromNamespaceAndPath(Hominid.MODID, name));
        }

    }
}
