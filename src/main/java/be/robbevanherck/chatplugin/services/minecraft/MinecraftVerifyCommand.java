package be.robbevanherck.chatplugin.services.minecraft;

import be.robbevanherck.chatplugin.entities.ChatMessage;
import be.robbevanherck.chatplugin.entities.MessageablePlayer;
import be.robbevanherck.chatplugin.entities.Player;
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
                        PlayerMappingRepository.PlayerMapping mapping = PlayerMappingRepository.getMappingByName(name);
                        if (mapping == null) {
                            ctx.getSource().sendError(new LiteralText("No such mapping: " + name));
                            return 0;
                        } else {
                            if (mapping.contains(minecraftPlayer)) {
                                ctx.getSource().sendFeedback(new LiteralText("You were already in " + name), false);
                                return 0;
                            } else {
                                for (Player player : mapping) {
                                    if (player instanceof MessageablePlayer) {
                                        ((MessageablePlayer) player).sendMessage(new ChatMessage(
                                                minecraftPlayer,
                                                "Hi! I'm now linked to you."
                                        ));
                                    }
                                }
                                mapping.add(minecraftPlayer);
                                ctx.getSource().sendFeedback(new LiteralText("You were added to " + name), false);
                                return Command.SINGLE_SUCCESS;
                            }
                        }
                    }
            ))
            .executes(ctx -> {
                MinecraftPlayer minecraftPlayer = MinecraftPlayer.findOrCreate(ctx.getSource().getPlayer());

                PlayerMappingRepository.PlayerMapping playerMapping = PlayerMappingRepository.getMappingFor(minecraftPlayer);

                if (playerMapping != null) {
                    ctx.getSource().sendFeedback(new LiteralText("You are linked to " + playerMapping.getHumanReadableName()), false);
                } else {
                    playerMapping = PlayerMappingRepository.addMappingForPlayer(minecraftPlayer);
                    ctx.getSource().sendFeedback(new LiteralText("You were linked to " + playerMapping.getHumanReadableName()), false);
                }
                ctx.getSource().sendFeedback(new LiteralText("To link (for example) your Discord account, send \"!verify " + playerMapping.getHumanReadableName() + "\" in the right channel."), false);

                return Command.SINGLE_SUCCESS;
            })
        );
    }
}
