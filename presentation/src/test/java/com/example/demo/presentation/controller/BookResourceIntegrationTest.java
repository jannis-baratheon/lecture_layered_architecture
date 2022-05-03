package com.example.demo.presentation.controller;

import com.example.demo.persistence.entity.BookEntity;
import com.example.demo.persistence.repository.BookRepository;
import com.example.demo.presentation.config.PresentationConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(
        classes = PresentationConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
@AutoConfigureDataJpa
@Transactional
class BookResourceIntegrationTest {
    @Autowired
    private MockMvc restBookMockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void createsBook() throws Exception {
        String expectedTitle = "365 dni";
        String expectedAuthor = "Blanka Lipińska";

        restBookMockMvc
                .perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                        {
                                            "title": "%s",
                                            "author": "%s"
                                        }
                                        """,
                                expectedTitle,
                                expectedAuthor)))
                .andExpect(status().isCreated());

        assertThat(bookRepository.findAll())
                .extracting(BookEntity::getTitle, BookEntity::getAuthor)
                .containsExactly(tuple(expectedTitle, expectedAuthor));
    }

    @Test
    void getsBook() throws Exception {
        String expectedTitle = "365 dni";
        String expectedAuthor = "Blanka Lipińska";

        BookEntity bookEntity = bookRepository.saveAndFlush(new BookEntity(expectedTitle, expectedAuthor));

        restBookMockMvc
                .perform(get("/api/books/" + bookEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title").value(expectedTitle))
                .andExpect(jsonPath("$.author").value(expectedAuthor));
    }
}
