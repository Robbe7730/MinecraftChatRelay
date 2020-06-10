package be.robbevanherck.chatplugin.services.discord;

import be.robbevanherck.chatplugin.entities.ChatMessage;
import be.robbevanherck.chatplugin.entities.MessageablePlayer;
import be.robbevanherck.chatplugin.entities.Player;
import be.robbevanherck.chatplugin.entities.SystemMessage;
import be.robbevanherck.chatplugin.enums.OnlineStatus;
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
                break;
            case "!online":
                player.setOnlineStatus(OnlineStatus.ONLINE);
                break;
            case "!offline":
                player.setOnlineStatus(OnlineStatus.OFFLINE);
                break;
            case "!verify":
                if (messageSplit.length != 2 || messageSplit[1].equals("")) {
                    PlayerMappingRepository.PlayerMapping playerMapping = PlayerMappingRepository.getMappingFor(player);

                    if (playerMapping != null) {
                        parentService.sendMessage(new SystemMessage(event.getMessage().getAuthor().getAsMention() + " you are linked to " + playerMapping.getHumanReadableName()));
                    } else {
                        playerMapping = PlayerMappingRepository.addMappingForPlayer(player);
                        parentService.sendMessage(new SystemMessage(event.getMessage().getAuthor().getAsMention() + " you were linked to " + playerMapping.getHumanReadableName()));
                    }
                    parentService.sendMessage(new SystemMessage("To link (for example) your Discord account, send \"!verify " + playerMapping.getHumanReadableName() + "\" in the right channel."));
                } else {
                    PlayerMappingRepository.PlayerMapping mapping = PlayerMappingRepository.getMappingByName(messageSplit[1]);
                    if (mapping == null) {
                        parentService.sendMessage(new SystemMessage(event.getMessage().getAuthor().getAsMention() + " Whoops, no such mapping found"));
                    } else {
                        if (mapping.contains(player)) {
                            parentService.sendMessage(new SystemMessage(event.getMessage().getAuthor().getAsMention() + " was already linked to " + mapping.getHumanReadableName()));
                        } else {
                            for (Player otherPlayer : mapping) {
                                if (otherPlayer instanceof MessageablePlayer) {
                                    ((MessageablePlayer) otherPlayer).sendMessage(new ChatMessage(
                                            player,
                                            "Hi! I'm now linked to you."
                                    ));
                                }
                            }
                            mapping.add(player);
                            parentService.sendMessage(new SystemMessage(event.getMessage().getAuthor().getAsMention() + " is now linked to " + mapping.getHumanReadableName()));
                        }
                    }
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
