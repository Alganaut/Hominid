package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.layer.HominidModelLayers;
import com.alganaut.hominid.registry.entity.custom.HuskEntity;
import com.alganaut.hominid.registry.entity.custom.StrayEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class StrayRenderer extends MobRenderer<StrayEntity, StrayModel<StrayEntity>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/stray/stray.png");
    public StrayRenderer(EntityRendererProvider.Context context) {
            super(context, new StrayModel<>(context.bakeLayer(HominidModelLayers.STRAY)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(StrayEntity husk) {
        return LOCATION;
    }
}