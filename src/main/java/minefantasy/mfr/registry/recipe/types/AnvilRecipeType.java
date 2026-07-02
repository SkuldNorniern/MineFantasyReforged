package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum AnvilRecipeType implements StringRepresentable, IRecipeMFRType {
	ANVIL_SHAPED_RECIPE,
	ANVIL_SHAPELESS_RECIPE,
	ANVIL_SHAPED_CUSTOM_MATERIAL_RECIPE,
	ANVIL_SHAPELESS_CUSTOM_MATERIAL_RECIPE,
	ANVIL_DYNAMIC_RECIPE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static AnvilRecipeType deserialize(String name) {
		for (AnvilRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public AnvilRecipeType getByNameWithModId(String name, String modId) {
		for (AnvilRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
