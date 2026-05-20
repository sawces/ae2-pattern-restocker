package com.vaded.ae2patternrestocker.mixin;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.storage.MEStorage;
import appeng.core.definitions.AEItems;
import appeng.menu.me.items.PatternEncodingTermMenu;
import appeng.menu.slot.RestrictedInputSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PatternEncodingTermMenu.class)
public abstract class PatternEncodingTermMenuMixin {
    @Shadow
    @Final
    private RestrictedInputSlot blankPatternSlot;

    @Inject(method = "encode", at = @At("HEAD"))
    private void ae2PatternRestocker$fillBlankPattern(CallbackInfo ci) {
        PatternEncodingTermMenu self = (PatternEncodingTermMenu) (Object) this;

        if (self.isClientSide()) {
            return;
        }

        ItemStack current = this.blankPatternSlot.getItem();

        if (!current.isEmpty()) {
            return;
        }

        MEStorage storage = ((MEStorageMenuAccessor) self).ae2PatternRestocker$getStorage();

        if (storage == null) {
            return;
        }

        AEItemKey key = AEItemKey.of(AEItems.BLANK_PATTERN.asItem());

        long extracted = storage.extract(
                key,
                64,
                Actionable.MODULATE,
                IActionSource.ofPlayer(self.getPlayer())
        );

        if (extracted <= 0) {
            return;
        }

        this.blankPatternSlot.set(
                AEItems.BLANK_PATTERN.stack((int) extracted)
        );
    }
}
