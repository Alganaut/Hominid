package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.client.animations.FamishedAnimations;
import com.alganaut.hominid.registry.entity.client.animations.SirenAnimations;
import com.alganaut.hominid.registry.entity.custom.Famished;
import com.alganaut.hominid.registry.entity.custom.Siren;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SirenModel<T extends Siren> extends HierarchicalModel<T> {

    private final ModelPart siren;
    private final ModelPart siren2;
    private final ModelPart head;
    private final ModelPart body;

    public SirenModel(ModelPart root) {
        this.siren = root.getChild("siren");
        this.siren2 = siren.getChild("siren2");
        this.body = this.siren2.getChild("torso");
        this.head = this.body.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition siren = partdefinition.addOrReplaceChild("siren", CubeListBuilder.create(), PartPose.offset(0.0F, 36.0F, 0.0F));

        PartDefinition siren2 = siren.addOrReplaceChild("siren2", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition torso = siren2.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(40, 47).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 25).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 16.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -22.0F, 0.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 16).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -10.0F, 0.0F));

        PartDefinition right_arm = torso.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -10.0F, 0.0F));

        PartDefinition tail = siren2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 32).addBox(-2.1F, 0.0F, -2.0F, 8.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, -22.0F, 0.0F));

        PartDefinition tailsegment = tail.addOrReplaceChild("tailsegment", CubeListBuilder.create().texOffs(0, 36).addBox(-2.75F, 0.25F, -1.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.65F, 8.75F, 0.0F));

        PartDefinition cloth = tailsegment.addOrReplaceChild("cloth", CubeListBuilder.create().texOffs(29, 59).addBox(0.0F, -2.0F, -1.0F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.25F, 4.25F, 1.0F));

        PartDefinition tailend = tailsegment.addOrReplaceChild("tailend", CubeListBuilder.create().texOffs(0, 47).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 7.25F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Siren entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);
        this.animate(entity.idleAnimationState, SirenAnimations.IDLE_WATER,ageInTicks, 1f);
        this.animate(entity.flopAnimationState, SirenAnimations.IDLE,ageInTicks, 1f);
        this.animate(entity.grabAnimationState, SirenAnimations.DRAGGING,ageInTicks, 1f);
        this.animate(entity.drownAnimationState, SirenAnimations.DROWNING,ageInTicks, 1f);
        this.animate(entity.swimAnimationState, SirenAnimations.SWIM,ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        siren.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return siren;
    }
}