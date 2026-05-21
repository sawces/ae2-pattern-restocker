package com.vaded.ae2patternrestocker.mixin;

import appeng.menu.AEBaseMenu;
import appeng.menu.guisync.ClientActionKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AEBaseMenu.class)
public interface AEBaseMenuAccessor {
    @Invoker("registerClientAction")
    void ae2PatternRestocker$registerClientAction(ClientActionKey<Void> key, Runnable callback);

    @Invoker("sendClientAction")
    void ae2PatternRestocker$sendClientAction(ClientActionKey<Void> action);
}
