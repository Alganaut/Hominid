package com.alganaut.hominid.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.client.entity.bellman.BellmanModel;
import com.alganaut.hominid.client.entity.famished.FamishedModel;
import com.alganaut.hominid.client.entity.fossilized.FossilizedModel;
import com.alganaut.hominid.client.entity.incendiary.IncendiaryModel;
import com.alganaut.hominid.client.entity.juggernaut.JuggernautModel;
import com.alganaut.hominid.client.entity.layer.HominidModelLayers;
import com.alganaut.hominid.client.entity.mellified.MellifiedModel;
import com.alganaut.hominid.client.entity.vampire.VampireModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Hominid.MODID, value = Dist.CLIENT)
public class HominidEntityRenderers {

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
