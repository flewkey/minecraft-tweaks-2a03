package party._2a03.mc.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class HeadCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("head").executes(ctx -> {
			return giveHead(ctx.getSource(), ctx.getSource().getPlayer(), ctx.getSource().getName());
		}).then(CommandManager.argument("username", StringArgumentType.greedyString()).executes(ctx -> {
			return giveHead(ctx.getSource(), ctx.getSource().getPlayer(), StringArgumentType.getString(ctx, "username"));
		})));
	}

	private static int giveHead(ServerCommandSource source, ServerPlayerEntity sender, String skullowner) {
		ItemStack itemstack = new ItemStack(Blocks.PLAYER_HEAD.asItem());
		CompoundTag compoundtag = new CompoundTag();
		compoundtag.putString("SkullOwner", skullowner);
		itemstack.setTag(compoundtag);
		ItemEntity itementity = sender.dropItem(itemstack, false);
		itementity.resetPickupDelay();
		itementity.setOwner(sender.getUuid());
		source.sendFeedback(new LiteralText("Player head has been given"), false);
		return 1;
	}
}
