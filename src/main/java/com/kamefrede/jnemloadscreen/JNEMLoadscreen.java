package com.kamefrede.jnemloadscreen;

import net.minecraft.client.gui.ResourceLoadProgressGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.kamefrede.jnemloadscreen.JNEMLoadscreen.MOD_ID;

@Mod(MOD_ID)
public class JNEMLoadscreen {

	public static final String MOD_ID = "jnemloadscreen";
	public static JNEMLoadscreen instance;
	public static Logger LOGGER = LogManager.getLogger(MOD_ID);

	public JNEMLoadscreen() {
		instance = this;
		DistExecutor.callWhenOn(Dist.CLIENT, () -> () -> {
			ResourceLoadProgressGui.MOJANG_LOGO_TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/title/mojang.png");
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
			return null;
		});
	}

	private void clientSetup(FMLClientSetupEvent event){
		PatronRewardHandler.firstStart();
	}
}
