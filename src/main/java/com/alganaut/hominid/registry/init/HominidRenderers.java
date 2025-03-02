package com.alganaut.hominid.registry.init;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.HominidEntities;
import com.alganaut.hominid.registry.entity.client.MellifiedModel;
import com.alganaut.hominid.registry.entity.client.MellifiedRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Hominid.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class HominidRenderers
{
    public static final ModelLayerLocation MELLIFIED = new ModelLayerLocation(Hominid.id("mellified"), "main");

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(HominidEntities.MELLIFIED.get(), MellifiedRenderer::new);
    }

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(MELLIFIED, MellifiedModel::createBodyLayer);
    }


}