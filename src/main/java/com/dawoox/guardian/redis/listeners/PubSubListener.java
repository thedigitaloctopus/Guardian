package com.dawoox.guardian.redis.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPubSub;

public class PubSubListener extends JedisPubSub {

    public static final Logger LOGGER = LoggerFactory.getLogger("guardian.redis.pubsub");

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        LOGGER.info("Client is subscribed to channel '" + pattern + "'");
    }

    @Override
    public void onMessage(String channel, String message) {
        LOGGER.info("Channel " + channel + " has send '" + message + "'");
    }
}
