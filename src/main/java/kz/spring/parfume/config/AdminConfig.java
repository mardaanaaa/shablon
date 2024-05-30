package kz.spring.parfume.config;

import kz.spring.parfume.observers.AdminObservable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfig {

    @Bean
    public AdminObservable adminObservable() {
        return new AdminObservable();
    }
}