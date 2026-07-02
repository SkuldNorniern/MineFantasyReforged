package minefantasy.mfr.api.armour;

import net.minecraft.world.damagesource.DamageSource;

public interface IArmouredEntity {
	float getThreshold(DamageSource src);
}
