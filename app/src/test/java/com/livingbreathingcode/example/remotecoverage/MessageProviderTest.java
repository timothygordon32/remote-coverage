package com.livingbreathingcode.example.remotecoverage;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;

import java.util.Map;

public class MessageProviderTest {
    @Test
    public void shouldContainSpringBoot() {
        Map<String,Object> message = new MessageProvider().getHelloMessage();
        assertThat(message).hasSize(1);
        assertThat(((String)message.get("message"))).contains("Spring Boot");
    }
}
