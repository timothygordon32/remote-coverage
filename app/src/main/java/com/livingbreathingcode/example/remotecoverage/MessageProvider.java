package com.livingbreathingcode.example.remotecoverage;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is easy to test as a unit.
 */
public class MessageProvider {
    public Map<String, Object> getHelloMessage() {
        return getMessage("Gotta love Spring Boot");
    }

    private Map<String, Object> getMessage(String value) {
        Map<String, Object> message = new HashMap<String, Object>();
        message.put("message", value);
        return message;
    }

    public Map<String, Object> getGoodbyeMessage() {
        return getMessage("Bye, bye!");
    }
}
