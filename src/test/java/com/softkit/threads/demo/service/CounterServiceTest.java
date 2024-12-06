package com.softkit.threads.demo.service;

import com.softkit.threads.demo.components.Counter;
import com.softkit.threads.demo.model.CounterLog;
import com.softkit.threads.demo.repository.CounterLogRepository;
import com.softkit.threads.demo.service.impl.CounterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CounterServiceTest {

    @Mock
    private Counter mockCounter;
    @Mock
    private CounterLogRepository mockCounterLogRepository;
    @InjectMocks
    private CounterServiceImpl counterService;

    @Test
    void testUpdateCounter() throws Exception {
        int newValue = 50;

        counterService.updateCounter(newValue);

        verify(mockCounter, times(1)).setValue(newValue);
    }

    @Test
    void testSaveCounterLog() {
        int counterValue = 100;
        CounterLog mockCounterLog = CounterLog.builder().value(counterValue).build();
        when(mockCounterLogRepository.save(any(CounterLog.class))).thenReturn(mockCounterLog);

        CounterLog savedCounterLog = counterService.saveCounterLog(counterValue);

        assertEquals(counterValue, savedCounterLog.getValue());

        ArgumentCaptor<CounterLog> captor = ArgumentCaptor.forClass(CounterLog.class);
        verify(mockCounterLogRepository, times(1)).save(captor.capture());

        CounterLog capturedLog = captor.getValue();
        assertEquals(counterValue, capturedLog.getValue());
    }

    @Test
    void testCounterLogRepositoryInteractionWithReflection() throws Exception {
        int counterValue = 150;
        CounterLog counterLog = CounterLog.builder().value(counterValue).build();
        when(mockCounterLogRepository.save(any(CounterLog.class))).thenReturn(counterLog);

        CounterLog result = counterService.saveCounterLog(counterValue);

        assertEquals(counterValue, result.getValue());
    }
}
