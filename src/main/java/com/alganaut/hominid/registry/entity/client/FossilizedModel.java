package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.client.animations.FossilizedAnimations;
import com.alganaut.hominid.registry.entity.custom.Fossilized;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FossilizedModel<T extends Fossilized> extends HierarchicalModel<T> {

    private final ModelPart fossilized;
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public FossilizedModel(ModelPart root) {
        this.fossilized = root.getChild("fossilized");
        this.body = this.fossilized.getChild("body");
        this.left_arm = this.body.getChild("left_arm");
        this.right_arm = this.body.getChild("right_arm");
        this.left_leg = this.fossilized.getChild("left_leg");
        this.right_leg = this.fossilized.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition fossilized = partdefinition.addOrReplaceChild("fossilized", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition body = fossilized.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -27.0F, -1.0F, 12.0F, 27.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(42, 13).addBox(0.0F, -29.0F, -1.0F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(18, 32).addBox(0.0F, -2.0F, -1.0F, 6.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -15.0F, 0.0F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 30).addBox(-6.0F, -6.0F, -1.0F, 6.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -15.0F, 0.0F));

        PartDefinition left_leg = fossilized.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(36, 36).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -14.0F, 0.0F));

        PartDefinition right_leg = fossilized.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(36, 18).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -14.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Fossilized entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);


        this.animateWalk(FossilizedAnimations.ANIM_FOSSILIZED_WALK, limbSwing, limbSwingAmount, 4f, 54);
        this.animate(entity.idleAnimationState,FossilizedAnimations.ANIM_FOSSILIZED_IDLE,ageInTicks, 1f);
        this.animate(entity.throwAnimationState,FossilizedAnimations.ANIMATION_FOSSILIZED_ATTACK,ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        fossilized.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return fossilized;
    }
}