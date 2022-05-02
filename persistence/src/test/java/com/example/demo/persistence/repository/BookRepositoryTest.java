package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = TestConfig.class)
@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void addsBook() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle("Vademecum ojca");
        bookEntity.setAuthor("Janusz Korwin-Mikke");
        bookRepository.saveAndFlush(bookEntity);

        BookEntity actualBookEntity = bookRepository.getById(bookEntity.getId());

        assertThat(actualBookEntity)
                .isNotNull()
                .isEqualTo(bookEntity);
    }
}
