package com.alganaut.hominid.registry.entity;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.MellifiedModel;
import com.alganaut.hominid.registry.entity.client.MellifiedRenderer;
import com.alganaut.hominid.registry.entity.client.layer.ModModelLayers;
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
public class ModEntityCreator {
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

    // ATTRIBUTES

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityCreator.MELLIFIED.get(), Mellified.createAttributes().build());
    }

    // RENDERERS

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntityCreator.MELLIFIED.get(), MellifiedRenderer::new);
    }

    // LAYERS

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(ModModelLayers.MELLIFIED, MellifiedModel::createBodyLayer);
    }
}
