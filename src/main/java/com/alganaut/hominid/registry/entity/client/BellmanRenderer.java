package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.layer.HominidModelLayers;
import com.alganaut.hominid.registry.entity.custom.Bellman;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BellmanRenderer extends MobRenderer<Bellman, BellmanModel<Bellman>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/bellman/bellman.png");
    public BellmanRenderer(EntityRendererProvider.Context context) {
            super(context, new BellmanModel<>(context.bakeLayer(HominidModelLayers.BELLMAN)), 0.8f);
    }

    @Override
    public ResourceLocation getTextureLocation(Bellman bellman) {
        return LOCATION;
    }
}