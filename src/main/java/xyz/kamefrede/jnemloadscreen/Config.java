package xyz.kamefrede.jnemloadscreen;

import net.minecraftforge.common.ForgeConfigSpec;

import org.apache.commons.lang3.tuple.Pair;

public class Config {

	public static class Client {

		public final ForgeConfigSpec.BooleanValue darkMode;

		public Client(ForgeConfigSpec.Builder builder) {
			darkMode = builder.comment("Toggle between JNEM's dark mode logo or not")
					.define("darkMode", true);
		}

	}

	public static final Client CLIENT;
	public static final ForgeConfigSpec CLIENT_SPEC;

	static {
		final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

}
