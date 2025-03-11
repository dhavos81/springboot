package com.testing.course.springboottesting.mainApp.kafka.consumer;

import com.testing.course.springboottesting.mainApp.exception.ShouldRetryNonBlockingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import static org.springframework.kafka.retrytopic.DltStrategy.FAIL_ON_ERROR;


@Slf4j
@Component
public class StatisticsEmployyeEventBatchConsumer {

    //@KafkaHandler
    @RetryableTopic(attempts = "5",
            dltStrategy = FAIL_ON_ERROR,
            backoff = @Backoff(delay = 3000L, maxDelay = 6000L, multiplier = 2),
            timeout = "6000000",
            retryTopicSuffix = "stat-",
            dltTopicSuffix = "dlt-stat-")
    @KafkaListener(topics = "employee-events", groupId = "statistics-employee-events-group-id")
    public void consumeMessage(String message) throws ShouldRetryNonBlockingException {
        log.info("Received on second queue: {}", message);
        //send bach
    }

    @DltHandler
    public void processDltMessage(String message) {
        log.info("In " + this.getClass() + " employee message failed and put into DLQ, processing it in DLQ" + message);
        // do additional things to message
    }
}
