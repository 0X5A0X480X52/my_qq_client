package cn.amatrix.utils.configManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "src/main/resources/config/application.properties";
    private static final String encode = "UTF-8";

    static {
        try (InputStream input = Files.newInputStream(Paths.get(CONFIG_FILE))) {
            properties.load(input);
        } catch (IOException ex) {
            System.out.println("Sorry, unable to find application.properties");
            generateDefaultConfigFile();
            ex.printStackTrace();
        }
    }

    public static void generateDefaultConfigFile() {
        properties.setProperty("encode", "UTF-8");
        properties.setProperty("websocket.uri", "ws://47.97.117.157:8080/demo_webapp/chat");
        properties.setProperty("http.base_url", "http://47.97.117.157:8080/demo_webapp");
        properties.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("db.url", "jdbc:mysql://47.97.117.157:8080/onlineChatApp?serverTimezone=UTC");
        properties.setProperty("db.user", "root");
        properties.setProperty("db.password", "ZHRhenry20050305");

        try (OutputStream output = Files.newOutputStream(Paths.get(CONFIG_FILE))) {
            output.write("# Default Configuration\n".getBytes(encode));
            String currentTime = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").format(new Date());
            output.write(("# " + currentTime + " \n\n").getBytes(encode));

            output.write("# Encoding Configuration\n".getBytes(encode));
            output.write(("encode=" + properties.getProperty("encode") + "\n\n").getBytes(encode));

            output.write("# WebSocket Server Configuration\n".getBytes(encode));
            output.write(("websocket.uri=" + properties.getProperty("websocket.uri").replace(":", "\\:") + "\n\n").getBytes(encode));

            output.write("# HTTP Server Configuration\n".getBytes(encode));
            output.write(("http.base_url=" + properties.getProperty("http.base_url").replace(":", "\\:") + "\n\n").getBytes(encode));

            output.write("# Database Configuration\n".getBytes(encode));
            output.write(("db.driver=" + properties.getProperty("db.driver") + "\n").getBytes(encode));
            output.write(("db.url=" + properties.getProperty("db.url").replace(":", "\\:").replace("=", "\\=") + "\n").getBytes(encode));
            output.write(("db.user=" + properties.getProperty("db.user") + "\n").getBytes(encode));
            output.write(("db.password=" + properties.getProperty("db.password") + "\n").getBytes(encode));

            System.out.println("Default configuration file generated.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public static void storeProperty() throws IOException {
        try (OutputStream output = Files.newOutputStream(Paths.get(CONFIG_FILE))) {
            properties.store(output, "Updated Configuration");
        }
    }

    public static void storePropertyWithComments() throws IOException {
        try (OutputStream output = Files.newOutputStream(Paths.get(CONFIG_FILE))) {
            output.write("# Updated Configuration\n".getBytes(encode));
            String currentTime = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").format(new Date());
            output.write(("# " + currentTime + " \n\n").getBytes(encode));

            output.write("# Encoding Configuration\n".getBytes(encode));
            output.write(("encode=" + properties.getProperty("encode") + "\n\n").getBytes(encode));

            output.write("# WebSocket Server Configuration\n".getBytes(encode));
            output.write(("websocket.uri=" + properties.getProperty("websocket.uri").replace(":", "\\:") + "\n\n").getBytes(encode));

            output.write("# HTTP Server Configuration\n".getBytes(encode));
            output.write(("http.base_url=" + properties.getProperty("http.base_url").replace(":", "\\:") + "\n\n").getBytes(encode));

            output.write("# Database Configuration\n".getBytes(encode));
            output.write(("db.driver=" + properties.getProperty("db.driver") + "\n").getBytes(encode));
            output.write(("db.url=" + properties.getProperty("db.url").replace(":", "\\:").replace("=", "\\=") + "\n").getBytes(encode));
            output.write(("db.user=" + properties.getProperty("db.user") + "\n").getBytes(encode));
            output.write(("db.password=" + properties.getProperty("db.password") + "\n").getBytes(encode));
        }
    }

    public static void main(String[] args) {
        System.out.println(ConfigManager.getProperty("encode"));
        System.out.println(ConfigManager.getProperty("websocket.uri"));
        System.out.println(ConfigManager.getProperty("http.base_url"));
        System.out.println(ConfigManager.getProperty("db.url"));
        System.out.println(ConfigManager.getProperty("db.user"));
        System.out.println(ConfigManager.getProperty("db.password"));

        properties.setProperty("encode", "UTF-8");

        properties.setProperty("websocket.uri", "ws://47.97.117.157:8080/demo_webapp/chat");

        properties.setProperty("http.base_url", "http://47.97.117.157:8080/demo_webapp");

        properties.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("db.url", "jdbc:mysql://47.97.117.157:8080/onlineChatApp?serverTimezone=UTC");
        properties.setProperty("db.user", "root");
        properties.setProperty("db.password", "ZHRhenry20050305");

        try {
            ConfigManager.storePropertyWithComments();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(ConfigManager.getProperty("encode"));
        System.out.println(ConfigManager.getProperty("websocket.uri"));
        System.out.println(ConfigManager.getProperty("http.base_url"));
        System.out.println(ConfigManager.getProperty("db.url"));
        System.out.println(ConfigManager.getProperty("db.user"));
        System.out.println(ConfigManager.getProperty("db.password"));
    }
}
