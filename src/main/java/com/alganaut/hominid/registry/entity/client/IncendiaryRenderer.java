package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.layer.ModModelLayers;
import com.alganaut.hominid.registry.entity.custom.Incendiary;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class IncendiaryRenderer  extends MobRenderer<Incendiary, IncendiaryModel<Incendiary>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/incendiary/incendiary.png");
    public IncendiaryRenderer(EntityRendererProvider.Context context) {
        super(context, new IncendiaryModel<>(context.bakeLayer(ModModelLayers.INCENDIARY)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Incendiary incendiary) {
        return LOCATION;
    }
}