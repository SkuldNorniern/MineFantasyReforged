package minefantasy.mfr.config;

// TODO: wire these fields to NeoForge ModConfigSpec when config porting is complete
public class ConfigStamina {
	public static boolean isSystemActive = true;
	public static float defaultMax = 100F;
	public static float decayModifierCfg = 1.0F;
	public static float decayModifierBase = 0.5F;
	public static float configRegenModifier = 1.0F;
	public static float pauseModifier = 1.0F;
	public static float configArmourWeightModifier = 1.0F;
	public static float configBulk = 1.0F;
	public static boolean scaleDifficulty = true;

	public static boolean levelUp = false;
	public static float levelAmount = 5F;

	public static float exhaustDamage = 2F;
	public static float weaponModifier = 1F;
	public static float sprintModifier = 1F;
	public static float dodgeForceModifier = 1F;
	public static float dodgeCostModifier = 1F;
	public static int dodgeShieldCost = 10;
	public static int fullRegenSeconds = 60;
	public static int eatDelayModifier = 1;
	public static int fatAccumulationModifier = 1;
	public static int fatThreshold = 50;
	public static float weaponDrain = 1F;
	public static float bowModifier = 1F;
}
