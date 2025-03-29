package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.client.animations.FossilisedAnimations;
import com.alganaut.hominid.registry.entity.client.animations.MellifiedAnimations;
import com.alganaut.hominid.registry.entity.custom.Fossilised;
import com.alganaut.hominid.registry.entity.custom.Mellified;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class FossilisedModel<T extends Fossilised> extends HierarchicalModel<T> {

    private final ModelPart fossilised;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart slab;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public FossilisedModel(ModelPart root) {
        this.fossilised = root.getChild("fossilised");
        this.body = this.fossilised.getChild("body");
        this.head = this.body.getChild("head");
        this.left_arm = this.body.getChild("left_arm");
        this.right_arm = this.body.getChild("right_arm");
        this.slab = this.right_arm.getChild("slab");
        this.left_leg = this.fossilised.getChild("left_leg");
        this.right_leg = this.fossilised.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition fossilised = partdefinition.addOrReplaceChild("fossilised", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = fossilised.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -14.0F, -2.0F, 10.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 0.0F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(28, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -12.0F, 0.0F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(18, 36).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -12.0F, 0.0F));

        PartDefinition slab = right_arm.addOrReplaceChild("slab", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, 0.0F));

        PartDefinition head_r1 = slab.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(36, 50).addBox(-5.0F, -5.0F, -2.0F, 10.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-5.0F, -10.0F, -2.0F, 10.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));

        PartDefinition left_leg = fossilised.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-0.9F, -1.0F, -2.0F, 5.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, -12.0F, 0.0F));

        PartDefinition right_leg = fossilised.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(28, 19).addBox(-3.1F, -1.0F, -2.0F, 5.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, -12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Fossilised entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);


        this.animateWalk(FossilisedAnimations.ANIM_FOSSILISED_WALK, limbSwing, limbSwingAmount, 4f, 54);
        this.animate(entity.idleAnimationState,FossilisedAnimations.ANIM_FOSSILISED_IDLE,ageInTicks, 1f);
        this.animate(entity.throwAnimationState,FossilisedAnimations.ANIMATION_FOSSILISED_ATTACK,ageInTicks, 1f);

        this.slab.visible = (entity.throwAnimationState.isStarted());
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        fossilised.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return fossilised;
    }
}