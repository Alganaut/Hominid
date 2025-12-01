package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.client.animations.BellmanAnimations;
import com.alganaut.hominid.registry.entity.custom.Bellman;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class BellmanModel<T extends Bellman> extends HierarchicalModel<T> {

    private final ModelPart bellman;
    private final ModelPart torso;

    public BellmanModel(ModelPart root) {
        this.bellman = root.getChild("bellman");
        this.torso = this.bellman.getChild("torso");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bellman = partdefinition.addOrReplaceChild("bellman", CubeListBuilder.create(), PartPose.offset(1.0F, 22.0F, 0.0F));

        PartDefinition torso = bellman.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(48, 0).addBox(-8.0F, -24.25F, -3.0F, 16.0F, 24.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -24.25F, -3.0F, 16.0F, 26.0F, 8.0F, new CubeDeformation(0.55F)), PartPose.offset(-1.0F, -11.75F, 0.0F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(28, 34).mirror().addBox(-1.5F, -3.5001F, -3.0F, 6.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.5F, -21.25F, 1.0F));

        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-9.5F, -21.25F, 1.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(28, 34).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 8.4999F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bell = right_arm.addOrReplaceChild("bell", CubeListBuilder.create().texOffs(52, 32).addBox(-8.0F, 12.0F, -8.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(52, 50).addBox(-7.0F, 4.0F, -7.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(52, 70).addBox(-4.0F, 0.0F, -2.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.05F))
                .texOffs(84, 70).addBox(-2.0F, 2.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-0.5F, 19.5F, 1.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition clapper = bell.addOrReplaceChild("clapper", CubeListBuilder.create().texOffs(68, 70).addBox(-2.0F, 12.0F, -1.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 4.0F, -2.0F));

        PartDefinition right_leg = bellman.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 34).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, -12.0F, 1.5F));

        PartDefinition left_leg = bellman.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-3.5F, 0.0F, -3.5F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.5F, -12.0F, 1.5F));

        return LayerDefinition.create(meshdefinition, 128, 96);
    }

    @Override
    public void setupAnim(Bellman entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);


        this.animateWalk(BellmanAnimations.ANIM_BELLMAN_WALK, limbSwing, limbSwingAmount, 4f, 54);
        this.animate(entity.idleAnimationState,BellmanAnimations.ANIM_BELLMAN_IDLE,ageInTicks, 1f);
        this.animate(entity.attackAnimationState,BellmanAnimations.ANIM_BELLMAN_ATTACK,ageInTicks, 1f);

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        bellman.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return bellman;
    }
}