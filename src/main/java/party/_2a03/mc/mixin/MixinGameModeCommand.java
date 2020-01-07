package party._2a03.mc.mixin;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.GameModeCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameModeCommand.class)
public class MixinGameModeCommand {
	@Inject(method = "setGameMode", at = @At("HEAD"))
	private static void OnSetGameMode(ServerCommandSource source, ServerPlayerEntity player, GameMode gameMode, CallbackInfo ci) {
		if (gameMode == GameMode.CREATIVE) {
			source.sendFeedback(new LiteralText("<Server> Creative mode? What are you, a cheater?"), false);
		}
		return;
	}
}
