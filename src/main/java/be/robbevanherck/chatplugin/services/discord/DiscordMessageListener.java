package be.robbevanherck.chatplugin.services.discord;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.services.MessageReceivedHandler;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Set;

public class DiscordMessageListener extends ListenerAdapter {
    DiscordChatService parentService;

    public DiscordMessageListener(DiscordChatService parentService) {
        this.parentService = parentService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Just to be safe, ignore bot messages
        if (event.getMessage().getAuthor().isBot()) {
            return;
        }
        if (event.getMessage().getContentDisplay().equals("!setup")) {
            MessageChannel channel = event.getChannel();
            DiscordRepository.setChannel(channel);
            event.getMessage().delete().reason("This is my calling!").queue();
        } else {
            Message message = new Message(
                    event.getMessage().getAuthor().getName(),
                    event.getMessage().getContentDisplay(),
                    parentService
            );
            parentService.onMessageReceived(message);
        }
    }
}
