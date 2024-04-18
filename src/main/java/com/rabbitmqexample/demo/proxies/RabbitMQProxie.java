package com.rabbitmqexample.demo.proxies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rabbitmqexample.demo.activeDirectories.GeneratedPrimeNumbersStorage;
import com.rabbitmqexample.demo.dtos.GeneratedPrimeNumbersResponse;
import com.rabbitmqexample.demo.dtos.PrimeNumberQueueMessage;

@Component
public class RabbitMQProxie {
    final private RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProxie.class);
    
    public RabbitMQProxie(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Scheduled(fixedDelay = 1000)
    public void sendPrimeNumberMessage(PrimeNumberQueueMessage primeNumberQueueMessage) {
        LOGGER.info(String.format("Send JSON message -> %s", primeNumberQueueMessage));
        rabbitTemplate.convertAndSend("test-exchage", "foo.bar.#", primeNumberQueueMessage);
    }

    @RabbitListener(queues = "calculator")
    public void receiveAndCalculatePrimeNumberMessage(PrimeNumberQueueMessage in) {
        LOGGER.info(String.format("Received JSON message <- %s", in));
        var generatedPrimeNumbersResponse = calculatePrimeNumbersInRangeAndAddToMap(in); 
        GeneratedPrimeNumbersStorage.addGeneratedPrimeNumber(in.getGuid(), generatedPrimeNumbersResponse);
    }

    private GeneratedPrimeNumbersResponse calculatePrimeNumbersInRangeAndAddToMap(PrimeNumberQueueMessage primeNumber) {
        long lower = primeNumber.getStart();
        long upper = primeNumber.getEnd();

        long firstNumber = 0;
        boolean isFirstNumber = true;
        long lastNumber = 0;
        long count = 0;

        for (long i = lower; i <= upper; i++) {
            if (isPrime(i)) {
                if (isFirstNumber) {
                    firstNumber = i;
                }
                isFirstNumber = false;
                lastNumber = i;
                count++;
            }
        }

        var generatedPrimeNumbersResponse = new GeneratedPrimeNumbersResponse();
        generatedPrimeNumbersResponse.setFirst(firstNumber);
        generatedPrimeNumbersResponse.setLast(lastNumber);
        generatedPrimeNumbersResponse.setCount(count);
        return generatedPrimeNumbersResponse;
    }

    private boolean isPrime(long n) {
        if (n < 2) {
            return false;
        }

        for (int i = 2; i < n/2; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}
