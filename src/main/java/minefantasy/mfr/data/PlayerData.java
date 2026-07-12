package minefantasy.mfr.data;

import minefantasy.mfr.MineFantasyReforged;
import minefantasy.mfr.init.ModAttachmentTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Per-player data stored via NeoForge attachment (replaces Forge capability).
 * Credit: inspired by Electroblob's capability design.
 */
@EventBusSubscriber
public class PlayerData {

	private final @Nullable Player player;
	private static final List<IStoredVariable<?>> storedVariables = new ArrayList<>();
	private final Map<IVariable<?>, Object> playerData;

	public PlayerData(@Nullable Player player) {
		this.player = player;
		this.playerData = new HashMap<>();
	}

	public static PlayerData get(Player player) {
		return player.getData(ModAttachmentTypes.PLAYER_DATA.get());
	}

	public static void registerStoredVariables(IStoredVariable<?>... variables) {
		storedVariables.addAll(Arrays.asList(variables));
	}

	public static List<IVariable<?>> getSyncedVariables() {
		return storedVariables.stream().filter(IVariable::isSynced).collect(Collectors.toList());
	}

	public <T> void setVariable(IVariable<? super T> variable, T value) {
		playerData.put(variable, value);
	}

	@SuppressWarnings("unchecked")
	public @Nullable <T> T getVariable(IVariable<T> variable) {
		return (T) playerData.get(variable);
	}

	@SuppressWarnings("unchecked")
	private void update() {
		playerData.forEach((k, v) -> playerData.put(k, ((IVariable<Object>) k).update(player, v)));
		playerData.keySet().removeIf(k -> ((IVariable<Object>) k).canPurge(player, playerData.get(k)));
	}

	public void copyFrom(PlayerData data, boolean respawn) {
		for (IVariable<?> variable : data.playerData.keySet()) {
			if (variable.isPersistent(respawn)) {
				playerData.put(variable, data.playerData.get(variable));
			}
		}
	}

	public void sync() {
		// TODO: port PlayerDataPacket — sync player data to client
	}

	@SuppressWarnings("unchecked")
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		storedVariables.forEach(k -> ((IStoredVariable<Object>) k).write(tag, playerData.get(k)));
		return tag;
	}

	public void deserializeNBT(CompoundTag nbt) {
		try {
			storedVariables.forEach(k -> playerData.put(k, k.read(nbt)));
		} catch (ClassCastException e) {
			MineFantasyReforged.LOG.error("Player data NBT tag was not of expected type!", e);
		}
	}

	// ——— Events ———

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event) {
		PlayerData newData = PlayerData.get(event.getEntity());
		PlayerData oldData = PlayerData.get(event.getOriginal());
		newData.copyFrom(oldData, event.isWasDeath());
		newData.sync();
	}

	@SubscribeEvent
	public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
		if (!event.getLevel().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
			PlayerData data = PlayerData.get(player);
			// TODO: RPGElements.initSkills(data) when RPGElements is ported
			// TODO: ResearchLogic.syncData(player) when ResearchLogic is ported
			data.sync();
		}
	}

	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Post event) {
		if (event.getEntity() instanceof Player player) {
			PlayerData.get(player).update();
		}
	}
}
