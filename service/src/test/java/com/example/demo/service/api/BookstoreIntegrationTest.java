package com.example.demo.service.api;

import com.example.demo.persistence.entity.BookEntity;
import com.example.demo.persistence.repository.BookRepository;
import com.example.demo.service.config.ServiceConfiguration;
import com.example.demo.service.model.Book;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ContextConfiguration(classes = ServiceConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:file:./build/testdb/testdb",
        "spring.jpa.hibernate.ddl-auto=create",
})
class BookstoreIntegrationTest {
    @Autowired
    Bookstore sut;

    @Autowired
    BookRepository bookRepository;

    @Test
    void addsBook() {
        // given
        // setup
        Book expectedBook = new Book("Title", "Some Author");

        // when
        // act
        long id = sut.addBook(expectedBook);

        // then
        // aseert
        assertThat(bookRepository.findAll())
                .extracting(BookEntity::getId, BookEntity::getTitle, BookEntity::getAuthor)
                .containsExactly(tuple(id, "Title", "Some Author"));
    }

    @Test
    void getsBook() {
        BookEntity bookEntity = new BookEntity(
                "The Master and Margarita",
                "Mikhail Bulgakov");
        bookRepository.saveAndFlush(bookEntity);

        Book actualBook = sut.getBook(bookEntity.getId());

        assertThat(actualBook)
                .isNotNull()
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder().withIgnoredFields("id").build())
                .isEqualTo(bookEntity);
    }

    @Test
    void findsBookByAuthor() {
        BookEntity book1 = new BookEntity(
                "The Master and Margarita",
                "Mikhail Bulgakov");
        BookEntity book2 = new BookEntity(
                "Refactoring",
                "Martin Fowler");
        bookRepository.saveAllAndFlush(Arrays.asList(book1, book2));

        Collection<Book> actualBook = sut.findBooksByAuthor("%Fow%");

        assertThat(actualBook)
                .extracting(Book::title, Book::author)
                .containsExactly(tuple("Refactoring", "Martin Fowler"));
    }
}
