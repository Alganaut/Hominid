package com.alganaut.hominid.registry.entity.client.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class IncendiaryAnimations {

        public static final AnimationDefinition ANIM_INCENDIARY_IDLE = AnimationDefinition.Builder.withLength(6.0F).looping()
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0417F, KeyframeAnimations.degreeVec(5.0F, 1.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0417F, KeyframeAnimations.degreeVec(5.0F, 1.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.2917F, KeyframeAnimations.degreeVec(-26.0522F, -16.4513F, 1.4312F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.6667F, KeyframeAnimations.degreeVec(-26.0522F, -16.4513F, 1.4312F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.degreeVec(59.9339F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0F, KeyframeAnimations.degreeVec(0.0F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0417F, KeyframeAnimations.degreeVec(5.0F, -1.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0417F, KeyframeAnimations.degreeVec(5.0F, -1.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.2083F, KeyframeAnimations.degreeVec(-26.0522F, 16.4513F, -1.4312F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.5833F, KeyframeAnimations.degreeVec(-26.0522F, 16.4513F, -1.4312F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.degreeVec(59.9339F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0F, KeyframeAnimations.degreeVec(0.0F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.9583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.9583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.25F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.625F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.degreeVec(13.44F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0833F, KeyframeAnimations.degreeVec(18.68F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.2083F, KeyframeAnimations.degreeVec(13.44F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.3333F, KeyframeAnimations.degreeVec(18.68F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.4583F, KeyframeAnimations.degreeVec(13.44F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5833F, KeyframeAnimations.degreeVec(18.68F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.7083F, KeyframeAnimations.degreeVec(13.44F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-2.5024F, -2.4976F, 0.1091F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0833F, KeyframeAnimations.degreeVec(-2.5024F, -2.4976F, 0.1091F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.3333F, KeyframeAnimations.degreeVec(-4.722F, 0.185F, -0.0081F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(4.7083F, KeyframeAnimations.degreeVec(-4.722F, 0.185F, -0.0081F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.125F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.375F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.625F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(4.7083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.75F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(4.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.0F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.125F, KeyframeAnimations.posVec(0.0F, -0.04F, -1.91F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.25F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.375F, KeyframeAnimations.posVec(0.0F, -0.04F, -1.91F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.5F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.625F, KeyframeAnimations.posVec(0.0F, -0.04F, -1.91F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(5.75F, KeyframeAnimations.posVec(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(6.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition ANIM_INCENDIARY_WALK = AnimationDefinition.Builder.withLength(1.25F).looping()
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.degreeVec(0.0F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(10.0F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5417F, KeyframeAnimations.degreeVec(10.0F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.1511F, -9.8466F, -1.7538F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.5833F, KeyframeAnimations.degreeVec(5.1511F, 9.8466F, 1.7538F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(5.1511F, -9.8466F, -1.7538F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.329F, 7.4928F, -2.5215F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-0.329F, -7.4928F, 2.5215F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(-0.329F, 7.4928F, -2.5215F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.degreeVec(-20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition ANIM_INCENDIARY_IGNITING = AnimationDefinition.Builder.withLength(3.0F)
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(60.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(3.7217F, 35.2993F, 0.94F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(3.7217F, 35.2993F, 0.94F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.625F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(4.9907F, 0.434F, -2.4621F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -32.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, -32.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -15.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, -35.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(-90.0F, 45.0F, -90.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(-90.0F, 45.0F, -90.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(1.625F, KeyframeAnimations.posVec(0.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.posVec(4.0F, 0.0F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition ANIM_INCENDIARY_ATTACK = AnimationDefinition.Builder.withLength(0.7083F)
                .addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-0.943F, -24.9795F, 2.7477F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-0.9869F, 29.9788F, -2.8426F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-5.0F, 20.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.degreeVec(5.6617F, -14.7677F, -2.5756F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.375F, KeyframeAnimations.degreeVec(20.0199F, -0.3038F, -90.0212F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.degreeVec(0.0F, 6.1694F, -9.3285F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.posVec(0.0F, 0.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.degreeVec(0.0F, -6.1694F, 9.3285F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();

        public static final AnimationDefinition ANIM_INCENDIARY_AGGRO_WALK = AnimationDefinition.Builder.withLength(0.625F).looping()
                .addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.1587F, -7.0453F, -2.5782F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(20.1587F, 7.0453F, 2.5782F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.degreeVec(20.1587F, -7.0453F, -2.5782F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.4885F, 0.5409F, 2.4408F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-7.4885F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.degreeVec(-7.4885F, 0.5409F, 2.4408F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(38.8468F, 10.6786F, -10.3042F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-38.9241F, -12.3028F, -9.8064F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.degreeVec(38.8468F, 10.6786F, -10.3042F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-38.9241F, 12.3028F, 9.8064F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(38.8468F, -10.6786F, 10.3042F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.degreeVec(-38.9241F, 12.3028F, 9.8064F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.degreeVec(70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .addAnimation("incendiary", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.1667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.4583F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
                ))
                .build();
}