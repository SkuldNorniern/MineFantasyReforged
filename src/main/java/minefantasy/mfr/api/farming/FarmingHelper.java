package minefantasy.mfr.api.farming;

import minefantasy.mfr.util.MFRLogUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class FarmingHelper {
	public static boolean isEnabled = true;
	public static float hoeFailChanceCfg = 1.0F;
	public static float farmBreakCfg = 1.0F;
	private static Random rand = new Random();
	private static float hoeFailChanceModifier = 1.0F;

	public static boolean didHoeFail(ItemStack hoe, Level world, boolean grass) {
		if (!isEnabled) {
			return false;
		}
		float chanceMax = 30F * hoeFailChanceModifier * hoeFailChanceCfg;
		float chance = rand.nextFloat() * chanceMax;
		float efficiency = (getHoeEfficiency(hoe) * (grass ? 2.0F : 3.0F));

		MFRLogUtil.logDebug("Hoe Chance Fail = " + chance + " / " + efficiency + " (max= " + chanceMax + ")");
		return chance > efficiency;
	}

	public static boolean didHarvestRuinBlock(Level world, boolean scythe) {
		if (!isEnabled) {
			return false;
		}
		float chance = 20F + (world.getDifficulty().getId() * 10F);
		if (scythe)
			chance *= 2F;
		return rand.nextFloat() * 100F <= chance * farmBreakCfg;
	}

	private static float getHoeEfficiency(ItemStack hoe) {
		// HoeItem no longer exposes its ToolMaterial — use custom entry or a sensible default
		return CustomHoeEntry.getEntryEfficiency(hoe, 6.0F);
	}
}
