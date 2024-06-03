package com.example.projecttestserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.example.projecttestserver.utils.TestOutputWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TestOutputWebSocketHandler testOutputWebSocketHandler;

    public WebSocketConfig(TestOutputWebSocketHandler testOutputWebSocketHandler) {
        this.testOutputWebSocketHandler = testOutputWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(testOutputWebSocketHandler, "/test-output").setAllowedOrigins("*");
    }
}