package com.dawoox.guardian.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class Test extends TimerTask {

    public static final Logger LOGGER = LoggerFactory.getLogger("guardian.tasks.test");

    @Override
    public void run() {
        LOGGER.info("Hello World!");
    }
}
