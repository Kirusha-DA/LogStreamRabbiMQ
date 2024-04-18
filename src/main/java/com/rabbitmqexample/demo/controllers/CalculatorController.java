package com.rabbitmqexample.demo.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rabbitmqexample.demo.activeDirectories.GeneratedPrimeNumbersStorage;
import com.rabbitmqexample.demo.dtos.GeneratedPrimeNumbersResponse;
import com.rabbitmqexample.demo.dtos.GuidResponse;
import com.rabbitmqexample.demo.dtos.PrimeNumbersRangeRequest;
import com.rabbitmqexample.demo.services.CalculatorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/calc/prime")
    public GuidResponse calculatePrimeNumbersInRange(
        @RequestBody PrimeNumbersRangeRequest primeNumbersRangeRequest
    ) {

        String guid = calculatorService.sendPrimeNumbersInRange(
            primeNumbersRangeRequest.getStart(),
            primeNumbersRangeRequest.getEnd()
        );
        var guidResponse = new GuidResponse();
        guidResponse.setGuid(guid);
        return guidResponse;
    }

    @GetMapping("/calc/prime/{guid}")
    public ResponseEntity<GeneratedPrimeNumbersResponse> getCalculatedPrimeNumbersInfo(@PathVariable String guid) {
        var generatedPrimeNumber = GeneratedPrimeNumbersStorage.getGeneratePrimeNumberByGuid(guid);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(generatedPrimeNumber);
    }
        
}