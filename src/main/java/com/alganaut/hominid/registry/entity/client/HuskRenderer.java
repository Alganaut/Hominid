package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.layer.HominidModelLayers;
import com.alganaut.hominid.registry.entity.custom.HuskEntity;
import com.alganaut.hominid.registry.entity.custom.Juggernaut;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HuskRenderer extends MobRenderer<HuskEntity, HuskModel<HuskEntity>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/husk/husk.png");
    public HuskRenderer(EntityRendererProvider.Context context) {
            super(context, new HuskModel<>(context.bakeLayer(HominidModelLayers.HUSK)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(HuskEntity husk) {
        return LOCATION;
    }
}