package com.softkit.threads.demo.repository;

import com.softkit.threads.demo.model.CounterLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterLogRepository extends JpaRepository<CounterLog, Long> {
}
