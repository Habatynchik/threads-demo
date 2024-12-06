package com.softkit.threads.demo.service.impl;

import com.softkit.threads.demo.components.Counter;
import com.softkit.threads.demo.model.ThreadRequestLog;
import com.softkit.threads.demo.repository.ThreadRequestLogRepository;
import com.softkit.threads.demo.service.CounterService;
import com.softkit.threads.demo.service.ThreadsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;

/**
 * Implementation of the {@link ThreadsService} interface that provides  managing producer and consumer threads.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ThreadsServiceImpl implements ThreadsService {
    private final Counter counter;
    private final ThreadRequestLogRepository threadRequestLogRepository;
    private final CounterService counterService;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Value("${threads.sleepTime.min}")
    private Integer minThreadSleepTime;
    @Value("${threads.sleepTime.max}")
    private Integer maxThreadSleepTime;

    /**
     * Increases the number of producer threads and executes them in the thread pool.
     *
     * @param producerThreads the number of producer threads to be created and executed
     *                        in the thread pool.
     */
    @Override
    public void increaseProducerThreads(Long producerThreads) {
        LongStream.iterate(1L, l -> l + 1)
                .limit(producerThreads)
                .forEach((e) ->
                        executor.execute(this::runProducerJob)
                );
    }

    /**
     * Increases the number of consumer threads and executes them in the thread pool.
     *
     * @param consumerThreads the number of consumer threads to be created and executed
     *                        in the thread pool.
     */
    @Override
    public void increaseConsumerThreads(Long consumerThreads) {
        LongStream.iterate(1L, l -> l + 1)
                .limit(consumerThreads)
                .forEach((e) ->
                        executor.execute(this::runConsumerJob)
                );
    }

    /**
     * Saves the number of consumer threads and producer threads to the DB.
     *
     * @param producerThreads the number of producer threads to be saved in DB.
     * @param consumerThreads the number of consumer threads to be saved in DB.
     * @return ThreadRequestLog entity
     */
    @Override
    public ThreadRequestLog saveThreadRequestLog(Long producerThreads, Long consumerThreads) {
        ThreadRequestLog threadRequestLog = ThreadRequestLog.builder()
                .consumers(consumerThreads)
                .producers(producerThreads)
                .build();
        threadRequestLogRepository.save(threadRequestLog);
        log.info("Saving thread request log: {}", threadRequestLog);
        return threadRequestLog;
    }

    private void runProducerJob() {
        long sleepTime = Math.round(minThreadSleepTime + Math.random() * (maxThreadSleepTime - minThreadSleepTime));
        log.info("Created producer job with name: {}, sleepTime: {}", Thread.currentThread().getName(), sleepTime);

        while (counter.getValue() > 0 && counter.getValue() < 100) {
            try {
                int val = counter.increment();
                log.info("Producer {} increase counter to {}", Thread.currentThread().getName(), val);
                generateCounterLog(val);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Thread.currentThread().interrupt();
        log.info("Producer job with name: {} ended", Thread.currentThread().getName());
    }

    private void runConsumerJob() {
        long sleepTime = Math.round(minThreadSleepTime + Math.random() * (maxThreadSleepTime - minThreadSleepTime));
        log.info("Created consumer job with name: {}, sleepTime: {}", Thread.currentThread().getName(), sleepTime);

        while (counter.getValue() > 0 && counter.getValue() < 100) {
            try {
                int val = counter.decrement();
                log.info("Consumer {} decrease counter to {}", Thread.currentThread().getName(), val);
                generateCounterLog(val);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Thread.currentThread().interrupt();
        log.info("Consumer job with name: {} ended", Thread.currentThread().getName());
    }

    private void generateCounterLog(Integer value) {
        if (value == 0 || value == 100) {
            counterService.saveCounterLog(value);
        }
    }
}
