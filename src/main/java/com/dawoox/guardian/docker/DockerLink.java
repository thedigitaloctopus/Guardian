package com.dawoox.guardian.docker;

import com.dawoox.guardian.ExitCode;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DockerClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    public static void test() {
        List<Container> containers = dockerClient.listContainersCmd().exec();
        containers.forEach(container -> LOGGER.debug(Arrays.toString(container.getNames()) + " --- " + container.getStatus()));
    }
}
