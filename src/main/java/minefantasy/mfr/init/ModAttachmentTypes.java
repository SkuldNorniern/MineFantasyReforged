package minefantasy.mfr.init;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.data.PlayerData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class ModAttachmentTypes {

	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
			DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MineFantasyReforged.MOD_ID);

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<PlayerData>> PLAYER_DATA =
			ATTACHMENT_TYPES.register("player_data", () -> AttachmentType
					.<PlayerData>builder(holder -> {
						if (holder instanceof Player player) return new PlayerData(player);
						throw new IllegalStateException("PlayerData attachment used on non-player: " + holder);
					})
					.serialize(new IAttachmentSerializer<>() {
						@Override
						public @Nullable PlayerData read(IAttachmentHolder holder, ValueInput input) {
							if (!(holder instanceof Player player)) return null;
							PlayerData data = new PlayerData(player);
							input.read("data", CompoundTag.CODEC).ifPresent(data::deserializeNBT);
							return data;
						}

						@Override
						public boolean write(PlayerData attachment, ValueOutput output) {
							CompoundTag tag = attachment.serializeNBT();
							if (!tag.isEmpty()) {
								output.store("data", CompoundTag.CODEC, tag);
								return true;
							}
							return false;
						}
					})
					.build());
}
