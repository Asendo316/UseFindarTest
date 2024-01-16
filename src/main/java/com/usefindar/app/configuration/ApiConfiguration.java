package com.usefindar.app.configuration;

import com.usefindar.app.entities.books.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {

    @Bean
    public Book books() {
        return new Book();
    }
}