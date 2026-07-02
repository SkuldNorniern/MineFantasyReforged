package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum BigFurnaceRecipeType implements StringRepresentable, IRecipeMFRType {
	BIG_FURNACE_RECIPE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static BigFurnaceRecipeType deserialize(String name) {
		for (BigFurnaceRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public BigFurnaceRecipeType getByNameWithModId(String name, String modId) {
		for (BigFurnaceRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
