package party._2a03.mc.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party._2a03.mc.server.Config;

@Mixin(TntEntity.class)
public abstract class MixinTntEntity extends Entity {
	@Shadow
	private int fuseTimer;

	public MixinTntEntity(EntityType<? extends TntEntity> entityType, World world) {
		super(entityType, world);
		this.fuseTimer = 80;
		this.inanimate = true;
	}

	@Inject(method = "explode", at = @At("HEAD"), cancellable = true)
	private void OnTntExplode(CallbackInfo ci) {
		if (Config.getBool("disableTntExplosions") == true)
			ci.cancel();
	}
}
