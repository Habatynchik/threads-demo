package com.softkit.threads.demo.service;

import com.softkit.threads.demo.model.CounterLog;

public interface CounterService {

    void updateCounter(Integer value);

    CounterLog saveCounterLog(Integer counterValue);
}
