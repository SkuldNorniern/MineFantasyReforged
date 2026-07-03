package minefantasy.mfr.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public final class EntityUtils {

	private EntityUtils() {}

	public static List<LivingEntity> getLivingWithinRadius(double radius, double x, double y, double z, Level world) {
		return getEntitiesWithinRadius(radius, x, y, z, world, LivingEntity.class);
	}

	public static <T extends Entity> List<T> getEntitiesWithinRadius(double radius, double x, double y, double z, Level world, Class<T> entityType) {
		AABB aabb = new AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius);
		List<T> entityList = world.getEntitiesOfClass(entityType, aabb);
		entityList.removeIf(e -> Math.sqrt(e.distanceToSqr(x, y, z)) > radius);
		return entityList;
	}
}
