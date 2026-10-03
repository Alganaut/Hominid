package com.alganaut.hominid.client.entity.vampire;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.client.entity.layer.HominidModelLayers;
import com.alganaut.hominid.entity.vampire.Vampire;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class VampireRenderer extends MobRenderer<Vampire, VampireModel<Vampire>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/vampire/vampire.png");
    private static final ResourceLocation AGGRO = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/vampire/vampire_aggro.png");
    public VampireRenderer(EntityRendererProvider.Context context) {
            super(context, new VampireModel<>(context.bakeLayer(HominidModelLayers.VAMPIRE)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Vampire vampire) {
        return vampire.isAggressive() ? AGGRO : LOCATION;
    }
}