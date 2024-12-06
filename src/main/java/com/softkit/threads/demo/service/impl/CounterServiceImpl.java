package com.softkit.threads.demo.service.impl;

import com.softkit.threads.demo.components.Counter;
import com.softkit.threads.demo.model.CounterLog;
import com.softkit.threads.demo.repository.CounterLogRepository;
import com.softkit.threads.demo.service.CounterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link CounterService} interface for updating the counter value.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CounterServiceImpl implements CounterService {
    private final Counter counter;
    private final CounterLogRepository counterLogRepository;

    /**
     * Updates the value of the counter.
     *
     * @param counterValue the new value for the counter
     */
    @Override
    public void updateCounter(Integer counterValue) {
        counter.setValue(counterValue);
        log.info("Updated counter value: {}", counterValue);
    }

    /**
     * Saves the value of the counter to the DB.
     *
     * @param counterValue value for the counter
     */
    @Override
    public CounterLog saveCounterLog(Integer counterValue) {
        CounterLog counterLog = CounterLog.builder()
                .value(counterValue)
                .build();
        counterLogRepository.save(counterLog);
        log.info("Saved counter log: {}", counterLog);
        return counterLog;
    }
}
