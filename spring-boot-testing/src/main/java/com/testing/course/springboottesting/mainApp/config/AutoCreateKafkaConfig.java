package com.testing.course.springboottesting.mainApp.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
//@Profile("local")
public class AutoCreateKafkaConfig {

    @Bean
    public KafkaAdmin.NewTopics employeeEvents(){
        return new KafkaAdmin.NewTopics(
                TopicBuilder
                .name("employee-events")
                .replicas(1)
                .partitions(1)
                .build());
    }

    @Bean
    DefaultErrorHandler defaultErrorHandler() {
        return new DefaultErrorHandler((rec, ex) -> {
            log.info("Problem with message for topic {}", rec.topic());
            log.error("Problem with kafka message: {}", rec);
        }, new FixedBackOff(3000L, 3L));
    }

    /* for claud
    @Bean
    public ListenerContainerCustomizer<AbstractMessageListenerContainer<?,?>> listenerContainerCustomizer(){
        return (container, dest, group) ->
                container.setErrorHandler(containerAwareErrorHandler());
    }

    public SeekToCurrentErrorHandler containerAwareErrorHandler(){
        return new SeekToCurrentErrorHandler(new FixedBackOff(0, maxAttempts-1);
    }

     */

}
