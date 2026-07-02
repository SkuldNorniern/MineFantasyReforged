package minefantasy.mfr.registry.material.types;

import net.minecraft.util.StringRepresentable;

public enum CustomMaterialType implements StringRepresentable {
	WOOD_MATERIAL("wood_types"),
	METAL_MATERIAL("metal_types"),
	LEATHER_MATERIAL("leather_types"),
	NONE("");

	final String fileName;

	CustomMaterialType(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}

	public static CustomMaterialType deserialize(String name) {
		for (CustomMaterialType type : values()) {
			if (type.getSerializedName().equals(name)) {
				return type;
			}
		}
		return NONE;
	}

	public static CustomMaterialType getByNameWithModId(String name, String modId) {
		for (CustomMaterialType type : values()) {
			if ((modId + ":" + type.getSerializedName()).equals(name)) {
				return type;
			}
		}
		return NONE;
	}
}
