package minefantasy.mfr.config;

// TODO: wire these fields to NeoForge ModConfigSpec when config porting is complete
// Note: isEnabled, hoeFailChanceCfg, and farmBreakCfg should be applied to FarmingHelper
//       at mod init time.
public class ConfigFarming {
	public static boolean isEnabled = true;
	public static float hoeFailChanceCfg = 1.0F;
	public static float farmBreakCfg = 1.0F;
}
