package minefantasy.mfr.api.heating;

import minefantasy.mfr.util.NbtUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;

import static net.minecraft.world.level.block.Blocks.CAULDRON;

public class TongsHelper {

	public static boolean hasHeldItem(ItemStack tongs) {
		CompoundTag nbt = NbtUtils.getOrCreateNBT(tongs);
		return nbt.contains("Held") && nbt.getBoolean("Held").orElse(false);
	}

	public static ItemStack clearHeldItem(ItemStack tongs, LivingEntity user) {
		if (!user.level().isClientSide()) {
			CompoundTag nbt = NbtUtils.getOrCreateNBT(tongs);
			nbt.putBoolean("Held", false);
			NbtUtils.saveNBT(tongs, nbt);
		}
		tongs.hurtAndBreak(1, user, EquipmentSlot.MAINHAND);
		return tongs;
	}

	public static boolean trySetHeldItem(ItemStack tongs, ItemStack item) {
		if (item.isEmpty() || !isHotItem(item) || item.getItem() instanceof BlockItem) {
			return false;
		}
		CompoundTag nbt = NbtUtils.getOrCreateNBT(tongs);
		nbt.putBoolean("Held", true);
		// TODO: ItemStack.save() requires HolderLookup.Provider in MC 26 — stub for now
		NbtUtils.saveNBT(tongs, nbt);
		return true;
	}

	public static boolean isHotItem(ItemStack item) {
		if (item.getItem() instanceof IHotItem hot) {
			return hot.isHot(item);
		}
		return false;
	}

	public static boolean isCoolableItem(ItemStack item) {
		if (item.getItem() instanceof IHotItem hot) {
			return hot.isCoolable(item);
		}
		return false;
	}

	public static ItemStack getHeldItem(ItemStack tongs) {
		CompoundTag nbt = NbtUtils.getOrCreateNBT(tongs);
		if (nbt.contains("Held") && nbt.getBoolean("Held").orElse(false) && nbt.contains("Saved")) {
			// TODO: ItemStack.parseOptional() requires HolderLookup.Provider in MC 26
			// CompoundTag save = nbt.getCompound("Saved").orElseGet(CompoundTag::new);
			// return ItemStack.parseOptional(provider, save).orElse(ItemStack.EMPTY);
		}
		return ItemStack.EMPTY;
	}

	public static ItemStack getHeldItemTongs(ItemStack tongs) {
		return getHeldItem(tongs);
	}

	public static float getWaterSource(Level level, BlockPos pos) {
		float special = TongsHelper.getQuenced(level, pos);
		if (special >= 0) {
			return special;
		}
		if (level.getFluidState(pos).is(Fluids.WATER)) {
			level.removeBlock(pos, false);
			return 25F;
		}
		if (isCauldron(level, pos)) {
			return 10F;
		}
		return -1F;
	}

	public static boolean isCauldron(Level level, BlockPos pos) {
		return level.getBlockState(pos).getBlock() == CAULDRON;
	}

	public static float getQuenced(Level level, BlockPos pos) {
		var tile = level.getBlockEntity(pos);
		if (tile instanceof IQuenchBlock quench) {
			return quench.quench();
		}
		return -1F;
	}
}
