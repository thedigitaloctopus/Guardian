package com.dawoox.guardian;

import com.dawoox.guardian.core.ExitThread;
import com.dawoox.guardian.data.Config;
import com.dawoox.guardian.redis.listeners.PubSubListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Guardian {

    public static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger("guardian.main");

    public static void main(String[] args){
        Runtime.getRuntime().addShutdownHook(new ExitThread());
        DEFAULT_LOGGER.info(String.format("\n   ___                     _ _             \n" +
                "  / _ \\_   _  __ _ _ __ __| (_) __ _ _ __  \n" +
                " / /_\\/ | | |/ _` | '__/ _` | |/ _` | '_ \\ \n" +
                "/ /_\\\\| |_| | (_| | | | (_| | | (_| | | | |\n" +
                "\\____/ \\__,_|\\__,_|_|  \\__,_|_|\\__,_|_| |_|\n" +
                "============================================\n" +
                "Starting Guardian Server version {}\n" +
                "Main Dev: Dawoox\n" +
                "Help Discord: {}\n" +
                "Github repository: {}\n" +
                "============================================"),
                Config.VERSION, Config.SUPPORT_SERVER_URL, Config.GITHUB_URL);

        if (Config.IS_SNAPSHOT) {
            DEFAULT_LOGGER.warn("Please note : this version is a snapshot and can be unstable.");
        }

        if (!Config.configIsPresent()) {
            DEFAULT_LOGGER.error("No config file found, start aborted.");
            System.exit(ExitCode.FATAL_ERROR.getValue());
        }

        Jedis jedis = new Jedis(Config.REDIS_HOST);
        jedis.auth(Config.REDIS_PASS);
        
        PubSubListener listener = new PubSubListener();
        jedis.subscribe(listener, Config.REDIS_PUBSUB_CHANNEL);
    }

}
