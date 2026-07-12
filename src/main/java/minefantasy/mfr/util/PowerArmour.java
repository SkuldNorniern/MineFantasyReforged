package minefantasy.mfr.util;

import minefantasy.mfr.api.armour.IPowerArmour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PowerArmour {
	public static boolean isWearingCogwork(LivingEntity user) {
		return user.getVehicle() instanceof IPowerArmour;
	}

	public static boolean isPowered(LivingEntity user) {
		if (user.getVehicle() instanceof IPowerArmour power) {
			return power.isPowered();
		}
		return false;
	}

	public static boolean isFullyArmoured(LivingEntity user) {
		if (user.getVehicle() instanceof IPowerArmour power) {
			return power.isFullyArmoured();
		}
		return false;
	}

	public static float modifyDamage(LivingEntity user, float dam, DamageSource src) {
		if (user.getVehicle() instanceof IPowerArmour power) {
			return power.modifyDamage(user, dam, src);
		}
		return dam;
	}

	public static boolean allowDamageToBlock(DamageSource src) {
		if (src.is(DamageTypes.STARVE) || src.is(DamageTypes.FELL_OUT_OF_WORLD) || src.is(DamageTypes.DROWN)) {
			return false;
		}
		return true;
	}

	public static float getFuelValue(ItemStack item) {
		// TODO: wire to ForgeItemHandler when api/heating/ForgeItemHandler is ported
		return 0;
	}

	public static boolean isStationBlock(Level level, BlockPos pos) {
		// TODO: wire BlockFrame/BlockFrameHolder instanceof checks when block classes are ported
		return false;
	}

	public static boolean isBasicStationFrame(Level level, BlockPos pos) {
		// TODO: wire BlockFrame/BlockFrameHolder instanceof checks when block classes are ported
		return false;
	}

	public static boolean isShaft(Level level, BlockPos pos) {
		// TODO: wire BlockFrame instanceof checks when block classes are ported
		return false;
	}
}
