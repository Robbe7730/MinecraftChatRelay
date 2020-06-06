package be.robbevanherck.chatplugin.services.discord;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.repositories.PropertiesRepository;
import be.robbevanherck.chatplugin.services.ChatService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;

public class DiscordChatService extends ChatService {
    protected static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void serverStarted(MinecraftServer server) {
        String token = (String) PropertiesRepository.getProperty("discord-token");
        System.err.println(token);
        try {
            JDABuilder builder = new JDABuilder(token);
            builder.setActivity(Activity.playing("Minecraft: " + server.getServerMotd()));

            JDA jda = builder.build();

            jda.addEventListener(new DiscordMessageListener(this));
        } catch (LoginException e) {
            LOGGER.error("Could not setup Discord connection", e);
        }
    }

    @Override
    public void serverStopped(MinecraftServer server) {
        if (DiscordRepository.getChannel() != null) {
            JDA jda = DiscordRepository.getChannel().getJDA();

            DiscordRepository.setChannel(null);

            jda.shutdown();
        }
    }

    @Override
    public void sendMessage(Message message) {
        if (DiscordRepository.getChannel() == null) {
            LOGGER.warn("Tried to send a message without connected channel.");
            return;
        }
        DiscordRepository.getChannel().sendMessage(message.toString()).queue();
    }
}
