package com.jamieswhiteshirt.reachentityattributes.mixin.client;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerInteractionManager.class)
abstract class ClientPlayerInteractionManagerMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow private GameMode gameMode;

    @Redirect(
            method = "getReachDistance()F",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getReachDistance(Z)F"))
    private float getActualReachDistance(boolean creative) {
        float defaultReachDistance = PlayerEntity.getReachDistance(this.gameMode.isCreative());
        if (this.client.player != null)
            return (float) ReachEntityAttributes.getReachDistance(this.client.player, defaultReachDistance);
        return defaultReachDistance;
    }
}
