package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.custom.Famished;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;

import javax.management.Attribute;

public class FamishedModel<T extends Famished> extends HierarchicalModel<T> {

    private final ModelPart famished;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart head_hungry;

    public FamishedModel(ModelPart root) {
        this.famished = root.getChild("famished");
        this.head = this.famished.getChild("head");
        this.head_hungry = this.famished.getChild("head_hungry");
        this.body = this.famished.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition famished = partdefinition.addOrReplaceChild("famished", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = famished.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition head_hungry = famished.addOrReplaceChild("head_hungry", CubeListBuilder.create().texOffs(28, 22).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(38, 38).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition body = famished.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(24, 16).addBox(-3.0F, 8.0F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition left_arm = famished.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(14, 46).mirror().addBox(0.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -23.0F, 0.0F, -0.9163F, 0.0F, 0.0F));

        PartDefinition right_arm = famished.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 46).addBox(-3.0F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -23.0F, 0.0F, -0.9163F, 0.0F, 0.0F));

        PartDefinition left_leg = famished.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(14, 28).addBox(-0.9F, 2.0F, -2.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, -16.0F, 0.0F));

        PartDefinition right_leg = famished.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-2.1F, 2.0F, -2.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.9F, -16.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Famished entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);


        this.animateWalk(FamishedAnimations.ANIM_FAMISHED_WALK, limbSwing, limbSwingAmount, 4f, 54);
        this.animate(entity.idleAnimationState,FamishedAnimations.ANIM_FAMISHED_IDLE,ageInTicks, 1f);
        this.animate(entity.eatingAnimationState,FamishedAnimations.ANIM_FAMISHED_EAT,ageInTicks, 1f);

        if (entity.getAttributeValue(Attributes.MOVEMENT_SPEED) == 0.27) {
            head.visible = false;
            head_hungry.visible = true;
        } else {
            head.visible = true;
            head_hungry.visible = false;
        }
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
        this.head_hungry.yRot = headYaw * ((float)Math.PI / 180f);
        this.head_hungry.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        famished.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return famished;
    }
}