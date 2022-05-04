package com.example.demo.service.mapper;

import com.example.demo.persistence.entity.BookEntity;
import com.example.demo.service.model.Book;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

import static com.example.demo.service.test.BookstoreTestFixtures.SOME_BOOKS_CSV_DATASET;
import static com.example.demo.service.test.BookstoreTestFixtures.SOME_TITLES_WITH_AUTHORS;
import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {
    private BookMapper sut;

    @BeforeEach
    final void setup() {
        sut = Mappers.getMapper(BookMapper.class);
    }

    @ParameterizedTest
    @CsvSource(
            useHeadersInDisplayName = true,
            textBlock = SOME_BOOKS_CSV_DATASET)
    void mapsFromEntity(String expectedTitle,
                        String expectedAuthor) {
        // given
        BookEntity bookEntity = new BookEntity(expectedTitle, expectedAuthor);

        // when
        Book actualMappedBook = sut.fromEntity(bookEntity);

        // then
        assertThat(actualMappedBook)
                .extracting(Book::title, Book::author)
                .containsExactly(expectedTitle, expectedAuthor);
    }

    @ParameterizedTest
    @CsvSource(
            useHeadersInDisplayName = true,
            textBlock = SOME_BOOKS_CSV_DATASET)
    void mapsToEntity(String expectedTitle,
                      String expectedAuthor) {
        // given
        Book book = new Book(expectedTitle, expectedAuthor);

        // when
        BookEntity actualMappedBookEntity = sut.toEntity(book);

        // then
        assertThat(actualMappedBookEntity)
                .extracting(BookEntity::getTitle, BookEntity::getAuthor)
                .containsExactly(expectedTitle, expectedAuthor);
    }

    @Test
    void mapsFromEntities() {
        // given
        List<List<String>> someTitlesWithAuthors = SOME_TITLES_WITH_AUTHORS;
        List<BookEntity> bookEntities = someTitlesWithAuthors.stream()
                .map(ta -> new BookEntity(ta.get(0), ta.get(1)))
                .toList();

        // when
        Collection<Book> actualMappedBooks = sut.fromEntites(bookEntities);

        // then
        assertThat(actualMappedBooks)
                .extracting(Book::title, Book::author)
                .containsExactly(
                        someTitlesWithAuthors.stream()
                                .map(List::toArray)
                                .map(Assertions::tuple)
                                .toArray(Tuple[]::new));
    }
}
