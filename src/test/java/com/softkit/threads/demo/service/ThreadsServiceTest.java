package com.softkit.threads.demo.service;

import com.softkit.threads.demo.components.Counter;
import com.softkit.threads.demo.model.ThreadRequestLog;
import com.softkit.threads.demo.repository.ThreadRequestLogRepository;
import com.softkit.threads.demo.service.impl.ThreadsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ThreadsServiceTest {
    @Spy
    private Counter mockCounter;
    @Mock
    private ThreadRequestLogRepository mockThreadRequestLogRepository;
    @Mock
    private CounterService mockCounterService;
    @InjectMocks
    private ThreadsServiceImpl threadsService;

    @BeforeEach
    void setUp() throws Exception {
        setPrivateField(threadsService, "minThreadSleepTime", 100);
        setPrivateField(threadsService, "maxThreadSleepTime", 1000);
    }

    @Test
    void testSaveThreadRequestLog() {
        Long producerThreads = 5L;
        Long consumerThreads = 3L;
        ArgumentCaptor<ThreadRequestLog> captor = ArgumentCaptor.forClass(ThreadRequestLog.class);

        ThreadRequestLog result = threadsService.saveThreadRequestLog(producerThreads, consumerThreads);

        verify(mockThreadRequestLogRepository, times(1)).save(captor.capture());
        ThreadRequestLog capturedLog = captor.getValue();
        assertEquals(producerThreads, capturedLog.getProducers());
        assertEquals(consumerThreads, capturedLog.getConsumers());
        assertEquals(capturedLog, result);
    }

    @Test
    void testIncreaseProducerThreads() {
        threadsService.increaseProducerThreads(3L);

        verify(mockCounter, timeout(2000).atLeast(1)).increment();
    }

    @Test
    void testIncreaseConsumerThreads() {
        threadsService.increaseConsumerThreads(2L);

        verify(mockCounter, timeout(2000).atLeast(1)).decrement();
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
