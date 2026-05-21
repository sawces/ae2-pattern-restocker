package com.vaded.ae2patternrestocker.mixin;

import appeng.api.config.ActionItems;
import appeng.client.gui.me.items.PatternEncodingTermScreen;
import appeng.client.gui.widgets.ActionButton;
import appeng.menu.SlotSemantics;
import appeng.menu.guisync.ClientActionKey;
import appeng.menu.me.items.PatternEncodingTermMenu;
import com.vaded.ae2patternrestocker.AE2PatternRestockerConfig;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PatternEncodingTermScreen.class)
public abstract class PatternEncodingTermScreenMixin {
    @Unique
    private ActionButton ae2PatternRestocker$returnButton = null;

    @Inject(method = "updateBeforeRender", at = @At("HEAD"))
    private void ae2PatternRestocker$addReturnButton(CallbackInfo ci) {
        @SuppressWarnings("unchecked")
        PatternEncodingTermScreen<PatternEncodingTermMenu> self =
                (PatternEncodingTermScreen<PatternEncodingTermMenu>) (Object) this;

        PatternEncodingTermMenu menu = (PatternEncodingTermMenu)
                ((AbstractContainerScreen<?>) (Object) this).getMenu();

        if (!AE2PatternRestockerConfig.RETURN_BUTTON.get()) return;

        List<Slot> slots = menu.getSlots(SlotSemantics.ENCODED_PATTERN);
        if (slots.isEmpty()) return;

        Slot encodedSlot = slots.get(0);

        if (ae2PatternRestocker$returnButton == null) {
            ae2PatternRestocker$returnButton = new ActionButton(ActionItems.S_CLOSE,
                    () -> ((AEBaseMenuAccessor) menu).ae2PatternRestocker$sendClientAction(new ClientActionKey<>("ae2pr$returnPattern")));
            ae2PatternRestocker$returnButton.setHalfSize(true);
            ae2PatternRestocker$returnButton.setDisableBackground(true);
            ae2PatternRestocker$returnButton.setMessage(Component.literal("Restocker\nClear & Restock Pattern"));
        }

        ae2PatternRestocker$returnButton.setX(self.getGuiLeft() + encodedSlot.x + 14);
        ae2PatternRestocker$returnButton.setY(self.getGuiTop() + encodedSlot.y - 6);

        ae2PatternRestocker$returnButton.setVisibility(!encodedSlot.getItem().isEmpty());

        List<Renderable> renderables = ((ScreenAccessor) (Object) this).ae2PatternRestocker$getRenderables();
        if (!renderables.contains(ae2PatternRestocker$returnButton)) {
            ((ScreenAccessor) (Object) this).ae2PatternRestocker$addRenderableWidget(ae2PatternRestocker$returnButton);
        }
    }
}
