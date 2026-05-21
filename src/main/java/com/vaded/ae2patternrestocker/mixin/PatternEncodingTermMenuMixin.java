package com.vaded.ae2patternrestocker.mixin;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.storage.MEStorage;
import appeng.core.definitions.AEItems;
import appeng.helpers.IPatternTerminalMenuHost;
import appeng.menu.guisync.ClientActionKey;
import appeng.menu.me.items.PatternEncodingTermMenu;
import appeng.menu.slot.RestrictedInputSlot;
import com.vaded.ae2patternrestocker.AE2PatternRestockerConfig;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PatternEncodingTermMenu.class)
public abstract class PatternEncodingTermMenuMixin {
    @Shadow @Final private RestrictedInputSlot blankPatternSlot;
    @Shadow @Final private RestrictedInputSlot encodedPatternSlot;

    @Inject(
        method = "<init>(Lnet/minecraft/world/inventory/MenuType;ILnet/minecraft/world/entity/player/Inventory;Lappeng/helpers/IPatternTerminalMenuHost;Z)V",
        at = @At("TAIL")
    )
    private void ae2PatternRestocker$onInit(
            MenuType<?> menuType, int id, Inventory ip,
            IPatternTerminalMenuHost host, boolean bindInventory,
            CallbackInfo ci) {
        ((AEBaseMenuAccessor) this).ae2PatternRestocker$registerClientAction(
                new ClientActionKey<>("ae2pr$returnPattern"), this::ae2PatternRestocker$doReturn);
    }

    @Inject(method = "encode", at = @At("HEAD"))
    private void ae2PatternRestocker$fillBlankPattern(CallbackInfo ci) {
        PatternEncodingTermMenu self = (PatternEncodingTermMenu) (Object) this;
        if (self.isClientSide()) return;
        if (!AE2PatternRestockerConfig.AUTO_RESTOCK.get()) return;
        if (!this.blankPatternSlot.getItem().isEmpty()) return;
        MEStorage storage = ((MEStorageMenuAccessor) self).ae2PatternRestocker$getStorage();
        if (storage == null) return;
        AEItemKey key = AEItemKey.of(AEItems.BLANK_PATTERN.asItem());
        long extracted = storage.extract(key, 64, Actionable.MODULATE, IActionSource.ofPlayer(self.getPlayer()));
        if (extracted <= 0) return;
        this.blankPatternSlot.set(AEItems.BLANK_PATTERN.stack((int) extracted));
    }

    @Unique
    private void ae2PatternRestocker$doReturn() {
        PatternEncodingTermMenu self = (PatternEncodingTermMenu) (Object) this;
        ItemStack encoded = this.encodedPatternSlot.getItem();
        if (encoded.isEmpty()) return;

        int count = encoded.getCount();
        this.encodedPatternSlot.set(ItemStack.EMPTY);
        ItemStack blanks = AEItems.BLANK_PATTERN.stack(count);

        // Try blank pattern slot first
        ItemStack inSlot = this.blankPatternSlot.getItem();
        if (inSlot.isEmpty()) {
            this.blankPatternSlot.set(blanks);
            blanks = ItemStack.EMPTY;
        } else if (inSlot.is(AEItems.BLANK_PATTERN.asItem())) {
            int space = inSlot.getMaxStackSize() - inSlot.getCount();
            int toAdd = Math.min(space, blanks.getCount());
            if (toAdd > 0) {
                ItemStack merged = inSlot.copy();
                merged.grow(toAdd);
                this.blankPatternSlot.set(merged);
                blanks.shrink(toAdd);
            }
        }

        if (blanks.isEmpty()) return;

        // Try ME storage
        MEStorage storage = ((MEStorageMenuAccessor) self).ae2PatternRestocker$getStorage();
        if (storage != null) {
            AEItemKey key = AEItemKey.of(AEItems.BLANK_PATTERN.asItem());
            long inserted = storage.insert(key, blanks.getCount(), Actionable.MODULATE,
                    IActionSource.ofPlayer(self.getPlayer()));
            blanks.shrink((int) inserted);
        }

        if (blanks.isEmpty()) return;

        // Give any remainder to the player
        self.getPlayer().getInventory().placeItemBackInInventory(blanks);
    }
}
