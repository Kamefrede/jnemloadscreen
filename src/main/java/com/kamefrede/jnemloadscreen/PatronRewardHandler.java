package com.kamefrede.jnemloadscreen;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


@Mod.EventBusSubscriber(modid = JNEMLoadscreen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PatronRewardHandler {

	private static volatile Set<String> patrons  = Collections.newSetFromMap(new HashMap<>());
	private static boolean startedLoading = false;

	public static void firstStart() {
		if (!startedLoading) {
			new ThreadPatreonListLoader();
			startedLoading = true;
		}
	}

	public static void load(Properties props){
		patrons.addAll(props.stringPropertyNames());
		patrons.add("Dev");
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onRenderPlayer(RenderPlayerEvent.Post event) {
		PlayerEntity player = event.getPlayer();
		String playerName = player.getGameProfile().getName();
		if(player instanceof AbstractClientPlayerEntity && patrons.contains(playerName)) {
			AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) player;
			if(clientPlayer.hasPlayerInfo()) {
				NetworkPlayerInfo info = ((AbstractClientPlayerEntity) player).playerInfo;
				Map<MinecraftProfileTexture.Type, ResourceLocation> textures = info.playerTextures;
				ResourceLocation loc = new ResourceLocation(JNEMLoadscreen.MOD_ID, "textures/misc/patron_cape.png");
				if(!(textures.get(MinecraftProfileTexture.Type.CAPE) == loc || textures.get(MinecraftProfileTexture.Type.ELYTRA) == loc)){
					textures.put(MinecraftProfileTexture.Type.CAPE, loc);
					textures.put(MinecraftProfileTexture.Type.ELYTRA, loc);
				}
			}
		}
	}

	private static class ThreadPatreonListLoader extends Thread {

		public ThreadPatreonListLoader() {
			setName("JNEM Patron list handler");
			setDaemon(true);
			setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(JNEMLoadscreen.LOGGER));
			start();
		}

		@Override
		public void run() {
			try {
				URL url = new URL("https://raw.githubusercontent.com/Boomflex/JustNotEnoughMods2/master/contributor.properties");
				Properties props = new Properties();
				try (InputStreamReader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
					props.load(reader);
					props.forEach((k, v) -> patrons.add((String)k));
					load(props);
				}
			} catch (IOException e) {
				JNEMLoadscreen.LOGGER.info("Could not load patron list. Either you're offline or github is down. Nothing to worry about, carry on~");
			}
		}

	}
}
