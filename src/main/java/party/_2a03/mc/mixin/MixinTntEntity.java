package party._2a03.mc.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TntEntity.class)
public abstract class MixinTntEntity extends Entity {
	public MixinTntEntity(EntityType<? extends TntEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	* @reason Disable TNT entity explosions.
	* @author flewkey
	*/
	@Overwrite
	private void explode() {
	}
}
