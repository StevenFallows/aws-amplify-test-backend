package uk.co.aquaq.awsamplifytestbackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;


@Slf4j
@SpringBootApplication
public class AwsAmplifyTestBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AwsAmplifyTestBackendApplication.class, args);
    }

    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public ReactorNettyWebSocketClient getWebSocketClient() {
        return new ReactorNettyWebSocketClient() {
        };
    }
}
