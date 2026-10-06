package ca.akeuben.adventuringsigns.mixin;

import ca.akeuben.adventuringsigns.platform.Services;
import ca.akeuben.adventuringsigns.text.TextTransformer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.modcommon.MinecraftAudiences;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.entity.SignTextSlot;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(targets = "net.minecraft.world.level.block.SignBlock")
public abstract class MixinSignBlock {



    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void adventuringsigns$useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if(level.getBlockEntity(pos) instanceof SignBlockEntity sign) {
            if (hand == InteractionHand.OFF_HAND)
                return;

            if (itemStack.getItem() != Items.INK_SAC)
                return;

            if(level.isClientSide()) {
                cir.setReturnValue(InteractionResult.SUCCESS);
                return;
            }

            player.sendSystemMessage(TextTransformer.parseMiniMessage("Hello <rainbow>World!</rainbow>"));

            // TODO: Check character width

            sign.setText(new SignText(
                    List.of(
                            TextTransformer.parseMiniMessage("Hello <rainbow>World!</rainbow>"),
                            Component.empty(),
                            Component.empty(),
                            Component.empty()
                    ),
                    List.of(
                            TextTransformer.parseMiniMessage("Hello <rainbow>World!</rainbow>"),
                            Component.empty(),
                            Component.empty(),
                            Component.empty()
                    ),
                    DyeColor.BLACK,
                    sign.getText(SignTextSlot.FRONT).hasGlowingText()
            ), SignTextSlot.FRONT);

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }


}
