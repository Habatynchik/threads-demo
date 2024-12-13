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

    public int decrement() {
        synchronized (mutex) {
            return --value;
        }
    }

    public int getValue() {
        synchronized (mutex) {
            return value;
        }
    }

    public void setValue(int value) {
        synchronized (mutex) {
            this.value = value;
        }
    }
}