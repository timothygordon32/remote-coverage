package com.livingbreathingcode.example.remotecoverage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * This controller can be tested as a unit, but that will not prove very much as it is an integration point. Some
 * integration test coverage would be useful to know it has been exercised in at least one end-to-end test.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@RestController
public class HelloController {

    public static void main(String[] args) {
        SpringApplication.run(HelloController.class, args);
    }

    @RequestMapping("/hello")
    public Map<String, Object> hello() {
        return messageProvider().getHelloMessage();
    }

    @RequestMapping("/goodbye")
    public Map<String, Object> goodbye() {
        return messageProvider().getGoodbyeMessage();
    }

    @Bean
    public MessageProvider messageProvider() {
        return new MessageProvider();
    }
}