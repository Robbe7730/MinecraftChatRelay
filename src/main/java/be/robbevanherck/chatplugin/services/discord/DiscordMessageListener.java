package be.robbevanherck.chatplugin.services.discord;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordMessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Just to be safe, ignore bot messages
        if (event.getMessage().getAuthor().isBot()) {
            return;
        }
        if (event.getMessage().getContentDisplay().equals("!setup")) {
            MessageChannel channel = event.getChannel();
            DiscordRepository.setChannel(channel);
        }
    }
}
