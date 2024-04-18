package com.rabbitmqexample.demo.dtos;

import lombok.Data;

@Data
public class PrimeNumbersRangeRequest {
    private long start;

    private long end;
}
