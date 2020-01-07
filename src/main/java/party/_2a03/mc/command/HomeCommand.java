package party._2a03.mc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.dimension.DimensionType;
import party._2a03.mc.server.Config;
import party._2a03.mc.server.PlayerData;
import party._2a03.mc.server.PlayerPosition;

public class HomeCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> literalargumentbuilder = CommandManager.literal("home").executes(ctx -> {
			ServerCommandSource source = ctx.getSource();
			ServerPlayerEntity sender = source.getPlayer();
			PlayerPosition position = Config.getPlayer(sender.getUuid().toString()).getHome();
			if (position.dimensiontype == null) {
				source.sendFeedback(new LiteralText("Home not found, do /home set"), false);
				return -1;
			}
			sender.teleport(sender.getServer().getWorld(position.dimensiontype), position.x, position.y, position.z, position.yaw, position.pitch);
			source.sendFeedback(new LiteralText("Teleported to home"), true);
			return 1;
		});

		literalargumentbuilder.then(CommandManager.literal("set").executes(ctx -> {
			ServerCommandSource source = ctx.getSource();
			ServerPlayerEntity sender = source.getPlayer();
			PlayerData playerdata = Config.getPlayer(sender.getUuid().toString());
			double x = sender.getX();
			double y = sender.getY();
			double z = sender.getZ();
			float yaw = sender.yaw;
			float pitch = sender.pitch;
			DimensionType dimensiontype = sender.getServerWorld().getDimension().getType();
			PlayerPosition location = new PlayerPosition(x, y, z, yaw, pitch, dimensiontype);
			playerdata.setHome(location);
			Config.setPlayer(playerdata);
			source.sendFeedback(new LiteralText("Your home has been updated"), true);
			return 1;
		}));

		literalargumentbuilder.then(CommandManager.literal("sudoset").requires(ctx -> {
			return ctx.hasPermissionLevel(2);
		}).then(CommandManager.argument("UUID", StringArgumentType.word()).executes(ctx -> {
			ServerCommandSource source = ctx.getSource();
			ServerPlayerEntity sender = source.getPlayer();
			PlayerData playerdata = Config.getPlayer(StringArgumentType.getString(ctx, "UUID"));
			double x = sender.getX();
			double y = sender.getY();
			double z = sender.getZ();
			float yaw = sender.yaw;
			float pitch = sender.pitch;
			DimensionType dimensiontype = sender.getServerWorld().getDimension().getType();
			PlayerPosition location = new PlayerPosition(x, y, z, yaw, pitch, dimensiontype);
			playerdata.setHome(location);
			Config.setPlayer(playerdata);
			source.sendFeedback(new LiteralText("User's home has been updated (" + StringArgumentType.getString(ctx, "UUID") + ")"), true);
			return 1;
		})));

		dispatcher.register(literalargumentbuilder);
	}
}
