package party._2a03.mc.mixin;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TntBlock.class)
public class MixinTntBlock extends Block {
	public MixinTntBlock(Block.Settings settings) {
		super(settings);
	}

	/**
	* @reason Disable TNT block explosions.
	* @author flewkey
	*/
	@Overwrite
	private static void primeTnt(World world, BlockPos pos, @Nullable LivingEntity igniter) {
	}
}
