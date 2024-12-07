package com.softkit.threads.demo.components;

import org.springframework.stereotype.Component;

@Component
public class Counter {
    private int value = 50;
    private final Object mutex = new Object();

    public int increment() {
        synchronized (mutex) {
            return ++value;
        }
    }

    public synchronized int decrement() {
        synchronized (mutex) {
            return --value;
        }
    }

    public synchronized int getValue() {
        synchronized (mutex) {
            return value;
        }
    }

    public synchronized void setValue(int value) {
        synchronized (mutex) {
            this.value = value;
        }
    }
}