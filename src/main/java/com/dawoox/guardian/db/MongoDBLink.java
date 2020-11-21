package com.dawoox.guardian.db;

import com.dawoox.guardian.data.Config;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDBLink {

    private static final Logger LOGGER = LoggerFactory.getLogger("guardian.mongodb");

    public static void initialize(){
        LOGGER.info("Initializing MongoDB");
        MongoClient client = new MongoClient( new MongoClientURI(Config.MONGODB_URI));
        MongoDatabase database = client.getDatabase("guardian");

        client.getDatabaseNames().forEach(LOGGER::debug);
    }
}
