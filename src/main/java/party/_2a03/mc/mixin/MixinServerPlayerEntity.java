package party._2a03.mc.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import party._2a03.mc.server.Config;
import party._2a03.mc.server.PlayerPosition;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity extends PlayerEntity {
	public MixinServerPlayerEntity() {
		super(null, null);
	}

	@Inject(method = "moveToSpawn", at = @At("HEAD"), cancellable = true)
	private void OnServerPlayerSpawn(CallbackInfo ci) {
		PlayerPosition position = new PlayerPosition(Config.getData("spawn"));
		if (position.dimensiontype == this.dimension) {
			this.updatePositionAndAngles(position.x, position.y, position.z, position.yaw, position.pitch);
			ci.cancel();
		}
	}
}
