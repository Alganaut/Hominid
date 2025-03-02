package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.custom.Mellified;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MellifiedRenderer  extends MobRenderer<Mellified, MellifiedModel<Mellified>> {
    public MellifiedRenderer(EntityRendererProvider.Context context) {
        super(context, new MellifiedModel<>(context.bakeLayer(MellifiedModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Mellified mellified) {
        return Hominid.id("textures/entity/mellified/mellified.png");
    }
}