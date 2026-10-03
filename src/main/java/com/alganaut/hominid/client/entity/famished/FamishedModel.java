package com.alganaut.hominid.client.entity.famished;

import com.alganaut.hominid.client.entity.ModelHeadRotation;
import com.alganaut.hominid.entity.famished.Famished;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;

public class FamishedModel<T extends Famished> extends HierarchicalModel<T> {

    private final ModelPart famished;
    private final ModelPart torso;
    private final ModelPart head;
    public FamishedModel(ModelPart root) {
        this.famished = root.getChild("famished");
        this.torso = this.famished.getChild("torso");
        this.head = this.torso.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition famished = partdefinition.addOrReplaceChild("famished", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition torso = famished.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -10.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));

        PartDefinition tummy = torso.addOrReplaceChild("tummy", CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 22).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(37, 38).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 0.0F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(14, 46).mirror().addBox(0.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -8.0F, 0.0F, -1.222F, 0.0149F, -0.041F));

        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 46).addBox(-3.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -8.0F, 0.0F, -1.222F, -0.0149F, 0.041F));

        PartDefinition left_leg = famished.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(14, 28).addBox(-0.9F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, -14.0F, 0.0F));

        PartDefinition right_leg = famished.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 28).addBox(-2.1F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.9F, -14.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Famished entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        root().getAllParts().forEach(ModelPart::resetPose);
        ModelHeadRotation.apply(head, netHeadYaw, headPitch);
        if (entity.getAttributeValue(Attributes.MOVEMENT_SPEED) == 0.27 || (entity.isAggressive() && entity.getTarget() instanceof Animal)) {
            animateWalk(FamishedAnimations.ANIM_FAMISHED_AGGRO_WALK, limbSwing, limbSwingAmount, 2f, 54);
            animate(entity.idleAnimationState,FamishedAnimations.ANIM_FAMISHED_AGGRO_IDLE,ageInTicks, 1f);
            animate(entity.attackAnimationState,FamishedAnimations.ANIM_FAMISHED_ATTACK,ageInTicks, 1f);
        } else {
            animateWalk(FamishedAnimations.ANIM_FAMISHED_WALK, limbSwing, limbSwingAmount, 3f, 54);
            animate(entity.idleAnimationState,FamishedAnimations.ANIM_FAMISHED_IDLE,ageInTicks, 1f);
            animate(entity.attackAnimationState,FamishedAnimations.ANIM_FAMISHED_ATTACK,ageInTicks, 1f);
        }
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