package com.softkit.threads.demo.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "counter_log")
public class CounterLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer value;

    private LocalDateTime timestamp;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}
