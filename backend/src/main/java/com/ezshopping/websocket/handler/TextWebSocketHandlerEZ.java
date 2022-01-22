package com.ezshopping.websocket.handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Primary
@Slf4j
public class TextWebSocketHandlerEZ extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("New session established");
        sessions.add(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Session closed");
        sessions.remove(session);
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    /**
     *
     * @return List<WebSocketSession>
     */
    public List<WebSocketSession> getSessions() {
        return this.sessions;
    }

    /**
     *
     * @param attribute
     * @param value
     * @return WebSocketSession
     */
    public WebSocketSession getSessionByAttributeValue(String attribute, String value) {
        return this.sessions.stream()
                .filter(session -> session.getAttributes().get(attribute).equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("session not found"));
    }
}