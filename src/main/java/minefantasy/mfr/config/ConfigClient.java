package minefantasy.mfr.config;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

// TODO: wire these fields to NeoForge ModConfigSpec when config porting is complete
@OnlyIn(Dist.CLIENT)
public class ConfigClient {
	// Sound
	public static boolean playBreath = true;
	public static boolean playHitsound = true;
	public static boolean playArmorSound = true;

	// Aesthetics
	public static boolean customModel = true;
	public static boolean shouldUseMfrCustomAnimations = true;

	// Stamina Bar
	public static int stam_xOrient = 1;
	public static int stam_yOrient = 1;
	public static int stam_xPos = -82;
	public static int stam_yPos = -7;
	public static int stam_direction = 1;

	// Armour Rating
	public static int AR_xOrient = -1;
	public static int AR_yOrient = -1;
	public static int AR_xPos = 4;
	public static int AR_yPos = 4;

	// Arrow Count
	public static int AC_xOrient = -1;
	public static int AC_yOrient = -1;
	public static int AC_xPos = 4;
	public static int AC_yPos = 4;

	// Cogwork Fuel
	public static int CF_xOrient = 1;
	public static int CF_yOrient = -1;
	public static int CF_xPos = -164;
	public static int CF_yPos = -4;

	// Debug
	public static boolean displayOreDict = false;
}
