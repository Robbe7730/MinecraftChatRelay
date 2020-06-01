package be.robbevanherck.chatplugin.commands;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.repositories.ChatServiceRepository;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;

public class SendAllCommand {
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean b) {
        commandDispatcher.register(CommandManager.literal("sendall").then(
                CommandManager.argument("text", string()).executes(ctx -> {
                    ChatServiceRepository.getChatServices().forEach(chatService -> chatService.sendMessage(
                            new Message("DefinitelyNotChatPlugin", getString(ctx, "text"), null)
                    ));
                    return 1;
                })
                ).executes( ctx -> {
                    ChatServiceRepository.getChatServices().forEach(chatService -> chatService.sendMessage(
                            new Message("ChatPlugin", "This is a test message", null)
                    ));
                    return 1;
                }
            )
        );
    }
}
