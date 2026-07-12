package minefantasy.mfr.init;

import com.mojang.serialization.Codec;
import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {

	public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
			DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MineFantasyReforged.MOD_ID);

	public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MULTI_USE_BITES =
			DATA_COMPONENTS.register("multi_use_bites", () -> DataComponentType.<Integer>builder()
					.persistent(Codec.INT)
					.build());
}
