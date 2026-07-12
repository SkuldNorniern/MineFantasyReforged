package minefantasy.mfr.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;

public class BlockUtils {

	public static void notifyBlockUpdate(Level level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		level.sendBlockUpdated(pos, state, state, 3);
	}

	public static void notifyBlockUpdate(BlockEntity tile) {
		notifyBlockUpdate(tile.getLevel(), tile.getBlockPos());
	}

	public static boolean isBlockWithinHorizontalRange(BlockPos blockPos, BlockPos pointPos, int range) {
		int dx = blockPos.getX() - pointPos.getX();
		int dz = blockPos.getZ() - pointPos.getZ();
		int distance = (int) Math.sqrt(dx * dx + dz * dz);
		return distance <= range;
	}

	public static boolean isWaterSource(Level level, BlockPos pos) {
		if (level.getFluidState(pos).is(Fluids.WATER)) {
			return true;
		}
		if (level.getBlockState(pos).is(Blocks.CAULDRON)) {
			return true;
		}
		// TODO: check TileEntityTrough when it is ported
		return false;
	}

	public static <T extends Comparable<T>> BlockState transferOldToNewProperty(
			BlockState newState,
			BlockState oldState,
			Property<T> property) {
		return newState.setValue(property, oldState.getValue(property));
	}

	public static ChestBlockEntity getOtherDoubleChest(BlockEntity inv) {
		// TODO: double-chest adjacency fields were removed in MC 1.13+; use ChestBlock.getConnectedChest or
		//       iterate adjacent positions if needed when tile entities are ported
		return null;
	}

	public static int[] getCoordsFor(float clickX, float clickY, float xBound, float xBound2, float yBound, float yBound2, int xSlots, int ySlots, Direction facing) {
		if (clickX < xBound || clickX > xBound2 || clickY < yBound || clickY > yBound2) {
			return null;
		}

		clickX -= xBound;
		clickY -= yBound;

		float xMax = xBound2 - xBound;
		float yMax = yBound2 - yBound;

		int xSlot = 0;
		int ySlot = 0;

		float xSpace = xMax / xSlots;
		float ySpace = yMax / ySlots;

		for (int xT = 0; xT < xSlots; xT++) {
			float minSpace = xSpace * xT;
			float maxSpace = xSpace * (xT + 1);
			if (clickX < maxSpace && clickX > minSpace) {
				xSlot = xT;
			}
		}

		for (int yT = 0; yT < ySlots; yT++) {
			float minSpace = ySpace * yT;
			float maxSpace = ySpace * (yT + 1);
			if (clickY < maxSpace && clickY > minSpace) {
				ySlot = yT;
			}
		}
		return translateCoords(xSlot, ySlot, xSlots, ySlots, facing);
	}

	public static int[] translateCoords(int x, int y, int maxX, int maxY, Direction facing) {
		if (facing == Direction.NORTH) {
			return new int[]{maxX - x - 1, maxY - y - 1};
		}
		if (facing == Direction.WEST) {
			return new int[]{y, maxX - x - 1};
		}
		if (facing == Direction.EAST) {
			return new int[]{maxY - y - 1, x};
		}
		return new int[]{x, y};
	}
}
