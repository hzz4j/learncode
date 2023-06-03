package org.hzz.config;

import org.hzz.ports.api.BookServicePort;
import org.hzz.ports.spi.BookPersistencePort;
import org.hzz.service.BookServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfig {

    @Bean
    public BookServicePort bookServicePort(BookPersistencePort bookPersistencePort) {
        return new BookServiceImpl(bookPersistencePort);
    }
}
