package minefantasy.mfr.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.Optional;

public class WorldUtils {

	public static Optional<BlockEntity> getTile(BlockGetter world, BlockPos pos) {
		return getTile(world, pos, BlockEntity.class);
	}

	public static <T> Optional<T> getTile(@Nullable BlockGetter world, @Nullable BlockPos pos, Class<T> teClass) {
		if (world == null || pos == null) {
			return Optional.empty();
		}

		BlockEntity te = world.getBlockEntity(pos);

		if (teClass.isInstance(te)) {
			return Optional.of(teClass.cast(te));
		}

		return Optional.empty();
	}
}
