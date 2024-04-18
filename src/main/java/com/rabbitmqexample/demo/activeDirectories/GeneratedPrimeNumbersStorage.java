package com.rabbitmqexample.demo.activeDirectories;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmqexample.demo.dtos.GeneratedPrimeNumbersResponse;
import com.rabbitmqexample.demo.exceptions.GuidNotFoundException;

public class GeneratedPrimeNumbersStorage {
    private static Map<String, GeneratedPrimeNumbersResponse> generatedPrimeNumbers = new HashMap<>();    


    public static void addGeneratedPrimeNumber(String guid, GeneratedPrimeNumbersResponse generatedPrimeNumber) {
        generatedPrimeNumbers.put(guid, generatedPrimeNumber);
    }

    public static GeneratedPrimeNumbersResponse getGeneratePrimeNumberByGuid(String guid) {
        var generatedPrimeNumbersResponse = generatedPrimeNumbers.get(guid);
        if (generatedPrimeNumbersResponse == null) {
            throw new GuidNotFoundException();
        }
        return generatedPrimeNumbersResponse;
    }
}