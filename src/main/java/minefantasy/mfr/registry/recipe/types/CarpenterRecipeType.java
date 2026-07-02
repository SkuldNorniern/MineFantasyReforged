package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum CarpenterRecipeType implements StringRepresentable, IRecipeMFRType {
	CARPENTER_SHAPED_RECIPE,
	CARPENTER_SHAPELESS_RECIPE,
	CARPENTER_SHAPED_CUSTOM_MATERIAL_RECIPE,
	CARPENTER_SHAPELESS_CUSTOM_MATERIAL_RECIPE,
	CARPENTER_DYNAMIC_RECIPE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static CarpenterRecipeType deserialize(String name) {
		for (CarpenterRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public CarpenterRecipeType getByNameWithModId(String name, String modId) {
		for (CarpenterRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
