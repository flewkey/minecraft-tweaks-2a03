package party._2a03.mc.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class HatCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("hat").executes(ctx -> {
			ServerCommandSource source = ctx.getSource();
			ServerPlayerEntity sender = source.getPlayer();
			ItemStack mainhand = sender.getEquippedStack(EquipmentSlot.MAINHAND);
			ItemStack head = sender.getEquippedStack(EquipmentSlot.HEAD);
			sender.equipStack(EquipmentSlot.MAINHAND, head);
			sender.equipStack(EquipmentSlot.HEAD, mainhand);
			source.sendFeedback(new LiteralText("Swapped items between main hand and head"), false);
			return 1;
		}));
	}
}
