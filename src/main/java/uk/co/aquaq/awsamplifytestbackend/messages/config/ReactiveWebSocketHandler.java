package uk.co.aquaq.awsamplifytestbackend.messages.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import uk.co.aquaq.awsamplifytestbackend.messages.dto.MessageQueue;

@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        System.out.println("Inside ReactiveWebSocketHandler");
        return session
                .receive()
                .map(WebSocketMessage -> {
                    MessageQueue.messageQueue.add(WebSocketMessage.getPayloadAsText());
                    System.out.println(WebSocketMessage.getPayloadAsText());
                    return WebSocketMessage.getPayloadAsText();
                })
                .log()
                .then();
    }
}
