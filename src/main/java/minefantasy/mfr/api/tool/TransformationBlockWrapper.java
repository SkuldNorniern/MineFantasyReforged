package minefantasy.mfr.api.tool;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TransformationBlockWrapper {
	private ItemStack tool;
	private BlockPos pos;
	private BlockState state;
	private Integer progress;
	private Integer maxProgress;
	private String displayName;

	public TransformationBlockWrapper(ItemStack tool, BlockPos pos, BlockState state, int progress, int maxProgress, String displayName) {
		this.tool = tool;
		this.pos = pos;
		this.state = state;
		this.progress = progress;
		this.maxProgress = maxProgress;
		this.displayName = displayName;
	}

	public int getProgressMetre(double i) {
		return (int) Math.ceil(i / this.maxProgress * this.progress);
	}

	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putLong("pos", this.pos.asLong());
		nbt.put("state", net.minecraft.nbt.NbtUtils.writeBlockState(this.state));
		// TODO: ItemStack serialization requires HolderLookup.Provider in MC 1.20.5+
		nbt.put("tool", new CompoundTag());
		nbt.putInt("progress", this.progress);
		nbt.putInt("maxProgress", this.maxProgress);
		nbt.putString("displayName", this.displayName);
		return nbt;
	}

	public static TransformationBlockWrapper deserializeNBT(CompoundTag nbt) {
		// TODO: BlockState deserialization requires HolderLookup.Provider — stubbed until BlockEntity context is available
		ItemStack tagTool = ItemStack.EMPTY;
		BlockPos tagPos = BlockPos.of(nbt.getLong("pos").orElse(0L));
		BlockState tagState = net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
		int tagProgress = nbt.getInt("progress").orElse(0);
		int tagMaxProgress = nbt.getInt("maxProgress").orElse(0);
		String tagDisplayName = nbt.getString("displayName").orElse("");
		return new TransformationBlockWrapper(tagTool, tagPos, tagState, tagProgress, tagMaxProgress, tagDisplayName);
	}

	public static boolean checkTransformationBlock(TransformationBlockWrapper transformationBlock, BlockState state, BlockPos pos) {
		return transformationBlock.getState() == state && transformationBlock.getPos().equals(pos);
	}

	public ItemStack getTool() { return tool; }
	public void setTool(ItemStack tool) { this.tool = tool; }
	public BlockPos getPos() { return pos; }
	public void setPos(BlockPos pos) { this.pos = pos; }
	public BlockState getState() { return state; }
	public void setState(BlockState state) { this.state = state; }
	public Integer getProgress() { return progress; }
	public void setProgress(Integer progress) { this.progress = progress; }
	public Integer getMaxProgress() { return maxProgress; }
	public void setMaxProgress(Integer maxProgress) { this.maxProgress = maxProgress; }
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }
}
