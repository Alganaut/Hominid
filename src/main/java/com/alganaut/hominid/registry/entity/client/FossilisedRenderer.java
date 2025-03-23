package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.layer.HominidModelLayers;
import com.alganaut.hominid.registry.entity.custom.Fossilised;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FossilisedRenderer extends MobRenderer<Fossilised, FossilisedModel<Fossilised>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/fossilised/fossilised.png");
    public FossilisedRenderer(EntityRendererProvider.Context context) {
        super(context, new FossilisedModel<>(context.bakeLayer(HominidModelLayers.FOSSILISED)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(Fossilised fossilised) {
        return LOCATION;
    }
}