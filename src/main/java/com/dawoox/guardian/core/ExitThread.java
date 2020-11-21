package com.dawoox.guardian.core;

import com.dawoox.guardian.docker.DockerLink;
import com.dawoox.guardian.redis.RedisLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExitThread extends Thread {

    private static final Logger EXIT_THREAD = LoggerFactory.getLogger("guardian.exit");

    public void run() {
        RedisLink.exit();
        DockerLink.exit();

        EXIT_THREAD.info("Guardian is now shut down.\n");
    }
}
