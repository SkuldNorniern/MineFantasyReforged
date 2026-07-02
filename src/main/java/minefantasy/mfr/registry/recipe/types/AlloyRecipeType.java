package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum AlloyRecipeType implements StringRepresentable, IRecipeMFRType {
	ALLOY_RATIO_RECIPE,
	ALLOY_SHAPED_RECIPE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static AlloyRecipeType deserialize(String name) {
		for (AlloyRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public AlloyRecipeType getByNameWithModId(String name, String modId) {
		for (AlloyRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
