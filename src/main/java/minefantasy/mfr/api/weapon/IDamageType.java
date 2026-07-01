package minefantasy.mfr.api.weapon;

public interface IDamageType {
	float[] getDamageRatio(Object... implement);

	float getPenetrationLevel(Object implement);
}
