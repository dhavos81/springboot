package com.testing.course.springboottesting.mainApp.config;

import com.testing.course.springboottesting.mainApp.exception.ShouldRetryOnlyOnBlockingException;
import com.testing.course.springboottesting.mainApp.exception.ShouldRetryThernMoveToOtherTopicOnBlockingException;
import com.testing.course.springboottesting.mainApp.exception.ShouldSkipNonBlockingRetriesException;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaRetryTopic;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;

@Configuration
@EnableKafka
@EnableScheduling
//@EnableKafkaRetryTopic
public class RetryKafkaStrategyConfig extends RetryTopicConfigurationSupport {

    @Override
    protected void configureBlockingRetries(BlockingRetriesConfigurer blockingRetries) {
        blockingRetries
                .retryOn(ShouldRetryOnlyOnBlockingException.class, ShouldRetryThernMoveToOtherTopicOnBlockingException.class)
                .backOff(new FixedBackOff(50, 3));
    }

    @Override
    protected void manageNonBlockingFatalExceptions(List<Class<? extends Throwable>> nonBlockingFatalExceptions) {
        nonBlockingFatalExceptions.add(ShouldSkipNonBlockingRetriesException.class);
    }

}
