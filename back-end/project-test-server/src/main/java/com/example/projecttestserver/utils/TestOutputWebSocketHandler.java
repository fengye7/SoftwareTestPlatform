package com.example.projecttestserver.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class TestOutputWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = Logger.getLogger(TestOutputWebSocketHandler.class.getName());

    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
        logger.info("WebSocket 连接已建立");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.session = null;
        logger.info("WebSocket 连接已关闭，状态：" + status);
    }

    public void sendMessage(String message) throws IOException {
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
            logger.info("发送消息：" + message);
        } else {
            logger.warning("发送消息失败：WebSocket 会话未打开");
        }
    }

    public void close() throws IOException {
        if (session != null) {
            session.close();
            logger.info("WebSocket 连接已关闭");
        }
    }
}