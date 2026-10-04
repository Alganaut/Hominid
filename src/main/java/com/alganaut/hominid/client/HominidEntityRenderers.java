package com.alganaut.hominid.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.client.entity.bellman.BellmanModel;
import com.alganaut.hominid.client.entity.bellman.BellmanRenderer;
import com.alganaut.hominid.client.entity.famished.FamishedModel;
import com.alganaut.hominid.client.entity.famished.FamishedRenderer;
import com.alganaut.hominid.client.entity.fossilized.FossilizedModel;
import com.alganaut.hominid.client.entity.fossilized.FossilizedRenderer;
import com.alganaut.hominid.client.entity.incendiary.IncendiaryModel;
import com.alganaut.hominid.client.entity.incendiary.IncendiaryRenderer;
import com.alganaut.hominid.client.entity.juggernaut.JuggernautModel;
import com.alganaut.hominid.client.entity.juggernaut.JuggernautRenderer;
import com.alganaut.hominid.client.entity.layer.HominidModelLayers;
import com.alganaut.hominid.client.entity.mellified.MellifiedModel;
import com.alganaut.hominid.client.entity.mellified.MellifiedRenderer;
import com.alganaut.hominid.client.entity.vampire.VampireModel;
import com.alganaut.hominid.client.entity.vampire.VampireRenderer;
import com.alganaut.hominid.registry.HominidEntityCreator;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Hominid.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HominidEntityRenderers {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(HominidEntityCreator.MELLIFIED.get(), MellifiedRenderer::new);
        event.registerEntityRenderer(HominidEntityCreator.INCENDIARY.get(), IncendiaryRenderer::new);
        event.registerEntityRenderer(HominidEntityCreator.FAMISHED.get(), FamishedRenderer::new);
        event.registerEntityRenderer(HominidEntityCreator.JUGGERNAUT.get(), JuggernautRenderer::new);
        event.registerEntityRenderer(HominidEntityCreator.BELLMAN.get(), BellmanRenderer::new);
        event.registerEntityRenderer(HominidEntityCreator.FOSSILIZED.get(), FossilizedRenderer::new);
        event.registerEntityRenderer(HominidEntityCreator.ROCK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(HominidEntityCreator.VAMPIRE.get(), VampireRenderer::new);
    }

    @SubscribeEvent
    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(HominidModelLayers.MELLIFIED, MellifiedModel::createBodyLayer);
        event.registerLayerDefinition(HominidModelLayers.INCENDIARY, IncendiaryModel::createBodyLayer);
        event.registerLayerDefinition(HominidModelLayers.FAMISHED, FamishedModel::createBodyLayer);
        event.registerLayerDefinition(HominidModelLayers.JUGGERNAUT, JuggernautModel::createBodyLayer);
        event.registerLayerDefinition(HominidModelLayers.BELLMAN, BellmanModel::createBodyLayer);
        event.registerLayerDefinition(HominidModelLayers.FOSSILIZED, FossilizedModel::createBodyLayer);
        event.registerLayerDefinition(HominidModelLayers.VAMPIRE, VampireModel::createBodyLayer);
    }
}
