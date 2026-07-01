package minefantasy.mfr.api.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public interface IIgnitable {

	void igniteBlock(Level world, BlockPos pos, BlockState state);

	static void playIgnitionSound(Level world, BlockPos pos) {
		Random rand = new Random();
		float pitch = (0.2F + (0.4F * rand.nextFloat()));
		world.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.AMBIENT, 0.33F, pitch);
	}
}
