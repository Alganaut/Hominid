// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class siren<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "siren"), "main");
	private final ModelPart siren;
	private final ModelPart siren2;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart tail;
	private final ModelPart tailsegment;
	private final ModelPart cloth;
	private final ModelPart tailend;

	public siren(ModelPart root) {
		this.siren = root.getChild("siren");
		this.siren2 = this.siren.getChild("siren2");
		this.torso = this.siren2.getChild("torso");
		this.head = this.torso.getChild("head");
		this.left_arm = this.torso.getChild("left_arm");
		this.right_arm = this.torso.getChild("right_arm");
		this.tail = this.siren2.getChild("tail");
		this.tailsegment = this.tail.getChild("tailsegment");
		this.cloth = this.tailsegment.getChild("cloth");
		this.tailend = this.tailsegment.getChild("tailend");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition siren = partdefinition.addOrReplaceChild("siren", CubeListBuilder.create(), PartPose.offset(0.0F, 43.0F, 0.0F));

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
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		siren.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}