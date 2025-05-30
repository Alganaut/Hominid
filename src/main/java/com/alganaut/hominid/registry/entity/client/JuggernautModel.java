package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.client.animations.JuggernautAnimations;
import com.alganaut.hominid.registry.entity.custom.Juggernaut;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class JuggernautModel<T extends Juggernaut> extends HierarchicalModel<T> {

    private final ModelPart juggernaut;
    private final ModelPart torso;
    private final ModelPart head;

    public JuggernautModel(ModelPart root) {
        this.juggernaut = root.getChild("juggernaut");
        this.torso = this.juggernaut.getChild("torso");
        this.head = this.torso.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition juggernaut = partdefinition.addOrReplaceChild("juggernaut", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition torso = juggernaut.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition body = torso.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(26, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition chestplate_r1 = body.addOrReplaceChild("chestplate_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-7.0F, -6.5F, -1.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(3.0F, 7.0F, -1.0F, 0.5672F, 0.0F, 0.0F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(50, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(40, 48).mirror().addBox(-1.0F, -1.9F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)).mirror(false)
                .texOffs(24, 48).mirror().addBox(-1.0F, -1.9F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F)).mirror(false)
                .texOffs(54, 59).mirror().addBox(2.5F, 5.35F, -0.9F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.26F)).mirror(false), PartPose.offsetAndRotation(5.0F, -10.0F, 0.0F, -1.44F, 0.0057F, -0.0433F));

        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(50, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 48).addBox(-2.0F, -1.9F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.26F))
                .texOffs(24, 48).addBox(-3.0F, -1.9F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.26F))
                .texOffs(54, 59).addBox(-5.5F, 5.25F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.26F)), PartPose.offsetAndRotation(-5.0F, -10.0F, 0.0F, -1.44F, -0.0057F, 0.0433F));

        PartDefinition left_leg = juggernaut.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(10, 16).mirror().addBox(-1.901F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 42).mirror().addBox(-1.901F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(1.9F, -12.0F, 0.0F));

        PartDefinition right_leg = juggernaut.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(10, 16).addBox(-2.101F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 42).addBox(-2.101F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, -12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Juggernaut entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);


        this.animateWalk(JuggernautAnimations.ANIM_JUGGERNAUT_WALK, limbSwing, limbSwingAmount, 4f, 54);
        this.animate(entity.idleAnimationState,JuggernautAnimations.ANIM_JUGGERNAUT_IDLE,ageInTicks, 1f);
        this.animate(entity.attackAnimationState,JuggernautAnimations.ANIM_JUGGERNAUT_ATTACK,ageInTicks, 1f);

    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        juggernaut.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return juggernaut;
    }
}