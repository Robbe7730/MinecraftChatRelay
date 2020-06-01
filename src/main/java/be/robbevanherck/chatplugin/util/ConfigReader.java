package be.robbevanherck.chatplugin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    /**
     * Read the properties from ./config/chatplugin.conf, if this doesn't exist, create it with the default config
     * @return The properties
     */
    public static Properties readProperties() {
        File file = new File("./config/chatplugin.conf");
        if (!file.exists()) {
            try {
                writeDefaultConfig(file);
                return getDefaultProps();
            } catch (IOException e) {
                throw new RuntimeException("Could not create config file", e);
            }
        }
        Properties props = new Properties();
        try (FileInputStream fin = new FileInputStream(file)) {
            props.load(fin);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config file", e);
        }
        return props;
    }

    /**
     * Write the default config to a file
     * @param file The file to write the config to
     * @throws IOException Creating the file may fail
     */
    private static void writeDefaultConfig(File file) throws IOException {
        file.getParentFile().mkdirs();
        file.createNewFile();

        Properties defaultProps = getDefaultProps();

        try (FileOutputStream fout = new FileOutputStream(file)) {
            defaultProps.store(fout, "");
        }
    }

    /**
     * Get the default properties
     * @return The default properties
     */
    private static Properties getDefaultProps() {
        Properties defaultProps = new Properties();

        defaultProps.setProperty("discord-token", "YOUR_TOKEN_HERE");

        return defaultProps;
    }
}

