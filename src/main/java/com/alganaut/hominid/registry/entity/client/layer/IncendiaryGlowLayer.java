package com.alganaut.hominid.registry.entity.client.layer;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.IncendiaryModel;
import com.alganaut.hominid.registry.entity.custom.Incendiary;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class IncendiaryGlowLayer<T extends Incendiary, M extends IncendiaryModel<T>> extends EyesLayer<T, M> {

    private static final RenderType INCENDIARY_GLOW = RenderType.breezeEyes(ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/incendiary/incendiary_glow.png"));

    public IncendiaryGlowLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return INCENDIARY_GLOW;
    }
}
