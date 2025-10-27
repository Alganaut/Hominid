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
        this.head = this.juggernaut.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition juggernaut = partdefinition.addOrReplaceChild("juggernaut", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition torso = juggernaut.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -10.0F, -4.0F, 12.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(20, 47).addBox(-5.0F, 0.0F, -3.0F, 10.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));

        PartDefinition left_leg = juggernaut.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 38).addBox(-2.0F, 0.0F, -3.0F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -13.0F, 0.5F));

        PartDefinition right_leg = juggernaut.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 38).mirror().addBox(-3.0F, 0.0F, -3.0F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -13.0F, 0.5F));

        PartDefinition left_arm = juggernaut.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(36, 27).addBox(0.0F, -2.0F, -3.0F, 5.0F, 14.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(42, 8).addBox(5.0F, -2.0F, -2.0F, 1.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(42, 8).addBox(5.0F, -2.0F, 1.0F, 1.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -24.0F, 0.5F));

        PartDefinition right_arm = juggernaut.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(36, 27).mirror().addBox(-5.0F, -2.0F, -3.0F, 5.0F, 14.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(42, 8).mirror().addBox(-6.0F, -2.0F, -2.0F, 1.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(42, 8).mirror().addBox(-6.0F, -2.0F, 1.0F, 1.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, -24.0F, 0.5F));

        PartDefinition head = juggernaut.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-5.0F, -12.0F, -4.0F, 10.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -26.0F, 0.0F));

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