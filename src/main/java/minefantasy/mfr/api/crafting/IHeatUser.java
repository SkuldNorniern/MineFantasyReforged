package minefantasy.mfr.api.crafting;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface IHeatUser {
	boolean canAccept(BlockEntity tile);
}
