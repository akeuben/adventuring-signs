package ca.akeuben.adventuringsigns.mixin;

import ca.akeuben.adventuringsigns.AdventuringSign;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.entity.SignTextSlot;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignBlockEntity.class)
public abstract class MixinSignBlockEntity implements AdventuringSign {

    @Shadow
    public abstract SignText getText(SignTextSlot slot);

    @Unique
    private final String[] adventuringsigns$content = new String[4];

    @Inject(
            method = "saveAdditional",
            at = @At("TAIL")
    )
    private void adventuringsigns$saveAdditional(
            ValueOutput output,
            CallbackInfo ci
    ) {
        for(int i = 0; i < adventuringsigns$content.length; i++) {
            if(adventuringsigns$content[i] != null)
                output.putString(
                        "adventuringsigns:line" + i,
                        this.adventuringsigns$content[i]
                );
        }
    }

    @Inject(
            method = "loadAdditional",
            at = @At("TAIL")
    )
    private void adventuringsigns$loadAdditional(
            ValueInput input,
            CallbackInfo ci
    ) {
        for(int i = 0; i < adventuringsigns$content.length; i++) {
            this.adventuringsigns$content[i] = input.getString("adventuringsigns:line" + i).orElse(null);
        }
    }

    @Override
    public @NonNull String adventuringsigns$getLine(int line) {
        String result = adventuringsigns$content[line];

        if(result == null)
            return getText(SignTextSlot.FRONT).getMessages(false).get(line).getString();

        return result;
    }

    @Override
    public void adventuringsigns$setLine(int line, @Nullable String content) {
        adventuringsigns$content[line] = content;
    }

    @Override
    public boolean isLineCustom(int line) {
        return adventuringsigns$content[line] != null;
    }
}
