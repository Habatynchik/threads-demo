package com.softkit.threads.demo.controller;

import com.softkit.threads.demo.model.CounterLog;
import com.softkit.threads.demo.model.ThreadRequestLog;
import com.softkit.threads.demo.service.CounterService;
import com.softkit.threads.demo.service.ThreadsService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ThreadsController {
    private final ThreadsService threadsService;
    private final CounterService counterService;

    @PostMapping("/threads")
    public ResponseEntity<String> createThreads(@RequestParam @Min(0) Long producers,
                                                @RequestParam @Min(0) Long consumers) {
        threadsService.increaseConsumerThreads(consumers);
        threadsService.increaseProducerThreads(producers);
        ThreadRequestLog threadRequestLog = threadsService.saveThreadRequestLog(consumers, producers);
        return new ResponseEntity<>(threadRequestLog.toString(), HttpStatus.CREATED);
    }

    @PutMapping("/counter")
    public ResponseEntity<String> updateCounter(@RequestParam @Min(0) @Max(100) Integer counterValue) {
        counterService.updateCounter(counterValue);
        CounterLog counterLog = counterService.saveCounterLog(counterValue);
        return new ResponseEntity<>(counterLog.toString(), HttpStatus.OK);
    }
}
