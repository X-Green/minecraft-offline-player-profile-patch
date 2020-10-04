package dev.eeasee.offline_profile_accessor.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerLoginNetworkHandler.class)
public abstract class ServerLoginNetworkHandlerMixin {
    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow
    protected abstract GameProfile toOfflineProfile(GameProfile profile);

    @Redirect(method = "acceptPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerLoginNetworkHandler;toOfflineProfile(Lcom/mojang/authlib/GameProfile;)Lcom/mojang/authlib/GameProfile;"))
    private GameProfile tryGetOnlineProfile(ServerLoginNetworkHandler serverLoginNetworkHandler, GameProfile profile) {
        GameProfile gameProfile = server.getUserCache().findByName(profile.getName());
        if (gameProfile == null) {
            return this.toOfflineProfile(profile);
        }
        if (gameProfile.getProperties().containsKey("textures")) {
            gameProfile = SkullBlockEntity.loadProperties(gameProfile);
        }
        return gameProfile;
    }
}
