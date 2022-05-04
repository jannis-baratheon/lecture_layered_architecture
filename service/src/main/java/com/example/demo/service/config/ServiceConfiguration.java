package com.example.demo.service.config;

import com.example.demo.persistence.config.PersistenceConfiguration;
import com.example.demo.persistence.repository.BookRepository;
import com.example.demo.service.api.Bookstore;
import com.example.demo.service.implementation.DefaultBookstore;
import com.example.demo.service.mapper.BookMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfiguration.class)
public class ServiceConfiguration {
    @Bean
    public Bookstore bookService(BookRepository bookRepository, BookMapper bookMapper) {
        return new DefaultBookstore(bookRepository, bookMapper);
    }

    @Bean
    BookMapper modelMapper() {
        return Mappers.getMapper(BookMapper.class);
    }
}
