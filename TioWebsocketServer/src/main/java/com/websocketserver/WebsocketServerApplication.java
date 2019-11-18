package com.websocketserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tio.websocket.starter.EnableTioWebSocketServer;

@SpringBootApplication
@EnableTioWebSocketServer
public class WebsocketServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketServerApplication.class, args);
    }

}
