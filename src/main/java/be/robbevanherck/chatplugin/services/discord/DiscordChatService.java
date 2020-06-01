package be.robbevanherck.chatplugin.services.discord;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.repositories.PropertiesRepository;
import be.robbevanherck.chatplugin.services.ChatService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.minecraft.server.MinecraftServer;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class DiscordChatService extends ChatService {
    @Override
    public void serverStarted(MinecraftServer server) {
        String token = (String) PropertiesRepository.getProperty("discord-token");
        System.err.println(token);
        try {
            JDABuilder builder = new JDABuilder(token);
            builder.setActivity(Activity.playing("Minecraft: " + server.getServerMotd()));

            JDA jda = builder.build();

            jda.addEventListener(new DiscordMessageListener(this));
        } catch (LoginException ignored) {
            //TODO
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
            return;
        }
        DiscordRepository.getChannel().sendMessage(message.toString()).queue();
    }
}
