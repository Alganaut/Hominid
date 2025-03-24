package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.layer.HominidModelLayers;
import com.alganaut.hominid.registry.entity.custom.Mellified;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MellifiedRenderer  extends MobRenderer<Mellified, MellifiedModel<Mellified>> {
    private static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/mellified/mellified.png");
    private static final ResourceLocation HONEY = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/mellified/mellified_overlay.png");
    public MellifiedRenderer(EntityRendererProvider.Context context) {
        super(context, new MellifiedModel<>(context.bakeLayer(HominidModelLayers.MELLIFIED)), 0.5f);
        this.addLayer(new MellifiedHoneyLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Mellified mellified) {
        return LOCATION;
    }

    private static class MellifiedHoneyLayer<T extends Mellified, M extends EntityModel<T>> extends RenderLayer<T, M> {
        private final ResourceLocation layerTexture = HONEY;

        public MellifiedHoneyLayer(RenderLayerParent<T, M> renderer) {
            super(renderer);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            RenderType renderType = RenderType.entityTranslucent(layerTexture);
            VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }
}