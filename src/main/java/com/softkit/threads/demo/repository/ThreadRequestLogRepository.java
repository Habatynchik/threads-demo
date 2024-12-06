package com.softkit.threads.demo.repository;

import com.softkit.threads.demo.model.ThreadRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRequestLogRepository extends JpaRepository<ThreadRequestLog, Long> {
}
