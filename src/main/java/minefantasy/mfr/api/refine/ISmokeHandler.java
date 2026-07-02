package minefantasy.mfr.api.refine;

import net.minecraft.world.level.Level;

public interface ISmokeHandler {
	void spawnSmoke(Level world, double x, double y, double z, int value);
}
