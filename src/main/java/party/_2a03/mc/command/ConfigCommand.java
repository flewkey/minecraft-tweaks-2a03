package party._2a03.mc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import party._2a03.mc.server.Config;

public class ConfigCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literalargumentbuilder = CommandManager.literal("config").requires(ctx -> {
			return ctx.hasPermissionLevel(2);
		});

		literalargumentbuilder.then(CommandManager.literal("reload").executes(ctx -> {
			ServerCommandSource source = ctx.getSource();
			try {
				Config.loadConfig();
				source.sendFeedback(new LiteralText("Reloaded the configuration"), true);
			} catch(Exception e) {
				source.sendFeedback(new LiteralText("Failed to reload the configuration"), true);
			}
			return 1;
		}));

		dispatcher.register(literalargumentbuilder);
	}
}
