package be.robbevanherck.chatplugin.services.minecraft;

import be.robbevanherck.chatplugin.repositories.PlayerMappingRepository;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import static com.mojang.brigadier.arguments.StringArgumentType.string;

/**
 * Command to link a Minecraft user to a mapping
 */
public class MinecraftVerifyCommand {
    /**
     * Private constructor to hide the implicit public one
     */
    private MinecraftVerifyCommand() {}
    /**
     * Register the command
     * @param dispatcher The Dispatcher required
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("verify")
            .then(CommandManager.argument("name", string()).executes(
                    ctx -> {
                        MinecraftPlayer minecraftPlayer = MinecraftPlayer.findOrCreate(ctx.getSource().getPlayer());
                        String name = StringArgumentType.getString(ctx, "name");

                        if (PlayerMappingRepository.addPlayerToMappingByName(minecraftPlayer, name, (message, isError) ->
                                ctx.getSource().sendFeedback(new LiteralText(message), isError)
                        )) {
                            return Command.SINGLE_SUCCESS;
                        }
                        return 0;
                    }
            ))
            .executes(ctx -> {
                MinecraftPlayer minecraftPlayer = MinecraftPlayer.findOrCreate(ctx.getSource().getPlayer());

                PlayerMappingRepository.createMappingForPlayerChecked(minecraftPlayer, (message, isError) ->
                        ctx.getSource().sendFeedback(new LiteralText(message), isError)
                );

                return Command.SINGLE_SUCCESS;
            })
        );
    }
}
