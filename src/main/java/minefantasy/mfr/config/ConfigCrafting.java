package minefantasy.mfr.config;

import net.minecraft.resources.Identifier;

// TODO: wire these fields to NeoForge ModConfigSpec when config porting is complete
// TODO: per-recipe enable/disable flags previously backed by a live Configuration object —
//       stub returns true until ModConfigSpec dynamic sections are wired up
public class ConfigCrafting {
	public static boolean allowIronResmelt = false;
	public static int maxFurnaceHeight = 16;
	public static boolean canCookBasics = true;
	public static float minimumDragonforgedTemperature = 12250F;

	public static boolean isAnvilRecipeEnabled(Identifier key) { return true; }
	public static boolean isCarpenterRecipeEnabled(Identifier key) { return true; }
	public static boolean isBigFurnaceRecipeEnabled(Identifier key) { return true; }
	public static boolean isAlloyRecipeEnabled(Identifier key) { return true; }
	public static boolean isBloomeryRecipeEnabled(Identifier key) { return true; }
	public static boolean isBlastFurnaceRecipeEnabled(Identifier key) { return true; }
	public static boolean isQuernRecipeEnabled(Identifier key) { return true; }
	public static boolean isTannerRecipeEnabled(Identifier key) { return true; }
	public static boolean isRoastRecipeEnabled(Identifier key) { return true; }
	public static boolean isKitchenBenchRecipeEnabled(Identifier key) { return true; }
	public static boolean isSalvageRecipeEnabled(Identifier key) { return true; }
	public static boolean isTransformationRecipeEnabled(Identifier key) { return true; }
	public static boolean isSpecialRecipeEnabled(Identifier key) { return true; }
}
