package com.livingbreathingcode.example.remotecoverage;

import com.sun.jersey.api.client.Client;

public class Shutdown {
    public static void main(String[] args) {
        Client.create().resource("http://localhost:8080/shutdown").post();
    }
}
