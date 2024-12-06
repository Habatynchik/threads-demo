package com.softkit.threads.demo.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "thread_request_log ")
public class ThreadRequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long producers;

    private Long consumers;

    private LocalDateTime timestamp;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}
