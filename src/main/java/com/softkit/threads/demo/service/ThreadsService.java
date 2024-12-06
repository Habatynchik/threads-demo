package com.softkit.threads.demo.service;

import com.softkit.threads.demo.model.ThreadRequestLog;

public interface ThreadsService {

    void increaseProducerThreads(Long producerThreads);

    void increaseConsumerThreads(Long consumerThreads);

    ThreadRequestLog saveThreadRequestLog(Long producerThreads, Long consumerThreads);
}
