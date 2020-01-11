package party._2a03.mc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import party._2a03.mc.command.ConfigCommand;
import party._2a03.mc.command.HatCommand;
import party._2a03.mc.command.HeadCommand;
import party._2a03.mc.command.HomeCommand;
import party._2a03.mc.command.SpawnCommand;
import party._2a03.mc.server.Config;

public class MinecraftTweaks2a03 implements ModInitializer {
	private static final Logger LOGGER = LogManager.getLogger();
	private Path configDir;
	
	@Override
	public void onInitialize() {
		configDir = new File(FabricLoader.getInstance().getConfigDirectory(), "minecraft-tweaks-2a03").toPath();
		if (!Files.exists(configDir)) {
			LOGGER.info("Creating 2a03.party config directory");
			try {
				Files.createDirectory(configDir);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error(e);
				return;
			}
		}
		Config.initConfig(configDir.toFile());
		try {
			Config.loadConfig();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
			return;
		}
		LOGGER.info("Registering 2a03.party commands");
		CommandRegistry.INSTANCE.register(false, dispatcher -> {
			ConfigCommand.register(dispatcher);
			HatCommand.register(dispatcher);
			HeadCommand.register(dispatcher);
			HomeCommand.register(dispatcher);
			SpawnCommand.register(dispatcher);
		});
	}
}
