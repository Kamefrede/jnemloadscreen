package xyz.kamefrede.jnemloadscreen.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.ResourceLoadProgressGui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResourceLoadProgressGui.class)
public class MixinResourceLoadProgressGui {

	@Inject(
			at= @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ResourceLoadProgressGui;blit(Lcom/mojang/blaze3d/matrix/MatrixStack;IIIIFFIIII)V"),
			method = "render"
	)
	private void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci){
		RenderSystem.defaultBlendFunc();
		RenderSystem.defaultAlphaFunc();
		RenderSystem.disableBlend();
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
}
