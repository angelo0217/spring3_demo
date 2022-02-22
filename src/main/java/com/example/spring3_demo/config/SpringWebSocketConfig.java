package com.example.spring3_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class SpringWebSocketConfig implements WebSocketConfigurer {

    private static final String WS_USER_END_POINT = "/chat/*/*";

    @Autowired
    private SpringWebSocketHandler springWebSocketHandler;


    @Bean
    public ServerEndpointExporter serverEndpointExporter(ApplicationContext context) {
        return new ServerEndpointExporter();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(springWebSocketHandler, WS_USER_END_POINT).addInterceptors(new SpringWebSocketHandlerInterceptor()).setAllowedOrigins("*");
    }

}