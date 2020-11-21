package com.dawoox.guardian.docker;

import com.dawoox.guardian.ExitCode;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DockerLink {

    private static DockerClient dockerClient = DockerClientBuilder.getInstance().build();
    private static final Logger LOGGER = LoggerFactory.getLogger("guardian.docker");

    public static void exit() {
        try {
            dockerClient.close();
        } catch (IOException e) {
            LOGGER.error("An error occurred while closing docker connexion: ", e);
            System.exit(ExitCode.FATAL_ERROR.getValue());
        }
    }
}
