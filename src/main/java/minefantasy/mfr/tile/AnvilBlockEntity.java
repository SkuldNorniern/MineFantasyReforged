package minefantasy.mfr.tile;

import minefantasy.mfr.init.ModBlockEntities;
import minefantasy.mfr.init.ModRecipeTypes;
import minefantasy.mfr.menu.AnvilMenu;
import minefantasy.mfr.registry.recipe.AnvilRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Optional;

/**
 * Anvil crafting station. Simplified from the legacy TileEntityAnvil: crafts instantly once the
 * 6x4 grid matches a registered {@link AnvilRecipe} (via vanilla {@code RecipeManager}), instead
 * of the original hit-quality/hammering minigame — that depends on unported systems (network
 * packets, PlayerTickHandler, research gating) and is deferred until those exist.
 */
public class AnvilBlockEntity extends BaseContainerBlockEntity {

	public static final int WIDTH = 6;
	public static final int HEIGHT = 4;
	public static final int GRID_SIZE = WIDTH * HEIGHT;
	public static final int RESULT_SLOT = GRID_SIZE;
	public static final int SLOT_COUNT = GRID_SIZE + 1;

	private NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
	private boolean updatingResult = false;

	public AnvilBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ANVIL.get(), pos, state);
	}

	@Override
	public int getContainerSize() {
		return SLOT_COUNT;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items = items;
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("tile.anvil_stone.name");
	}

	@Override
	protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
		return new AnvilMenu(containerId, inventory, this);
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (!updatingResult) {
			updateResult();
		}
	}

	private void updateResult() {
		if (level == null || level.isClientSide()) {
			return;
		}
		updatingResult = true;
		try {
			CraftingInput input = CraftingInput.of(WIDTH, HEIGHT, items.subList(0, GRID_SIZE));
			RecipeManager recipeManager = (RecipeManager) level.recipeAccess();
			Optional<RecipeHolder<AnvilRecipe>> match = recipeManager.getRecipeFor(ModRecipeTypes.ANVIL.get(), input, level);
			ItemStack result = match.map(holder -> holder.value().assemble(input)).orElse(ItemStack.EMPTY);
			items.set(RESULT_SLOT, result);
		} finally {
			updatingResult = false;
		}
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		net.minecraft.world.ContainerHelper.saveAllItems(output, items);
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
		net.minecraft.world.ContainerHelper.loadAllItems(input, items);
	}

	public static BlockEntityType.BlockEntitySupplier<AnvilBlockEntity> factory() {
		return AnvilBlockEntity::new;
	}
}
