package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.layer.HominidModelLayers;
import com.alganaut.hominid.registry.entity.custom.Vampire;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;

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