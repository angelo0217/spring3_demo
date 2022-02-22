package com.example.spring3_demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class WebSocketService {
    public void onOpen(WebSocketSession webSocketSession) {

    }
    public void onMessage(WebSocketSession webSocketSession, TextMessage message){
        System.out.println(message.getPayload());
    }
    public void onClose(WebSocketSession webSocketSession) {

    }
}
