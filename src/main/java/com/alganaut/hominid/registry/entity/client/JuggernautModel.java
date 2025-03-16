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
    private final ModelPart head;
    private final ModelPart body;

    public JuggernautModel(ModelPart root) {
        this.juggernaut = root.getChild("juggernaut");
        this.head = this.juggernaut.getChild("head");
        this.body = this.juggernaut.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition juggernaut = partdefinition.addOrReplaceChild("juggernaut", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = juggernaut.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(26, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition chestplate_r1 = body.addOrReplaceChild("chestplate_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-7.0F, -6.5F, -1.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 7.0F, -1.0F, 0.5672F, 0.0F, 0.0F));

        PartDefinition left_arm = juggernaut.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(50, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(10, 48).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
                .texOffs(0, 50).mirror().addBox(4.0F, -2.0F, -1.0F, 3.0F, 12.0F, 2.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(5.0F, -22.0F, 0.0F, -1.4399F, 0.0F, 0.0F));

        PartDefinition right_leg = juggernaut.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(10, 16).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, -12.0F, 0.0F));

        PartDefinition left_leg = juggernaut.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(26, 48).mirror().addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.9F, -12.0F, 0.0F));

        PartDefinition right_arm = juggernaut.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.0F, -22.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(10, 48).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
                .texOffs(0, 50).addBox(-7.0F, -2.0F, -1.0F, 3.0F, 12.0F, 2.0F, new CubeDeformation(0.5F))
                .texOffs(42, 48).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition head = juggernaut.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -24.0F, 0.0F));

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