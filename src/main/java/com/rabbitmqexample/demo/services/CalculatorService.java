package com.rabbitmqexample.demo.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.rabbitmqexample.demo.dtos.PrimeNumberQueueMessage;
import com.rabbitmqexample.demo.proxies.RabbitMQProxie;

@Service
public class CalculatorService {
    final private RabbitMQProxie rabbitMQProxie;

    public CalculatorService(RabbitMQProxie rabbitMQProxie) {
        this.rabbitMQProxie = rabbitMQProxie;
    }

    public String sendPrimeNumbersInRange(Long start, Long end) {
        String guid = UUID.randomUUID().toString();

        var primeNumberQueueMessage = new PrimeNumberQueueMessage();
        primeNumberQueueMessage.setGuid(guid);
        primeNumberQueueMessage.setStart(start);
        primeNumberQueueMessage.setEnd(end);

        rabbitMQProxie.sendPrimeNumberMessage(primeNumberQueueMessage);
        return guid;
    }
}
