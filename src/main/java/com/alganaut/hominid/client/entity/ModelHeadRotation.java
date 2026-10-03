package com.alganaut.hominid.client.entity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public final class ModelHeadRotation {
    private ModelHeadRotation() {}

    public static void apply(ModelPart head, float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        head.yRot = headYaw * ((float)Math.PI / 180f);
        head.xRot = headPitch * ((float)Math.PI / 180f);
    }
}
