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
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public FossilisedModel(ModelPart root) {
        this.fossilised = root.getChild("fossilised");
        this.body = this.fossilised.getChild("body");
        this.left_arm = this.body.getChild("left_arm");
        this.right_arm = this.body.getChild("right_arm");
        this.left_leg = this.fossilised.getChild("left_leg");
        this.right_leg = this.fossilised.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition fossilised = partdefinition.addOrReplaceChild("fossilised", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition body = fossilised.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -27.0F, -1.0F, 12.0F, 27.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(42, 13).addBox(0.0F, -29.0F, -1.0F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(18, 32).addBox(0.0F, -2.0F, -1.0F, 6.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -15.0F, 0.0F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 30).addBox(-6.0F, -6.0F, -1.0F, 6.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -15.0F, 0.0F));

        PartDefinition left_leg = fossilised.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(36, 36).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -14.0F, 0.0F));

        PartDefinition right_leg = fossilised.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(36, 18).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -14.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Fossilised entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);


        this.animateWalk(FossilisedAnimations.ANIM_FOSSILIZED_WALK, limbSwing, limbSwingAmount, 4f, 54);
        this.animate(entity.idleAnimationState,FossilisedAnimations.ANIM_FOSSILIZED_IDLE,ageInTicks, 1f);
        this.animate(entity.throwAnimationState,FossilisedAnimations.ANIMATION_FOSSILIZED_ATTACK,ageInTicks, 1f);
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