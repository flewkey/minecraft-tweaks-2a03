package party._2a03.mc.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;
import party._2a03.mc.server.Config;
import party._2a03.mc.server.PlayerPosition;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {	
	public MixinPlayerEntity() {
		super(null, null);
	}

	@Inject(method = "getSpawnPosition", at = @At("RETURN"), cancellable = true)
	public void OnGetSpawnPosition(CallbackInfoReturnable cir) {
		PlayerPosition position = new PlayerPosition(Config.getData("spawn"));
		if (position.dimensiontype == this.dimension)
			cir.setReturnValue(null); // Don't override the world spawn.
	}
}
