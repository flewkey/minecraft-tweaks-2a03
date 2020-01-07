package party._2a03.mc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.dimension.DimensionType;
import party._2a03.mc.server.Config;
import party._2a03.mc.server.PlayerPosition;

public class SpawnCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literalargumentbuilder = CommandManager.literal("spawn").executes(ctx -> {
			ServerCommandSource source = ctx.getSource();
			ServerPlayerEntity sender = source.getPlayer();
			PlayerPosition position = new PlayerPosition(Config.getData("spawn"));
			if (position.dimensiontype == null) {
				if (source.hasPermissionLevel(2)) {
					source.sendFeedback(new LiteralText("Spawn not found, do /spawn set"), false);
				} else {
					source.sendFeedback(new LiteralText("Spawn not found, ask an admin to set it"), false);
				}
				return -1;
			}
			sender.teleport(sender.getServer().getWorld(position.dimensiontype), position.x, position.y, position.z, position.yaw, position.pitch);
			source.sendFeedback(new LiteralText("Teleported to the spawn point"), true);
			return 1;
		});

		literalargumentbuilder.then(CommandManager.literal("set").requires(ctx -> {
			return ctx.hasPermissionLevel(2);
		}).executes(ctx -> {
			ServerCommandSource source = ctx.getSource();
			ServerPlayerEntity sender = source.getPlayer();
			double x = sender.getX();
			double y = sender.getY();
			double z = sender.getZ();
			float yaw = sender.yaw;
			float pitch = sender.pitch;
			DimensionType dimensiontype = sender.getServerWorld().getDimension().getType();
			PlayerPosition location = new PlayerPosition(x, y, z, yaw, pitch, dimensiontype);
			Config.setData("spawn", location.getJSON());
			source.sendFeedback(new LiteralText("Spawn has been set"), true);
			return 1;
		}));

		dispatcher.register(literalargumentbuilder);
	}
}
