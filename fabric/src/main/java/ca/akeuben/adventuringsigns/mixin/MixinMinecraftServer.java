package ca.akeuben.adventuringsigns.mixin;

import ca.akeuben.adventuringsigns.Constants;
import ca.akeuben.adventuringsigns.SignApplicator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
    @Inject(method = "handleCustomClickAction", at = @At("HEAD"), cancellable = true)
    private void adventuringsigns$handleCustomClickAction(Identifier id, Optional<Tag> payload, CallbackInfo ci) {
        if(id.equals(Identifier.fromNamespaceAndPath(Constants.MOD_ID, Constants.SIGN_EDITOR_PATH + "/submit"))) {
            MinecraftServer server = (MinecraftServer) (Object) this;
            CompoundTag data = payload.get().asCompound().get();

            SignApplicator.applySign(server, data);

            ci.cancel();
        }
    }
}
