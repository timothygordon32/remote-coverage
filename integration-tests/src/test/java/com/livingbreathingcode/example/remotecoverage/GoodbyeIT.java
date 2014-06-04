package com.livingbreathingcode.example.remotecoverage;

import com.sun.jersey.api.client.Client;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class GoodbyeIT {
    @Test
    public void helloEndpointShouldReturnStringContainingSpringBoot() throws IOException {
        Map response = Client.create().resource("http://localhost:8080/goodbye").get(Map.class);
        assertThat(response).hasSize(1);
        assertThat(((String)response.get("message"))).contains("Bye");
    }
}
