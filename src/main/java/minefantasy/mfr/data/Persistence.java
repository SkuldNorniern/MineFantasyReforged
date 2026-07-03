package minefantasy.mfr.data;

public enum Persistence {

	NEVER(false, false),
	DIMENSION_CHANGE(false, true),
	RESPAWN(true, false),
	ALWAYS(true, true);

	private final boolean persistsOnRespawn;
	private final boolean persistsOnDimensionChange;

	Persistence(boolean persistsOnRespawn, boolean persistsOnDimensionChange) {
		this.persistsOnRespawn = persistsOnRespawn;
		this.persistsOnDimensionChange = persistsOnDimensionChange;
	}

	public boolean persistsOnRespawn() {
		return persistsOnRespawn;
	}

	public boolean persistsOnDimensionChange() {
		return persistsOnDimensionChange;
	}
}
