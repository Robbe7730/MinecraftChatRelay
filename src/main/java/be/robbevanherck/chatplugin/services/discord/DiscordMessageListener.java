package be.robbevanherck.chatplugin.services.discord;

import be.robbevanherck.chatplugin.entities.ChatMessage;
import be.robbevanherck.chatplugin.enums.OnlineStatus;
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
        DiscordPlayer player = new DiscordPlayer(event.getMessage().getAuthor());
        switch (event.getMessage().getContentDisplay()) {
            case "!setup":
                MessageChannel channel = event.getChannel();
                DiscordRepository.setChannel(channel, event.getGuild());
                event.getMessage().delete().reason("This is my calling!").queue();
                break;
            case "!online":
                player.setOnlineStatus(OnlineStatus.ONLINE);
                break;
            case "!offline":
                player.setOnlineStatus(OnlineStatus.OFFLINE);
                break;
            default:
                ChatMessage message = new ChatMessage(
                        player,
                        event.getMessage().getContentDisplay()
                );
                parentService.onMessageReceived(message, parentService);
                break;
        }
    }
}
