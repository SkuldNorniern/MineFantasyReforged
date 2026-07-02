package minefantasy.mfr.registry.recipe.types;

import net.minecraft.util.StringRepresentable;

public enum TannerRecipeType implements StringRepresentable, IRecipeMFRType {
	TANNER_RECIPE,
	NONE;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static TannerRecipeType deserialize(String name) {
		for (TannerRecipeType type : values()) {
			if (type.getSerializedName().equals(name)) return type;
		}
		return NONE;
	}

	@Override
	public TannerRecipeType getByNameWithModId(String name, String modId) {
		for (TannerRecipeType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) return type;
		}
		return NONE;
	}
}
