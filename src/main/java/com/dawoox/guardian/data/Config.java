package com.dawoox.guardian.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

public class Config {

    private static final Logger LOGGER = LoggerFactory.getLogger("guardian.config-loader");

    private static final Properties PROPERTIES = loadProperties();
    private static final Properties CONFIG = loadConfig();

    public static final String VERSION = PROPERTIES.getProperty("version");
    public static final boolean IS_SNAPSHOT = VERSION.endsWith("SNAPSHOT");
    public static final String GITHUB_URL = PROPERTIES.getProperty("github.url");
    public static final String SUPPORT_SERVER_URL = PROPERTIES.getProperty("support.server.url");

    public static final String MONGODB_URI = CONFIG.getProperty("mongodb.uri");
    public static final String MONGODB_COLLECTION = CONFIG.getProperty("mongodb.collection");

    public static final String REDIS_HOST = CONFIG.getProperty("redis.host");
    public static final boolean REDIS_AUTH_NEEDED = Boolean.parseBoolean(CONFIG.getProperty("redis.auth_needed"));
    public static final String REDIS_PASS = CONFIG.getProperty("redis.pass");
    public static final String REDIS_PUBSUB_CHANNEL = CONFIG.getProperty("redis.pubsub.channel");

    private static Properties loadProperties() {
        final Properties properties = new Properties();
        try (final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("project.properties")) {
            if (is == null) {
                throw new RuntimeException("Configuration file not found.");
            }
            properties.load(is);
        } catch (final IOException err){
            LOGGER.error("An error ocurred while loading configuration file. Exiting.", err);
            throw new RuntimeException(err);
        }
        return properties;
    }

    private static Properties loadConfig() {
        final File file = new File("config.properties");
        if (!configIsPresent()) {
            throw new RuntimeException(String.format("%s file is missing.", file.getName()));
        }

        Properties properties = new Properties();
        try (final BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            properties.load(reader);
        } catch (final IOException e) {
            throw new RuntimeException(String.format("An error occured while loading %s file", file.getName()));
        }
        return properties;
    }

    public static Boolean configIsPresent() {
        final File file = new File("config.properties");
        return file.exists();
    }
}
