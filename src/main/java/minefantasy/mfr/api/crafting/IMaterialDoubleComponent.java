package minefantasy.mfr.api.crafting;

import minefantasy.mfr.registry.material.types.CustomMaterialType;

public interface IMaterialDoubleComponent extends IMaterialComponent {
	CustomMaterialType getPrimaryMaterialType();

	CustomMaterialType getSecondaryMaterialType();
}
