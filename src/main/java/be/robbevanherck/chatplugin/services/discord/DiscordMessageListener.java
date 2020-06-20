package be.robbevanherck.chatplugin.services.discord;

import be.robbevanherck.chatplugin.entities.ChatMessage;
import be.robbevanherck.chatplugin.entities.SystemMessage;
import be.robbevanherck.chatplugin.enums.OnlineStatus;
import be.robbevanherck.chatplugin.internals.DisplayServicePlayerUtil;
import be.robbevanherck.chatplugin.repositories.PlayerMappingRepository;
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

        String messageText = event.getMessage().getContentDisplay();

        // If we aren't connected to this channel, ignore the message, unless it's "!setup"
        if (!event.getChannel().equals(DiscordRepository.getChannel()) && !messageText.equals("!setup")) {
            return;
        }

        String[] messageSplit = messageText.split(" ");

        DiscordPlayer player = DiscordPlayer.findOrCreate(event.getMessage().getAuthor());
        switch (messageSplit[0]) {
            case "!setup":
                MessageChannel channel = event.getChannel();
                DiscordRepository.setChannel(channel, event.getGuild());
                event.getMessage().delete().reason("This is my calling!").queue();

                // Update the player list
                parentService.getOnlineStatusPlayer().setOnlineStatus(OnlineStatus.ONLINE);
                DisplayServicePlayerUtil.getInstance().addToPlayerList(parentService);
                break;
            case "!online":
                player.setOnlineStatus(OnlineStatus.ONLINE);
                break;
            case "!offline":
                player.setOnlineStatus(OnlineStatus.OFFLINE);
                break;
            case "!verify":
                if (messageSplit.length != 2 || messageSplit[1].equals("")) {
                    PlayerMappingRepository.createMappingForPlayerChecked(player, (message, isError) ->
                            parentService.sendMessage(new SystemMessage(message))
                    );
                } else {
                    PlayerMappingRepository.addPlayerToMappingByName(player, messageSplit[1], (message, isError) ->
                            parentService.sendMessage(new SystemMessage(message))
                    );
                }
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
