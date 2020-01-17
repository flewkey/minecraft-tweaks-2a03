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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TntBlock.class)
public class MixinTntBlock extends Block {
	@Shadow
	public static final BooleanProperty UNSTABLE = Properties.UNSTABLE;

	public MixinTntBlock(Block.Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(UNSTABLE, false));
	}

	/**
	* @reason Disable TNT block explosions.
	* @author flewkey
	*/
	@Overwrite
	private static void primeTnt(World world, BlockPos pos, @Nullable LivingEntity igniter) {
	}
}
