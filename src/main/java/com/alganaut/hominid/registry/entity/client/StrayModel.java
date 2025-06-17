package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.client.animations.HuskAnimations;
import com.alganaut.hominid.registry.entity.client.animations.StrayAnimations;
import com.alganaut.hominid.registry.entity.custom.HuskEntity;
import com.alganaut.hominid.registry.entity.custom.StrayEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class StrayModel<T extends StrayEntity> extends HierarchicalModel<T> {
    private final ModelPart stray;
    private final ModelPart torso;
    private final ModelPart torso2;
    private final ModelPart head;

    public StrayModel(ModelPart root) {
        this.stray = root.getChild("stray");
        this.torso = this.stray.getChild("torso");
        this.torso2 = this.torso.getChild("torso2");
        this.head = this.torso2.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition stray = partdefinition.addOrReplaceChild("stray", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_leg = stray.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-2.0F, -12.0F, 0.1F));

        PartDefinition right_leg2 = right_leg.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(32, 36).addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-2.0F, 5.0F, -2.1F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg = stray.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(2.0F, -12.0F, 0.1F));

        PartDefinition left_leg2 = left_leg.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(32, 36).mirror().addBox(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 36).mirror().addBox(-2.0F, 5.0F, -2.1F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition torso = stray.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition torso2 = torso.addOrReplaceChild("torso2", CubeListBuilder.create().texOffs(32, 20).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 47).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 13.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = torso2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(8, 68).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition left_arm = torso2.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(24, 36).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 50).mirror().addBox(-1.05F, -2.025F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 50).mirror().addBox(-1.0F, -2.05F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.24F)).mirror(false), PartPose.offsetAndRotation(5.0F, -10.0F, 0.0F, -0.0038F, 0.0872F, -0.0438F));

        PartDefinition right_arm = torso2.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 36).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 50).addBox(-2.975F, -2.025F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 50).addBox(-3.0F, -2.025F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.24F)), PartPose.offsetAndRotation(-5.0F, -10.0F, 0.0F, -0.0038F, -0.0872F, 0.0438F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(StrayEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);


        this.animateWalk(StrayAnimations.WALK, limbSwing, limbSwingAmount, 4f, 54);
        this.animate(entity.idleAnimationState,StrayAnimations.IDLE,ageInTicks, 1f);
        this.animate(entity.attackAnimationState,StrayAnimations.SHOOT,ageInTicks, 1f);

    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        stray.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return stray;
    }
}