package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.client.animations.HuskAnimations;
import com.alganaut.hominid.registry.entity.client.animations.JuggernautAnimations;
import com.alganaut.hominid.registry.entity.custom.HuskEntity;
import com.alganaut.hominid.registry.entity.custom.Juggernaut;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class HuskModel<T extends HuskEntity> extends HierarchicalModel<T> {
    private final ModelPart husk;
    private final ModelPart torso;
    private final ModelPart head;

    public HuskModel(ModelPart root) {
        this.husk = root.getChild("husk");
        this.torso = this.husk.getChild("body");
        this.head = this.torso.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition husk = partdefinition.addOrReplaceChild("husk", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = husk.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 16).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -13.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F))
                .texOffs(32, 0).addBox(0.0F, -1.0F, -4.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 44).addBox(0.0F, -2.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -10.0F, 0.0F, -0.9163F, 0.0F, 0.0F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 27).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -10.0F, 0.0F, -0.9163F, 0.0F, 0.0F));

        PartDefinition left_leg = husk.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(40, 27).addBox(-0.9F, 3.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, -16.0F, 0.0F));

        PartDefinition right_leg = husk.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-3.1F, 3.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.9F, -16.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(HuskEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);


        this.animateWalk(HuskAnimations.WALK, limbSwing, limbSwingAmount, 4f, 54);
        this.animate(entity.idleAnimationState,HuskAnimations.IDLE,ageInTicks, 1f);
        this.animate(entity.attackAnimationState,HuskAnimations.ATTACK,ageInTicks, 1f);

    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        husk.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return husk;
    }
}