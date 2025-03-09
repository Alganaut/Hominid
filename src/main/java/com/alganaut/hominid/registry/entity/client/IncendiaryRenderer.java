package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.Hominid;
import com.alganaut.hominid.registry.entity.client.layer.HominidModelLayers;
import com.alganaut.hominid.registry.entity.custom.Incendiary;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class IncendiaryRenderer  extends MobRenderer<Incendiary, IncendiaryModel<Incendiary>> {
    private static final ResourceLocation BASE = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/incendiary/incendiary.png");
    private static final ResourceLocation FIRE = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/incendiary/incendiary_ignited.png");
    private static final ResourceLocation GLOW = ResourceLocation.fromNamespaceAndPath(Hominid.MODID, "textures/entity/incendiary/incendiary_glow.png");
    public IncendiaryRenderer(EntityRendererProvider.Context context) {
        super(context, new IncendiaryModel<>(context.bakeLayer(HominidModelLayers.INCENDIARY)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Incendiary incendiary) {
        if (incendiary.isOnFire() && incendiary.isAggressive()) {
            return FIRE;
        }
        return BASE;
    }

    @Override
    public void render(Incendiary entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }
}