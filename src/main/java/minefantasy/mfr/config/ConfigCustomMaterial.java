package minefantasy.mfr.config;

import net.minecraft.resources.Identifier;

// TODO: wire to NeoForge ModConfigSpec dynamic sections when config porting is complete
// Per-material enable flags previously backed by a live Configuration object — stub returns true.
public class ConfigCustomMaterial {
	public static boolean isCustomMaterialEnabled(Identifier key) {
		return true;
	}
}
