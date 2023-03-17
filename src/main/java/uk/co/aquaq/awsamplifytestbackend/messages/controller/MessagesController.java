package uk.co.aquaq.awsamplifytestbackend.messages.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.co.aquaq.awsamplifytestbackend.messages.dto.Message;
import uk.co.aquaq.awsamplifytestbackend.messages.service.MessagesService;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessagesController {

    private final MessagesService messagesService;

    @GetMapping("")
    public Mono<List<Message>> getMessages() {
        return messagesService.getMessages();
    }

    /*@CrossOrigin(value="*")
    @GetMapping(value = "/new", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> getNewOrders() {
        System.out.println("Calling getNewMessages");
        return ordersService.getNewMessages();
    }*/

    @CrossOrigin(value="*")
    @GetMapping(value = "/new", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ServerSentEvent<String>> getNewMessages() {
        System.out.println("Calling getNewMessages");
        return messagesService.getNewMessages();
    }

    /*@CrossOrigin(value="*")
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> streamEvents() {
        //return Flux.interval(Duration.ofSeconds(2)).map(event -> MessageQueue.messageQueue.poll() + "\n");
        return Flux.interval(Duration.ofSeconds(2)).map(event -> "Test \n");
    }*/

    @CrossOrigin(value="*")
    @GetMapping(value = "/test")
    public Flux<ServerSentEvent<String>> streamEvents() {
        //return Flux.interval(Duration.ofSeconds(2)).map(event -> MessageQueue.messageQueue.poll() + "\n");
        return Flux.interval(Duration.ofSeconds(2)).map(event -> ServerSentEvent.<String> builder()
                .id(String.valueOf(event))
                .event("Test event")
                .data("Test")
                .build());
    }
}
