package dev.eeasee.offline_profile_accessor;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class ExampleCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("offline-profile-accessor")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(ExampleCommand::test));
    }

    private static int test(CommandContext<ServerCommandSource> commandContext) {
        MinecraftServer server = commandContext.getSource().getMinecraftServer();
        GameProfile gameProfile = server.getUserCache().findByName("eeasee");
        gameProfile = SkullBlockEntity.loadProperties(gameProfile);
        System.out.println(gameProfile);
        return 1;
    }

}
