package uk.co.aquaq.awsamplifytestbackend.messages.dto;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {
    public static Queue<String> messageQueue = new ConcurrentLinkedQueue<>();
}
