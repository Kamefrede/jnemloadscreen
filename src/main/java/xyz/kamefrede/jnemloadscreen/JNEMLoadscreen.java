package xyz.kamefrede.jnemloadscreen;

import net.minecraft.client.gui.ResourceLoadProgressGui;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static xyz.kamefrede.jnemloadscreen.JNEMLoadscreen.MOD_ID;

@Mod(MOD_ID)
public class JNEMLoadscreen {

	public static final String MOD_ID = "jnemloadscreen";
	public static JNEMLoadscreen instance;
	public static Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static final String JNEM_LOGO_DARK = "textures/gui/title/jnemlogo_dark.png";
	public static final String JNEM_LOGO_NORMAL = "textures/gui/title/jnemlogo.png";

	public JNEMLoadscreen() {
		instance = this;
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
		DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
			ResourceLoadProgressGui.MOJANG_LOGO_TEXTURE = new ResourceLocation(MOD_ID, Config.CLIENT.darkMode.get() ? JNEM_LOGO_DARK : JNEM_LOGO_NORMAL);
			ResourceLoadProgressGui.field_238627_b_ = ColorHelper.PackedColor.packColor(255, 54, 57, 63);
			ResourceLoadProgressGui.field_238628_c_ = ColorHelper.PackedColor.packColor(255, 54, 57, 63);
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
			return null;
		});
	}

	@OnlyIn(Dist.CLIENT)
	private void clientSetup(FMLClientSetupEvent event){
		PatronRewardHandler.firstStart();
	}
}
