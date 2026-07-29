package minefantasy.mfr.menu;

import minefantasy.mfr.init.ModMenuTypes;
import minefantasy.mfr.tile.AnvilBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class AnvilMenu extends AbstractContainerMenu {

	private static final int GRID_LEFT = 44;
	private static final int GRID_TOP = 38;
	private static final int RESULT_X = 214;
	private static final int RESULT_Y = 66;
	private static final int INV_LEFT = 36;
	private static final int INV_TOP = 186;

	private final Container anvil;

	public AnvilMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf extraData) {
		this(containerId, inventory, resolveBlockEntity(inventory, extraData.readBlockPos()));
	}

	public AnvilMenu(int containerId, Inventory inventory, Container anvil) {
		super(ModMenuTypes.ANVIL.get(), containerId);
		this.anvil = anvil;

		for (int y = 0; y < AnvilBlockEntity.HEIGHT; y++) {
			for (int x = 0; x < AnvilBlockEntity.WIDTH; x++) {
				int slot = x + y * AnvilBlockEntity.WIDTH;
				this.addSlot(new Slot(anvil, slot, GRID_LEFT + x * 18, GRID_TOP + y * 18));
			}
		}

		this.addSlot(new Slot(anvil, AnvilBlockEntity.RESULT_SLOT, RESULT_X, RESULT_Y) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return false;
			}
		});

		this.addStandardInventorySlots(inventory, INV_LEFT, INV_TOP);
	}

	private static Container resolveBlockEntity(Inventory inventory, BlockPos pos) {
		BlockEntity blockEntity = inventory.player.level().getBlockEntity(pos);
		if (blockEntity instanceof AnvilBlockEntity anvilBlockEntity) {
			return anvilBlockEntity;
		}
		return new SimpleContainer(AnvilBlockEntity.SLOT_COUNT);
	}

	@Override
	public boolean stillValid(Player player) {
		return anvil.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		ItemStack copy = ItemStack.EMPTY;
		Slot slot = this.slots.get(slotIndex);
		if (slot != null && slot.hasItem()) {
			ItemStack stack = slot.getItem();
			copy = stack.copy();
			int gridEnd = AnvilBlockEntity.SLOT_COUNT;

			if (slotIndex < gridEnd) {
				if (!this.moveItemStackTo(stack, gridEnd, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(stack, 0, AnvilBlockEntity.GRID_SIZE, false)) {
				return ItemStack.EMPTY;
			}

			if (stack.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (stack.getCount() == copy.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, stack);
		}
		return copy;
	}
}
