package party._2a03.mc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
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
	
	@Override
	public void onInitialize() {
		try {
			Config.loadConfig();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
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
