package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.client.animations.MellifiedAnimations;
import com.alganaut.hominid.registry.entity.custom.Mellified;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class MellifiedModel<T extends Mellified> extends HierarchicalModel<T> {

    private final ModelPart mellified;
    private final ModelPart torso;
    private final ModelPart head;

    public MellifiedModel(ModelPart root) {
        this.mellified = root.getChild("mellified");
        this.torso = this.mellified.getChild("torso");
        this.head = this.torso.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition mellified = partdefinition.addOrReplaceChild("mellified", CubeListBuilder.create(), PartPose.offset(3.0F, 25.0F, -1.0F));

        PartDefinition torso = mellified.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -11.0F, -2.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -11.0F, 1.0F));

        PartDefinition body_honey = torso.addOrReplaceChild("body_honey", CubeListBuilder.create().texOffs(32, 15).addBox(-7.0F, -11.0F, -1.0F, 8.0F, 11.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(3.0F, 0.0F, -1.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, -11.0F, 0.0F));

        PartDefinition head_honey = head.addOrReplaceChild("head_honey", CubeListBuilder.create().texOffs(0, 33).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 49).mirror().addBox(0.0F, -2.0341F, -2.2588F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -9.0F, 0.0F, -1.1781F, 0.0F, 0.0F));

        PartDefinition left_arm_honey = left_arm.addOrReplaceChild("left_arm_honey", CubeListBuilder.create().texOffs(14, 49).mirror().addBox(-3.0F, -2.0341F, -2.2588F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 49).addBox(-3.0F, -2.0341F, -2.2588F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -9.0F, 0.0F, -1.1781F, 0.0F, 0.0F));

        PartDefinition right_arm_honey = right_arm.addOrReplaceChild("right_arm_honey", CubeListBuilder.create().texOffs(14, 49).addBox(-3.0F, -2.0341F, -2.2588F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg = mellified.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 50).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -11.0F, 1.0F));

        PartDefinition left_leg_honey = left_leg.addOrReplaceChild("left_leg_honey", CubeListBuilder.create().texOffs(48, 50).addBox(-3.0F, -10.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.239F)), PartPose.offset(1.0F, 10.0F, -1.0F));

        PartDefinition right_leg = mellified.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(32, 36).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -11.0F, 1.0F));

        PartDefinition right_leg_honey = right_leg.addOrReplaceChild("right_leg_honey", CubeListBuilder.create().texOffs(48, 36).addBox(-3.0F, -10.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.24F)), PartPose.offset(1.0F, 10.0F, -1.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void setupAnim(Mellified entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);


        this.animateWalk(MellifiedAnimations.ANIM_MELLIFIED_WALK, limbSwing, limbSwingAmount, 4f, 54);
        this.animate(entity.idleAnimationState,MellifiedAnimations.ANIM_MELLIFIED_IDLE,ageInTicks, 1f);

    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        mellified.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return mellified;
    }
}