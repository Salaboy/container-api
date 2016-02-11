/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.docker.machine;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kie.container.docker.provider.DockerContainer;
import org.kie.container.docker.provider.DockerContainerInstance;
import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerConfiguration;
import org.kie.container.spi.model.ContainerInstance;
import org.kie.container.spi.model.providers.ContainerManager;


/**
 *
 * @author salaboy
 */
public class DockerContainerProvider implements ContainerManager {

    private Map<String, Container> containers = new HashMap<>();
    private Map<String, ContainerInstance> containerInstances = new HashMap<>();

    @Override
    public Container createContainer(String name, ContainerConfiguration conf) {
        Container m = new DockerContainer(name, conf);
        containers.put(name, m);
        return m;
    }

    @Override
    public List<Container> getAllContainers() {
        return new ArrayList<>(containers.values());
    }

    @Override
    public ContainerInstance createContainerInstance(Container c) throws DockerCertificateException, DockerException, InterruptedException {

        ContainerInstance mi = new DockerContainerInstance();
        // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars
        final DockerClient docker;

        docker = DefaultDockerClient.fromEnv().build();

        // Pull an image
       // docker.pull(m.getConfiguration().getProperty("name"));

        // Bind container ports to host ports
        final String[] ports = {"8080"};
        final Map<String, List<PortBinding>> portBindings = new HashMap<String, List<PortBinding>>();
//        for (String port : ports) {
//            List<PortBinding> hostPorts = new ArrayList<PortBinding>();
//            hostPorts.add(PortBinding.of("0.0.0.0", port));
//            portBindings.put(port, hostPorts);
//        }

// Bind container port 443 to an automatically allocated available host port.
        List<PortBinding> randomPort = new ArrayList<PortBinding>();
        PortBinding randomPortBinding = PortBinding.randomPort("0.0.0.0");
        System.out.println("Random Port Binding: "+randomPortBinding.hostPort());
        randomPort.add(randomPortBinding);
        portBindings.put("8080", randomPort);

        final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

// Create container with exposed ports
        final ContainerConfig containerConfig = ContainerConfig.builder()
                .hostConfig(hostConfig)
                .image(c.getConfiguration().getProperty("name")).exposedPorts(ports)
//                .cmd("sh", "-c", "while :; do sleep 1; done")
                .build();

        final ContainerCreation creation;
        
            creation = docker.createContainer(containerConfig);
       
        final String id = creation.id();
        System.out.println(">>> ID: "+id);
// Inspect container
        final ContainerInfo info = docker.inspectContainer(id);
        System.out.println(">>> INFO: "+info);

// Start container
        docker.startContainer(id);

// Exec command inside running container with attached STDOUT and STDERR
//        final String[] command = {"bash", "-c", "ls"};
//        final String execId = docker.execCreate(id, command, DockerClient.ExecCreateParam.attachStdout(), DockerClient.ExecCreateParam.attachStderr());
//        final LogStream output = docker.execStart(execId);
//        final String execOutput = output.readFully();

//// Kill container
//        docker.killContainer(id);
//
//// Remove container
//        docker.removeContainer(id);
        return mi;
    }

    @Override
    public List<ContainerInstance> getAllContainerInstances() {
        return new ArrayList<>(containerInstances.values());
    }

}
