package com.weiller.demo.hello.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * webSocket案例
 */
@Component
public class EchoHandler implements WebSocketHandler{

    @Override
    public Mono<Void> handle(final WebSocketSession webSocketSession) {
        return webSocketSession.send(
                webSocketSession.receive().map(msg -> webSocketSession.textMessage("Echo -> "+msg.getPayloadAsText()))
        );
    }
}
