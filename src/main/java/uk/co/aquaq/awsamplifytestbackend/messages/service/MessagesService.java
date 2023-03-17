package uk.co.aquaq.awsamplifytestbackend.messages.service;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.co.aquaq.awsamplifytestbackend.messages.dto.Message;

import java.util.List;

public interface MessagesService {
    Mono<List<Message>> getMessages();

    Flux<ServerSentEvent<String>> getNewMessages();
}
