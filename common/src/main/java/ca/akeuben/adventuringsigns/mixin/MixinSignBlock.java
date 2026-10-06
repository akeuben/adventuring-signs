package ca.akeuben.adventuringsigns.mixin;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.level.block.SignBlock")
public class MixinSignBlock {

    private final MiniMessage mm = MiniMessage.miniMessage();

    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    private void adventuringsigns$useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if(level.getBlockEntity(pos) instanceof SignBlockEntity sign) {
            if (hand == InteractionHand.OFF_HAND)
                return;

            if (itemStack.getItem() != Items.INK_SAC)
                return;

            if(!level.isClientSide()) {
                cir.setReturnValue(InteractionResult.SUCCESS);
                return;
            }

            player.sendSystemMessage((Component) mm.deserialize("Hello <rainbow>World</rainbow>"));

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
