package com.alganaut.hominid.client.entity.juggernaut;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.client.entity.layer.HominidModelLayers;
import com.alganaut.hominid.entity.juggernaut.Juggernaut;
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
