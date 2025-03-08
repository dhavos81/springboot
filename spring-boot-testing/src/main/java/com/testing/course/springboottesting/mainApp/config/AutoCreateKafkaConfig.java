package com.testing.course.springboottesting.mainApp.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;


@Slf4j
@Configuration
//@Profile("local")
public class AutoCreateKafkaConfig {
/*
    @Bean
    public RetryTopicConfiguration employeeRetryTopic(KafkaTemplate<Long, String> template) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .autoCreateTopics(true, 1, (short) 1)
                .create(template);
        //.autoCreateTopics(true,1, (short) 1)
    }

*/
    @Bean
    public KafkaAdmin.NewTopics employeeEvents(){
        return new KafkaAdmin.NewTopics(
                TopicBuilder
                .name("employee-events")
                .replicas(1)
                .partitions(1)
                .build(),
        TopicBuilder
                .name("employee-events-dlt")
                .replicas(1)
                .partitions(1)
                .build());
    }
/*

    @Bean
    public DefaultErrorHandler errorHandler() {
        BackOff fixedBackOff = new FixedBackOff(interval, maxAttempts);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
            // logic to execute when all the retry attemps are exhausted
        }, fixedBackOff);
        return errorHandler;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> greetingKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        // Other configurations
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.afterPropertiesSet();
        return factory;
    }

    // blockable retry
    @Bean
    DefaultErrorHandler defaultErrorHandler() {
        return new DefaultErrorHandler((rec, ex) -> {
            log.info("Problem with message for topic {}", rec.topic());
            log.error("Problem with kafka message: {}", rec);
        }, new FixedBackOff(4000L, 3L));
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
