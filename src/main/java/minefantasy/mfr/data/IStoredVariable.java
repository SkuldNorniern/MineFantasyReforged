package minefantasy.mfr.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import minefantasy.mfr.api.tool.TransformationBlockWrapper;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface IStoredVariable<T> extends IVariable<T> {

	void write(CompoundTag nbt, T value);

	T read(CompoundTag nbt);

	class StoredVariable<T, E extends Tag> implements IStoredVariable<T> {

		private final String key;
		private final Persistence persistence;
		private final Function<T, E> serialiser;
		private final Function<E, T> deserialiser;
		private boolean synced;
		private BiFunction<Player, T, T> ticker;

		public StoredVariable(String key, Function<T, E> serialiser, Function<E, T> deserialiser, Persistence persistence) {
			this.key = key;
			this.serialiser = serialiser;
			this.deserialiser = deserialiser;
			this.persistence = persistence;
			this.ticker = (p, t) -> t;
		}

		public StoredVariable<T, E> withTicker(BiFunction<Player, T, T> ticker) {
			this.ticker = ticker;
			return this;
		}

		public StoredVariable<T, E> setSynced() {
			this.synced = true;
			return this;
		}

		@Override
		public void write(CompoundTag nbt, T value) {
			if (value != null) minefantasy.mfr.util.NbtUtils.storeTagSafely(nbt, key, serialiser.apply(value));
		}

		@Override
		@SuppressWarnings("unchecked")
		public T read(CompoundTag nbt) {
			return nbt.contains(key) ? deserialiser.apply((E) nbt.get(key)) : null;
		}

		@Override
		public T update(Player player, T value) {
			return ticker.apply(player, value);
		}

		@Override
		public boolean isPersistent(boolean respawn) {
			return respawn ? persistence.persistsOnRespawn() : persistence.persistsOnDimensionChange();
		}

		@Override
		public boolean isSynced() {
			return synced;
		}

		@Override
		public void write(io.netty.buffer.ByteBuf buf, T value) {
			if (!synced || !(buf instanceof FriendlyByteBuf friendly)) return;
			CompoundTag nbt = new CompoundTag();
			write(nbt, value);
			friendly.writeNbt(nbt);
		}

		@Override
		public T read(io.netty.buffer.ByteBuf buf) {
			if (!synced || !(buf instanceof FriendlyByteBuf friendly)) return null;
			CompoundTag nbt = friendly.readNbt();
			return nbt != null ? read(nbt) : null;
		}

		// Factory methods

		public static StoredVariable<Byte, ByteTag> ofByte(String key, Persistence persistence) {
			return new StoredVariable<>(key, ByteTag::valueOf, ByteTag::getAsByte, persistence);
		}

		public static StoredVariable<Boolean, ByteTag> ofBoolean(String key, Persistence persistence) {
			return new StoredVariable<>(key, b -> ByteTag.valueOf(b), t -> t.getAsByte() == 1, persistence);
		}

		public static StoredVariable<Integer, IntTag> ofInt(String key, Persistence persistence) {
			return new StoredVariable<>(key, IntTag::valueOf, IntTag::getAsInt, persistence);
		}

		public static StoredVariable<int[], IntArrayTag> ofIntArray(String key, Persistence persistence) {
			return new StoredVariable<>(key, IntArrayTag::new, IntArrayTag::getAsIntArray, persistence);
		}

		public static StoredVariable<Float, FloatTag> ofFloat(String key, Persistence persistence) {
			return new StoredVariable<>(key, FloatTag::valueOf, FloatTag::getAsFloat, persistence);
		}

		public static StoredVariable<Double, DoubleTag> ofDouble(String key, Persistence persistence) {
			return new StoredVariable<>(key, DoubleTag::valueOf, DoubleTag::getAsDouble, persistence);
		}

		public static StoredVariable<Short, ShortTag> ofShort(String key, Persistence persistence) {
			return new StoredVariable<>(key, ShortTag::valueOf, ShortTag::getAsShort, persistence);
		}

		public static StoredVariable<Long, LongTag> ofLong(String key, Persistence persistence) {
			return new StoredVariable<>(key, LongTag::valueOf, LongTag::getAsLong, persistence);
		}

		public static StoredVariable<String, StringTag> ofString(String key, Persistence persistence) {
			return new StoredVariable<>(key, StringTag::valueOf, StringTag::getAsString, persistence);
		}

		public static StoredVariable<BlockPos, CompoundTag> ofBlockPos(String key, Persistence persistence) {
			return new StoredVariable<>(key, NbtUtils::writeBlockPos, NbtUtils::readBlockPos, persistence);
		}

		public static StoredVariable<UUID, IntArrayTag> ofUUID(String key, Persistence persistence) {
			return new StoredVariable<>(key, NbtUtils::createUUID, t -> NbtUtils.loadUUID(t), persistence);
		}

		/** ItemStack serialization requires HolderLookup.Provider — stub returns empty stack. */
		public static StoredVariable<ItemStack, CompoundTag> ofItemStack(String key, Persistence persistence) {
			return new StoredVariable<>(key, stack -> new CompoundTag(), nbt -> ItemStack.EMPTY, persistence);
		}

		public static StoredVariable<CompoundTag, CompoundTag> ofNBT(String key, Persistence persistence) {
			return new StoredVariable<>(key, t -> t, t -> t, persistence);
		}

		public static StoredVariable<TransformationBlockWrapper, CompoundTag> ofTransformationBlockWrapper(String key, Persistence persistence) {
			return new StoredVariable<>(key, TransformationBlockWrapper::serializeNBT, TransformationBlockWrapper::deserializeNBT, persistence);
		}
	}
}
