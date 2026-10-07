package ca.akeuben.adventuringsigns.mixin;

import ca.akeuben.adventuringsigns.AdventuringSign;
import ca.akeuben.adventuringsigns.ui.AdvancedSignEditor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.level.block.SignBlock")
public abstract class MixinSignBlock {

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void adventuringsigns$useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if(level.getBlockEntity(pos) instanceof SignBlockEntity vanillaSign) {

            AdventuringSign sign = (AdventuringSign) vanillaSign;

            if (hand == InteractionHand.OFF_HAND)
                return;

            if (itemStack.getItem() != Items.INK_SAC && !(itemStack.isEmpty() && sign.isAdventuring()))
                return;

            if(level.isClientSide()) {
                cir.setReturnValue(InteractionResult.SUCCESS);
                return;
            }

            player.openDialog(Holder.direct(AdvancedSignEditor.advancedSignEditor(sign, player)));

            ((ServerPlayer) player).connection.send(
                    new ClientboundSoundEntityPacket(
                            Holder.direct(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT),
                            SoundSource.BLOCKS,
                            player,
                            1.0F,
                            1.0F,
                            player.getRandom().nextLong()
                    )
            );

            if(player.gameMode() != GameType.CREATIVE && !sign.isAdventuring())
                player.getInventory().removeItem(player.getInventory().getSelectedSlot(), 1);

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }


}
