package com.reddit.user.koppeh.flamingo.client;

import com.reddit.user.koppeh.flamingo.FlamingoBlock;
import com.reddit.user.koppeh.flamingo.FlamingoBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class FlamingoBlockEntityRenderer extends BlockEntityRenderer<FlamingoBlockEntity> {

	private FlamingoModel model = new FlamingoModel();
	private Identifier resource = new Identifier("flamingo", "textures/model/flamingo.png");

	public FlamingoBlockEntityRenderer(BlockEntityRenderDispatcher renderDispatcher) {
		super(renderDispatcher);
	}

	@Override
	public void render(FlamingoBlockEntity flamingo, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
		int rotation = 0;
		float wiggle = 0;

		if (flamingo != null) {
			final BlockView world = flamingo.getWorld();
			if (world != null) {
				rotation = world.getBlockState(flamingo.getPos()).get(FlamingoBlock.ROTATION) * 360 / 16;
			}

			wiggle = (float) Math.sin(flamingo.wiggle + partialTicks) * flamingo.wiggleStrength;
		}

		matrixStack.push();

		matrixStack.translate(0.5F, 0, 0.5F);
		matrixStack.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(rotation));
		matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(1F));
		matrixStack.translate(0.0, 1.5, 0.0);
		matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180F));
		matrixStack.translate(0.0, 1.5, 0.0);
		matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(wiggle));
		matrixStack.translate(0.0, -1.5, 0.0);

		model.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(resource)), i, j, 1F, 1F, 1F, 1F);

		matrixStack.pop();
	}
}
