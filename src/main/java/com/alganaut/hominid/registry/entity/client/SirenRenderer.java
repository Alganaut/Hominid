package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.layer.HominidModelLayers;
import com.alganaut.hominid.registry.entity.custom.Famished;
import com.alganaut.hominid.registry.entity.custom.Siren;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SirenRenderer extends MobRenderer<Siren, SirenModel<Siren>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/siren/siren.png");
    public SirenRenderer(EntityRendererProvider.Context context) {
        super(context, new SirenModel<>(context.bakeLayer(HominidModelLayers.SIREN)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Siren siren) {
        return LOCATION;
    }
}