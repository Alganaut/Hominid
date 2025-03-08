package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.layer.HominidModelLayers;
import com.alganaut.hominid.registry.entity.custom.Juggernaut;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class JuggernautRenderer extends MobRenderer<Juggernaut, JuggernautModel<Juggernaut>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/juggernaut/juggernaut.png");
    public JuggernautRenderer(EntityRendererProvider.Context context) {
            super(context, new JuggernautModel<>(context.bakeLayer(HominidModelLayers.JUGGERNAUT)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Juggernaut juggernaut) {
        return LOCATION;
    }
}