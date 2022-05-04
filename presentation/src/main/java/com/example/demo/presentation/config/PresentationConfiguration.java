package com.example.demo.presentation.config;

import com.example.demo.presentation.controller.BookResource;
import com.example.demo.presentation.mapper.BookDTOMapper;
import com.example.demo.service.api.Bookstore;
import com.example.demo.service.config.ServiceConfiguration;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@Import(ServiceConfiguration.class)
public class PresentationConfiguration  {
    @Bean
    BookDTOMapper bookDTOMapper() {
        return Mappers.getMapper(BookDTOMapper.class);
    }

    @Bean
    BookResource bookResource(Bookstore bookstore,
                              BookDTOMapper bookDTOMapper) {
        return new BookResource(bookstore, bookDTOMapper);
    }
}
