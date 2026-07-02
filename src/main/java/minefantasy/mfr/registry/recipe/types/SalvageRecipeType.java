package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum SalvageRecipeType implements StringRepresentable, IRecipeMFRType {
	SALVAGE_RECIPE,
	SALVAGE_RECIPE_SHARED,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static SalvageRecipeType deserialize(String name) {
		for (SalvageRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public SalvageRecipeType getByNameWithModId(String name, String modId) {
		for (SalvageRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
