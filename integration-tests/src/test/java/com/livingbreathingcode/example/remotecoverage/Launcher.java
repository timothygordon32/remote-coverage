package com.livingbreathingcode.example.remotecoverage;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import static org.junit.Assert.assertNotNull;

public class Launcher {
    private static final int PING_ATTEMPTS = 10;
    private static final long ONE_SECOND = 1000;

    private String fatJarPath;
    private String agentJarPath;
    private String pingUrl;

    public static void main(String[] args) throws IOException, InterruptedException {
        String fatJarPath = System.getProperty("jar.path");
        assertNotNull("Path to fat jar for service not specified", fatJarPath != null);
        String agentJarPath = System.getProperty("agent.jar.path");
        assertNotNull("Path to agent jar not specified", fatJarPath != null);
        String pingUrl = System.getProperty("ping.url");
        assertNotNull("Ping url for service availability not specified", pingUrl);

        new Launcher(fatJarPath, agentJarPath, pingUrl).run();
    }

    public Launcher(String fatJarPath, String agentJarPath, String pingUrl) {
        this.fatJarPath = fatJarPath;
        this.agentJarPath = agentJarPath;
        this.pingUrl = pingUrl;
    }

    private void run() throws IOException, InterruptedException {
        String[] launchArgs = new String[]{"java", "-javaagent:" + agentJarPath + "=output=tcpserver", "-jar", fatJarPath};
        System.out.println("Launching: " + StringUtils.join(launchArgs, " "));

        Process process = Runtime.getRuntime().exec(launchArgs);
        pipe(process.getInputStream(), System.out);
        pipe(process.getInputStream(), System.out);

        waitForOk("http://localhost:8080/info");
    }

    private Thread pipe(final InputStream inputStream, final PrintStream printStream) throws IOException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                BufferedReader br;
                try {
                    br = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = br.readLine()) != null) {
                        printStream.println(line);
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
        Thread pipeThread = new Thread(runnable);
        pipeThread.setDaemon(true);
        pipeThread.start();
        return pipeThread;
    }

    private static void waitForOk(String pingUrl) {
        boolean ok = false;
        for (int connectAttempts = 0; connectAttempts < PING_ATTEMPTS; connectAttempts++) {
            waitMoment();

            try {
                if (Client.create().resource(pingUrl).get(ClientResponse.class).getStatus() == Response.Status.OK.getStatusCode()) {
                    ok = true;
                    break;
                }
            } catch (ClientHandlerException e) {
                // Can happen
            }
        }
        if (!ok) {
            throw new IllegalStateException("No service returned OK from " + pingUrl);
        }
    }

    private static void waitMoment() {
        try {
            Thread.sleep(ONE_SECOND);
        } catch (InterruptedException e) {
            // Patience
        }
    }
}
