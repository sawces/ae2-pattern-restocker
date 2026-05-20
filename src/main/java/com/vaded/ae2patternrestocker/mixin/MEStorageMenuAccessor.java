package com.vaded.ae2patternrestocker.mixin;

import appeng.api.storage.MEStorage;
import appeng.menu.me.common.MEStorageMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MEStorageMenu.class)
public interface MEStorageMenuAccessor {
    @Accessor("storage")
    MEStorage ae2PatternRestocker$getStorage();
}
