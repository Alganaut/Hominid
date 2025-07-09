package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.client.animations.MellifiedAnimations;
import com.alganaut.hominid.registry.entity.client.animations.VampireAnimations;
import com.alganaut.hominid.registry.entity.custom.Mellified;
import com.alganaut.hominid.registry.entity.custom.Vampire;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class VampireModel<T extends Vampire> extends HierarchicalModel<T> {

    private final ModelPart vampire;
    private final ModelPart head;
    private final ModelPart torso;

    public VampireModel(ModelPart root) {
        this.vampire = root.getChild("vampire");
        this.torso = this.vampire.getChild("torso");
        this.head = this.torso.getChild("head");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition vampire = partdefinition.addOrReplaceChild("vampire", CubeListBuilder.create(), PartPose.offset(4.0F, 2.0F, 0.0F));

        PartDefinition torso = vampire.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 10.0F, 0.0F));

        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -10.0F, 0.0F, -1.44F, -0.0057F, 0.0433F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 32).addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -10.0F, 0.0F, -1.44F, 0.0057F, -0.0433F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition right_leg = vampire.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(24, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 10.0F, 0.0F));

        PartDefinition left_leg = vampire.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 10.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Vampire entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);


        this.animateWalk(VampireAnimations.ANIM_VAMPIRE_WALK, limbSwing, limbSwingAmount, 4f, 54);
        this.animate(entity.idleAnimationState,VampireAnimations.ANIM_VAMPIRE_IDLE,ageInTicks, 1f);
        this.animate(entity.attackAnimationState,VampireAnimations.ANIM_VAMPIRE_ATTACK,ageInTicks, 1f);
        this.animate(entity.dieAnimationState,VampireAnimations.ANIM_VAMPIRE_SCREAM,ageInTicks, 1f);

    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        vampire.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return vampire;
    }
}