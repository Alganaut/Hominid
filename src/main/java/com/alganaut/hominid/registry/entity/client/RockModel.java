package com.alganaut.hominid.registry.entity.client;

import com.alganaut.hominid.registry.entity.client.animations.FossilisedAnimations;
import com.alganaut.hominid.registry.entity.client.animations.RockAnimations;
import com.alganaut.hominid.registry.entity.custom.Fossilised;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class RockModel<T extends Fossilised> extends HierarchicalModel<T> {

    private final ModelPart rock;
    private final ModelPart rock2;

    public RockModel(ModelPart root) {
        this.rock = root.getChild("rock");
        this.rock2 = this.rock.getChild("rock2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rock = partdefinition.addOrReplaceChild("rock", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 15.5F, 0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition rock2 = rock.addOrReplaceChild("rock2", CubeListBuilder.create().texOffs(42, 13).addBox(0.0F, -6.0F, -1.5F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(30, 0).addBox(-6.0F, -4.0F, -1.5F, 12.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Fossilised entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(RockAnimations.ANIM_ROCK_IDLE, limbSwing, limbSwingAmount, 2f, 54);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        rock.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return rock;
    }
}