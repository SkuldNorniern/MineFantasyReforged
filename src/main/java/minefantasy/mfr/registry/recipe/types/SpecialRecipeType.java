package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum SpecialRecipeType implements StringRepresentable, IRecipeMFRType {
	SPECIAL_RECIPE_DRAGONFORGED,
	SPECIAL_RECIPE_ORNATE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static SpecialRecipeType deserialize(String name) {
		for (SpecialRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public SpecialRecipeType getByNameWithModId(String name, String modId) {
		for (SpecialRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
