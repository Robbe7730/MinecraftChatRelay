package be.robbevanherck.chatplugin.repositories;

import be.robbevanherck.chatplugin.util.ConfigReader;

import java.util.Properties;

/**
 * Repository for accessing the mods properties
 */
public class PropertiesRepository {
    private static Properties properties;

    /**
     * Private constructor to hide the implicit public one
     */
    private PropertiesRepository() {}

    /**
     * Get all chat services. Initializes chatServices when it is null
     * @return All ChatServices
     */
    public static Properties getProperties() {
        if (properties == null) {
            properties = ConfigReader.readProperties();
        }
        return properties;
    }

    /**
     * Get a specific property
     * @param key The key to look up
     * @return The value associated with that key
     */
    public static Object getProperty(String key) {
        return getProperties().get(key);
    }
}
