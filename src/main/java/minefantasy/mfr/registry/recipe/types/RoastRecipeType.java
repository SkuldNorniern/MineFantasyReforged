package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum RoastRecipeType implements StringRepresentable, IRecipeMFRType {
	COOKING_RECIPE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static RoastRecipeType deserialize(String name) {
		for (RoastRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public RoastRecipeType getByNameWithModId(String name, String modId) {
		for (RoastRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
