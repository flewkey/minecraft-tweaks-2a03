package party._2a03.mc.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class FlyCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("fly").executes(ctx -> {
			ServerCommandSource source = ctx.getSource();
			ServerPlayerEntity sender = source.getPlayer();
			boolean flight = sender.abilities.allowFlying;
			sender.abilities.allowFlying = !flight;
			if (!flight)
				source.sendFeedback(new LiteralText("I wanna fly like an eagle... to the sea!"), true);
			else {
				source.sendFeedback(new LiteralText("Flight disabled"), true);
				sender.abilities.flying = false;
			}
			sender.sendAbilitiesUpdate();
			return 1;
		}));
	}
}
