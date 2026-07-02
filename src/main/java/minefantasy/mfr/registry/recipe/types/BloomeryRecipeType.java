package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum BloomeryRecipeType implements StringRepresentable, IRecipeMFRType {
	BLOOMERY_RECIPE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static BloomeryRecipeType deserialize(String name) {
		for (BloomeryRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public BloomeryRecipeType getByNameWithModId(String name, String modId) {
		for (BloomeryRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
