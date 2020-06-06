package be.robbevanherck.chatplugin.services.discord;

import be.robbevanherck.chatplugin.entities.*;
import be.robbevanherck.chatplugin.repositories.PropertiesRepository;
import be.robbevanherck.chatplugin.services.ChatService;
import be.robbevanherck.chatplugin.services.minecraft.callbacks.PlayerJoinCallback;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.minecraft.server.MinecraftServer;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;

public class DiscordChatService extends ChatService {
    protected static final Logger LOGGER = LogManager.getLogger();
    private JDA jda;
    private DiscordPlayer discordBotPlayer;

    @Override
    public void serverStarted(MinecraftServer server) {
        String token = (String) PropertiesRepository.getProperty("discord-token");
        try {
            JDABuilder builder = new JDABuilder(token);
            builder.setActivity(Activity.playing("Minecraft: " + server.getServerMotd()));

            jda = builder.build();

            jda.addEventListener(new DiscordMessageListener(this));
        } catch (LoginException e) {
            LOGGER.error("Could not setup Discord connection", e);
        }
        discordBotPlayer = new DiscordPlayer(jda.getSelfUser());

        PlayerJoinCallback.EVENT.register((joinedPlayer) -> {
            if (DiscordRepository.getChannel() == null && joinedPlayer instanceof MessageablePlayer) {
                MessageablePlayer messageablePlayer = (MessageablePlayer) joinedPlayer;
                messageablePlayer.sendMessage(new ChatMessage(
                        discordBotPlayer,
                        "Hello there, I seem to be disconnected from Discord. To set up a connection to Discord, please go to the right channel and type !setup. Thank you!"
                ));
            }
        });
    }

    @Override
    public void serverStopped(MinecraftServer server) {
        if (DiscordRepository.getChannel() != null) {
            DiscordRepository.setChannel(null);
        }
        jda.shutdown();

        // Force the HTTP/2 clients to close
        OkHttpClient client = jda.getHttpClient();
        client.connectionPool().evictAll();
        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void sendMessage(Message message) {
        if (DiscordRepository.getChannel() == null) {
            LOGGER.warn("Tried to send a message without connected channel.");
            return;
        }
        DiscordRepository.getChannel().sendMessage(message.toString()).queue();
    }

    public DiscordPlayer getDiscordBotPlayer() {
        return discordBotPlayer;
    }
}
