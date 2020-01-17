package party._2a03.mc.mixin;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import party._2a03.mc.server.Config;

@Mixin(TntBlock.class)
public abstract class MixinTntBlock extends Block {
	@Shadow
	public static final BooleanProperty UNSTABLE = Properties.UNSTABLE;

	public MixinTntBlock(Block.Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(UNSTABLE, false));
	}

	@Inject(method = "primeTnt", at = @At("HEAD"), cancellable = true)
	private static void OnPrimeTnt(CallbackInfo ci) {
		if (Config.getBool("disableTntExplosions") == true)
			ci.cancel();
	}
}
