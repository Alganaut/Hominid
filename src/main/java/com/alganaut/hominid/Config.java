package com.alganaut.hominid;

import java.util.*;
import java.util.function.Supplier;

import com.alganaut.hominid.entity.bellman.Bellman;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    static final ModConfigSpec SPEC = BUILDER.build();
    private static EntityType<?>[] bellmanSummons;

    private static final Supplier<ModConfigSpec.ConfigValue<List<? extends String>>> BELLMAN_SUMMONS = () -> BUILDER
            .comment("Entities the bellman can summon.")
            .defineListAllowEmpty("Bellman Summons", Bellman.SUPPORTED_SUMMONS, Config::validateSummon);

    // Should spawn?
    public static final ModConfigSpec.ConfigValue<Boolean> BELLMAN;
    public static final ModConfigSpec.ConfigValue<Boolean> VAMPIRE;
    public static final ModConfigSpec.ConfigValue<Boolean> FAMISHED;
    public static final ModConfigSpec.ConfigValue<Boolean> FOSSILIZED;
    public static final ModConfigSpec.ConfigValue<Boolean> INCENDIARY;
    public static final ModConfigSpec.ConfigValue<Boolean> JUGGERNAUT;
    public static final ModConfigSpec.ConfigValue<Boolean> MELLIFIED;

    static {
        BELLMAN = makeConfig("Bellman", BELLMAN_SUMMONS);
        FAMISHED = makeConfig("Famished");
        VAMPIRE = makeConfig("Vampire");
        FOSSILIZED = makeConfig("Fossilized");
        INCENDIARY = makeConfig("Incendiary");
        JUGGERNAUT = makeConfig("Juggernaut");
        MELLIFIED = makeConfig("Mellified");
    }

    private static ModConfigSpec.ConfigValue<Boolean> makeConfig(String name) {
        return makeConfig(name, null);
    }

    private static ModConfigSpec.ConfigValue<Boolean> makeConfig(String name, Supplier<?> supplier) {
        BUILDER.push(name);
        var i = BUILDER.comment(getDescription(name)).define("enabled", true);
        if (supplier != null) {
            supplier.get();
        }
        BUILDER.pop();
        return i;
    }

    private static String getDescription(String name) {
        return "Whether " + name + "'s should spawn";
    }

    private static boolean validateSummon(final Object obj) {
        return obj instanceof String entity && ResourceLocation.tryParse(entity) != null;
    }
    public static EntityType<?>[] getBellmanSummons() {
        if (bellmanSummons == null) {
            bellmanSummons = BELLMAN_SUMMONS.get().get().stream()
                    .map(ResourceLocation::tryParse)
                    .filter(Objects::nonNull)
                    .map(BuiltInRegistries.ENTITY_TYPE::getOptional)
                    .flatMap(Optional::stream)
                    .distinct()
                    .toArray(EntityType<?>[]::new);
        }
        return bellmanSummons;
    }
}
