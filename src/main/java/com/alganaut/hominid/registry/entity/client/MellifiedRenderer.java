package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.layer.ModModelLayers;
import com.alganaut.hominid.registry.entity.custom.Mellified;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MellifiedRenderer  extends MobRenderer<Mellified, MellifiedModel<Mellified>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/mellified/mellified.png");
    public MellifiedRenderer(EntityRendererProvider.Context context) {
        super(context, new MellifiedModel<>(context.bakeLayer(ModModelLayers.MELLIFIED)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Mellified mellified) {
        return LOCATION;
    }
}