package uk.co.aquaq.awsamplifytestbackend.messages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.co.aquaq.awsamplifytestbackend.messages.dto.Message;
import uk.co.aquaq.awsamplifytestbackend.messages.dto.MessageQueue;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagesServiceImpl implements MessagesService {

    private final WebClient.Builder webClientBuilder;

    private final ReactorNettyWebSocketClient webSocketClient;

    private final WebSocketHandler webSocketHandler;

    @Value("${aws.apigateway.dynamodb.fix-messages-test.url}")
    private String FIX_MESSAGES_TEST_URL;

    @Value("${aws.apigateway.dynamodb.new-fix-messages-test.url}")
    private String NEW_FIX_MESSAGES_TEST_URL;

    @Override
    public Mono<List<Message>> getMessages() {
        return webClientBuilder
                .build()
                .get()
                .uri(FIX_MESSAGES_TEST_URL)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public Flux<ServerSentEvent<String>> getNewMessages() {
        /*webSocketClient.execute(URI.create(NEW_FIX_MESSAGES_TEST_URL), session -> session
                .receive()
                .map(WebSocketMessage -> {
                    System.out.println(WebSocketMessage.getPayloadAsText());
                    return WebSocketMessage.getPayloadAsText();
                }).
                log()
                .then());*/

        /*webSocketClient.execute(URI.create(NEW_FIX_MESSAGES_TEST_URL), session -> session.send(
                Mono.just(session.textMessage("test message")))
                .thenMany(session.receive()
                        .map(WebSocketMessage -> {
                            System.out.println(WebSocketMessage.getPayloadAsText());
                            return WebSocketMessage.getPayloadAsText();
                        })
                        .log())
                .then()
        );*/

        //Mono<Void> subscriber = webSocketClient.execute(URI.create(NEW_FIX_MESSAGES_TEST_URL), webSocketHandler);

        Mono<Void> subscriber = webSocketClient.execute(URI.create(NEW_FIX_MESSAGES_TEST_URL), webSocketHandler);

        subscriber.subscribe(
                content -> log.info("content"),
                error -> log.error("Error at receiving events: {}", error),
                () -> log.info("complete"));

        //return Flux.interval(Duration.ofSeconds(1)).map(event -> MessageQueue.messageQueue.poll() + "\n");
        //return Flux.interval(Duration.ofSeconds(1)).map(event -> "Test" + "\n");

        /*return Flux.interval(Duration.ofSeconds(1)).map(event -> ServerSentEvent.<String> builder()
                .id(String.valueOf(event))
                .event("Test event")
                .data("Test")
                .build());*/

        Flux<ServerSentEvent<String>> events = Flux.interval(Duration.ofSeconds(1)).map(event -> ServerSentEvent.<String>builder()
                .data(MessageQueue.messageQueue.poll())
                .build());

        return events;
    }

}
