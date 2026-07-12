package minefantasy.mfr.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import minefantasy.mfr.registry.material.CustomMaterial;
import minefantasy.mfr.registry.material.CustomMaterialRegistry;
import minefantasy.mfr.registry.material.types.CustomMaterialType;
import minefantasy.mfr.util.CustomToolHelper;
import minefantasy.mfr.util.NbtUtils;
import minefantasy.mfr.util.ToolHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber
public class CommandMFR {

	@SubscribeEvent
	public static void onRegisterCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(
			Commands.literal("CommandMF")
				.requires(src -> src.getEntity() instanceof ServerPlayer)
				.then(Commands.literal("edit")
					.then(Commands.literal("material")
						.then(Commands.argument("material", StringArgumentType.string())
							.suggests((ctx, builder) -> {
								for (CustomMaterial m : CustomMaterialRegistry.getValues()) {
									if (m.getType() == CustomMaterialType.WOOD_MATERIAL
											|| m.getType() == CustomMaterialType.METAL_MATERIAL) {
										builder.suggest(m.getName());
									}
								}
								return builder.buildFuture();
							})
							.executes(ctx -> {
								ServerPlayer player = ctx.getSource().getPlayerOrException();
								ItemStack item = player.getMainHandItem();
								String matName = StringArgumentType.getString(ctx, "material");
								if (item.isEmpty() || !CustomToolHelper.hasAnyMaterial(item)) {
									player.sendSystemMessage(Component.translatable("command.invalid.item"));
									return 0;
								}
								CustomMaterial material = CustomMaterialRegistry.getMaterial(matName);
								if (material == CustomMaterialRegistry.NONE) {
									player.sendSystemMessage(Component.translatable("command.edit.invalid.material"));
									return 0;
								}
								String slot = material.getType() == CustomMaterialType.METAL_MATERIAL
										? CustomToolHelper.slot_main : CustomToolHelper.slot_haft;
								CustomMaterialRegistry.addMaterial(item, slot, material.getName());
								player.sendSystemMessage(Component.translatable("command.edit.success"));
								return Command.SINGLE_SUCCESS;
							})))
					.then(Commands.literal("quality")
						.then(Commands.argument("level", IntegerArgumentType.integer(0, 200))
							.executes(ctx -> {
								ServerPlayer player = ctx.getSource().getPlayerOrException();
								ItemStack item = player.getMainHandItem();
								int level = IntegerArgumentType.getInteger(ctx, "level");
								item = ToolHelper.setQuality(item, level);
								CompoundTag tag = NbtUtils.getOrCreateNBT(item);
								if (level <= 50) tag.putBoolean("MF_Inferior", true);
								if (level >= 150) tag.putBoolean("MF_Inferior", false);
								NbtUtils.saveNBT(item, tag);
								player.sendSystemMessage(Component.translatable("command.edit.success"));
								return Command.SINGLE_SUCCESS;
							})))
					.then(Commands.literal("unbreakable")
						.then(Commands.argument("value", BoolArgumentType.bool())
							.executes(ctx -> {
								ServerPlayer player = ctx.getSource().getPlayerOrException();
								ItemStack item = player.getMainHandItem();
								boolean value = BoolArgumentType.getBool(ctx, "value");
								ToolHelper.setUnbreakable(item, value);
								player.sendSystemMessage(Component.translatable("command.edit.success"));
								return Command.SINGLE_SUCCESS;
							})))));
	}
}
