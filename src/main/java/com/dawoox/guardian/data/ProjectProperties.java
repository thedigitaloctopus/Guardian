package com.dawoox.guardian.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProjectProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger("guardian.configLoader");
    private static final Properties PROPERTIES = loadProperties();

    public static final String VERSION = PROPERTIES.getProperty("version");
    public static final boolean IS_SNAPSHOT = VERSION.endsWith("SNAPSHOT");
    public static final String GITHUB_URL = PROPERTIES.getProperty("github.url");
    public static final String SUPPORT_SERVER_URL = PROPERTIES.getProperty("support.server.url");


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
}
