package com.dawoox.guardian.redis;

import com.dawoox.guardian.ExitCode;
import com.dawoox.guardian.data.Config;
import com.dawoox.guardian.redis.listeners.PubSubListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

public class RedisLink {

    private final static Jedis jedis = new Jedis(Config.REDIS_HOST);
    private static final Logger LOGGER = LoggerFactory.getLogger("guardian.redis");

    public static void initialize(){
        LOGGER.info("Initialize connection with redis.. (on " + Config.REDIS_HOST + ")");
        try {
            if (Config.REDIS_AUTH_NEEDED) {
                jedis.auth(Config.REDIS_PASS);
            }
        } catch (JedisDataException e) {
            LOGGER.error("An error occurred while logging into redis: ", e);
            System.exit(ExitCode.FATAL_ERROR.getValue());
        }

        if (!jedis.isConnected() && Config.REDIS_AUTH_NEEDED) {
            LOGGER.error("An unknown error occurred with redis while logging-in");
            System.exit(ExitCode.FATAL_ERROR.getValue());
        } else {
            LOGGER.info("Connection with redis established");
        }

        PubSubListener listener = new PubSubListener();

        try {
            jedis.subscribe(listener, Config.REDIS_PUBSUB_CHANNEL);
            LOGGER.info("Connection with redis established");
        } catch (JedisDataException e) {
            LOGGER.error("An error occurred while subscribing to the channel: ", e);
            System.exit(ExitCode.FATAL_ERROR.getValue());
        }
    }

    public static void exit(){
        jedis.close();
    }
}
