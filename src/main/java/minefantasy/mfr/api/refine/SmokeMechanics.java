package minefantasy.mfr.api.refine;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;

import java.util.Random;

public class SmokeMechanics {
	public static ISmokeHandler handler;
	private static Random rand = new Random();

	public static boolean emitSmokeFromCarrier(Level world, BlockPos pos, ISmokeCarrier tile, int max) {
		int value = tile.getSmokeValue();
		if (max >= 0 && value > max) value = max;
		if (value <= 0) return true;
		boolean success = emitSmoke(world, pos, value) != -1;
		if (success) modifySmoke(tile, -value);
		return success;
	}

	public static int emitSmoke(Level world, BlockPos pos, int value) {
		BlockEntity tile = world.getBlockEntity(pos.above());
		if (tile == null) {
			if (world.getBlockState(pos.above()).isFaceSturdy(world, pos.above(), Direction.DOWN)) {
				return -1;
			}
			spawnSmoke(world, pos.above(), value);
			return 1;
		} else {
			if (tile instanceof ISmokeCarrier carrier) {
				if (carrier.getSmokeValue() < carrier.getMaxSmokeStorage()) {
					modifySmoke(carrier, value);
					return 0;
				}
			} else {
				if (world.getBlockState(pos.above()).isFaceSturdy(world, pos.above(), Direction.DOWN)) {
					return -1;
				}
			}
		}
		return -1;
	}

	public static void modifySmoke(ISmokeCarrier tile, int value) {
		tile.setSmokeValue(tile.getSmokeValue() + value);
		if (tile.getSmokeValue() <= 0) tile.setSmokeValue(0);
	}

	public static void spawnSmoke(Level world, BlockPos pos, int value) {
		spawnSmokeD(world, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, value);
	}

	public static void spawnSmokeD(Level world, double x, double y, double z, int value) {
		if (!world.isClientSide() && handler != null) {
			handler.spawnSmoke(world, x, y, z, value);
		}
		if (!world.isClientSide() && world instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(ParticleTypes.SMOKE, x, y, z, value, 0.1D, 0.1D, 0.1D, 0.0D);
		}
	}

	public static boolean tryUseChimney(Level world, BlockPos pos, int value) {
		return tryUseChimney(world, pos, value, true);
	}

	public static boolean tryUseChimney(Level world, BlockPos pos, int value, boolean indirect) {
		BlockEntity tile = world.getBlockEntity(pos);
		if (tile instanceof ISmokeCarrier chimney) {
			if ((!indirect || chimney.canAbsorbIndirect()) && chimney.getSmokeValue() < chimney.getMaxSmokeStorage()) {
				modifySmoke(chimney, value);
				return true;
			}
		}
		return false;
	}

	public static void emitSmokeIndirect(Level world, BlockPos pos, int value) {
		if (tryUseChimney(world, pos, value)) return;
		for (int x = -1; x <= 1; x++) {
			for (int y = 0; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					if (tryUseChimney(world, pos.offset(x, y, z), value)) return;
				}
			}
		}
		spawnSmoke(world, pos, value);
	}
}
