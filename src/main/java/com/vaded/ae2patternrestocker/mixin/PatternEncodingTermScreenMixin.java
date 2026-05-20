package com.vaded.ae2patternrestocker.mixin;

import appeng.api.config.ActionItems;
import appeng.client.gui.me.items.PatternEncodingTermScreen;
import appeng.client.gui.widgets.ActionButton;
import appeng.menu.SlotSemantics;
import appeng.menu.me.items.PatternEncodingTermMenu;
import com.vaded.ae2patternrestocker.IPatternRestockerMenu;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PatternEncodingTermScreen.class)
public abstract class PatternEncodingTermScreenMixin {
    @Shadow
    protected abstract <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget);

    @Inject(method = "init", at = @At("TAIL"))
    private void ae2PatternRestocker$addReturnButton(CallbackInfo ci) {
        @SuppressWarnings("unchecked")
        PatternEncodingTermScreen<PatternEncodingTermMenu> self =
                (PatternEncodingTermScreen<PatternEncodingTermMenu>) (Object) this;

        PatternEncodingTermMenu menu = (PatternEncodingTermMenu)
                ((AbstractContainerScreen<?>) (Object) this).getMenu();

        List<Slot> slots = menu.getSlots(SlotSemantics.ENCODED_PATTERN);
        if (slots.isEmpty()) return;

        Slot encodedSlot = slots.get(0);

        ActionButton button = new ActionButton(ActionItems.CLOSE,
                () -> ((IPatternRestockerMenu) menu).ae2PatternRestocker$initiateReturn());
        button.setX(self.getGuiLeft() + encodedSlot.x - 18);
        button.setY(self.getGuiTop() + encodedSlot.y);

        addRenderableWidget(button);
    }
}
