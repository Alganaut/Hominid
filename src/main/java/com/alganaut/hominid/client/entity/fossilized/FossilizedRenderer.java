package com.alganaut.hominid.client.entity.fossilized;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.client.entity.layer.HominidModelLayers;
import com.alganaut.hominid.entity.fossilized.Fossilized;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FossilizedRenderer extends MobRenderer<Fossilized, FossilizedModel<Fossilized>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/fossilized/fossilized.png");
    public FossilizedRenderer(EntityRendererProvider.Context context) {
        super(context, new FossilizedModel<>(context.bakeLayer(HominidModelLayers.FOSSILIZED)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(Fossilized fossilized) {
        return LOCATION;
    }
}
