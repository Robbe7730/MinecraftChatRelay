package be.robbevanherck.chatplugin.services.discord;

import be.robbevanherck.chatplugin.entities.ChatMessage;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * JDA listener for Discord messages
 */
public class DiscordMessageListener extends ListenerAdapter {
    DiscordChatService parentService;

    /**
     * JDA listener for Discord messages
     * @param parentService The DiscordChatService that created it and should be called back
     */
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
            ChatMessage message = new ChatMessage(
                    new DiscordPlayer(event.getMessage().getAuthor()),
                    event.getMessage().getContentDisplay()
            );
            parentService.onMessageReceived(message, parentService);
        }
    }
}
