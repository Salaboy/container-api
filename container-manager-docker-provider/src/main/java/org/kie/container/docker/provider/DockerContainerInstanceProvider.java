/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kie.container.docker.provider;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerCertificateException;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.kie.container.spi.model.Container;
import org.kie.container.spi.model.ContainerInstance;
import org.kie.container.spi.model.providers.ContainerProviderConfiguration;
import org.kie.container.spi.model.providers.base.BaseContainerInstanceProvider;

/**
 *
 * @author salaboy
 */
public class DockerContainerInstanceProvider extends BaseContainerInstanceProvider {

    private Map<String, ContainerInstance> containerInstances = new HashMap<>();
    private DockerClient docker;

    @Inject
    private Instance<DockerContainerInstance> instance;

    public DockerContainerInstanceProvider() {
        super("Docker Client");
        System.out.println(">>> DockerContainerInstanceProvider Created... " + this.hashCode());
        
    }
    
    

    @Override
    public void configure(ContainerProviderConfiguration config) {
        //@TODO: use the conf to configure the Docker Deamon
        this.config = config;
        try {
            docker = DefaultDockerClient.fromEnv().build();
        } catch (DockerCertificateException ex) {
            Logger.getLogger(DockerContainerProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public ContainerInstance createInstance(Container c) throws DockerCertificateException, DockerException, InterruptedException {

        ContainerInstance ci = instance.get();
        ci.getInfo().setName(c.getConfiguration().getProperties().get("name"));
        // Create a client based on DOCKER_HOST and DOCKER_CERT_PATH env vars

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
        System.out.println("Random Port Binding: " + randomPortBinding.hostPort());
        randomPort.add(randomPortBinding);
        portBindings.put("8080", randomPort);

        final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();

// Create container with exposed ports
        final ContainerConfig containerConfig = ContainerConfig.builder()
                .hostConfig(hostConfig)
                .image(c.getConfiguration().getProperties().get("name")).exposedPorts(ports)
                //                .cmd("sh", "-c", "while :; do sleep 1; done")
                .build();

        final ContainerCreation creation = docker.createContainer(containerConfig);

        final String id = creation.id();
        System.out.println(">>> ID: " + id);
// Inspect container
        final ContainerInfo info = docker.inspectContainer(id);
        System.out.println(">>> INFO: " + info);
        String shortId = id.substring(0, 12);
        ci.getInfo().setId(shortId); // get Short Id
        containerInstances.put(shortId, ci);

// Start container
//        docker.startContainer(id);
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
        return ci;
    }

    @Override
    public List<ContainerInstance> getAllInstances() {
        return new ArrayList<>(containerInstances.values());
    }

    @Override
    public void removeInstance(String containerId) {
        try {
            docker.removeContainer(containerId);
        } catch (DockerException ex) {
            Logger.getLogger(DockerContainerProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DockerContainerProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DockerClient getDocker() {
        return docker;
    }

    @Override
    public ContainerInstance getInstanceById(String id) {
        return containerInstances.get(id);
    }

}
