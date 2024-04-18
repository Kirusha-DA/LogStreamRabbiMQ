package com.rabbitmqexample.demo.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrimeNumberQueueMessage implements Serializable{
    private String guid;

    private Long start;

    private Long end;
}
