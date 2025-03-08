package com.alganaut.hominid.registry.entity;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.*;
import com.alganaut.hominid.registry.entity.client.layer.HominidModelLayers;
import com.alganaut.hominid.registry.entity.custom.Famished;
import com.alganaut.hominid.registry.entity.custom.Incendiary;
import com.alganaut.hominid.registry.entity.custom.Juggernaut;
import com.alganaut.hominid.registry.entity.custom.Mellified;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
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
                    .sized(0.6F, 1.99F)
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
    }

    // RENDERERS

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(HominidEntityCreator.MELLIFIED.get(), MellifiedRenderer::new);
        event.registerEntityRenderer(HominidEntityCreator.INCENDIARY.get(), IncendiaryRenderer::new);
        event.registerEntityRenderer(HominidEntityCreator.FAMISHED.get(), FamishedRenderer::new);
        event.registerEntityRenderer(HominidEntityCreator.JUGGERNAUT.get(), JuggernautRenderer::new);
    }

    // LAYERS

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(HominidModelLayers.MELLIFIED, MellifiedModel::createBodyLayer);
        event.registerLayerDefinition(HominidModelLayers.INCENDIARY, IncendiaryModel::createBodyLayer);
        event.registerLayerDefinition(HominidModelLayers.FAMISHED, FamishedModel::createBodyLayer);
        event.registerLayerDefinition(HominidModelLayers.JUGGERNAUT, JuggernautModel::createBodyLayer);
    }
}
