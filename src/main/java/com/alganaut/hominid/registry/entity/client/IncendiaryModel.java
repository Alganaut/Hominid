package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.client.animations.IncendiaryAnimations;
import com.alganaut.hominid.registry.entity.custom.Incendiary;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class IncendiaryModel<T extends Incendiary> extends HierarchicalModel<T> {

    private final ModelPart incendiary;
    private final ModelPart torso;
    private final ModelPart head;

    public IncendiaryModel(ModelPart root) {
        this.incendiary = root.getChild("incendiary");
        this.torso = this.incendiary.getChild("torso");
        this.head = this.torso.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition incendiary = partdefinition.addOrReplaceChild("incendiary", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition torso = incendiary.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(16, 32).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(16, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-6.0F, -6.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(1.0F, -11.0F, -2.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(1, 6).addBox(4.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition left_arm = torso.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, -10.0F, 0.0F, -1.44F, 0.0057F, -0.0433F));

        PartDefinition righter_arm = torso.addOrReplaceChild("righter_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, -10.0F, 0.0F));

        PartDefinition tank = righter_arm.addOrReplaceChild("tank", CubeListBuilder.create().texOffs(0, 33).addBox(2.0F, -1.0F, 2.0F, 6.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(-2, 43).addBox(1.0F, 0.0F, 2.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition oil = tank.addOrReplaceChild("oil", CubeListBuilder.create().texOffs(-6, 45).addBox(-0.5F, 0.0F, -6.0F, 1.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.525F, 0.025F, 3.975F));

        PartDefinition right_arm = righter_arm.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition left_leg = incendiary.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.9F, -12.0F, 0.0F));

        PartDefinition right_leg = incendiary.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, -12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void setupAnim(Incendiary entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);
        if(!entity.isIgniting()){
            if(entity.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() == 0.35){
                this.animateWalk(IncendiaryAnimations.ANIM_INCENDIARY_AGGRO_WALK, limbSwing, limbSwingAmount, 2f, 54);
            }else{
                this.animateWalk(IncendiaryAnimations.ANIM_INCENDIARY_WALK, limbSwing, limbSwingAmount, 2f, 54);
                this.animate(entity.idleAnimationState,IncendiaryAnimations.ANIM_INCENDIARY_IDLE,ageInTicks, 1f);
                this.animate(entity.attackAnimationState,IncendiaryAnimations.ANIM_INCENDIARY_ATTACK,ageInTicks, 2f);
            }
        }
        this.animate(entity.igniteAnimationState,IncendiaryAnimations.ANIM_INCENDIARY_IGNITING,ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        incendiary.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return incendiary;
    }
}