package minefantasy.mfr.api.heating;

import net.minecraft.world.item.ItemStack;

public class ForgeFuel {
	public ItemStack fuel;
	public float duration;
	public int baseHeat;
	public boolean isRefined;
	public boolean doesLight;

	public ForgeFuel(ItemStack item, float dura, int heat, boolean light) {
		this(item, dura, heat, light, false);
	}

	public ForgeFuel(ItemStack item, float dura, int heat, boolean light, boolean refined) {
		this.fuel = item;
		this.duration = dura;
		this.baseHeat = heat;
		this.doesLight = light;
		this.isRefined = refined;
	}
}
