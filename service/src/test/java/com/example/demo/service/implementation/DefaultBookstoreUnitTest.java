package com.example.demo.service.implementation;

import com.example.demo.persistence.entity.BookEntity;
import com.example.demo.persistence.repository.BookRepository;
import com.example.demo.service.api.Bookstore;
import com.example.demo.service.mapper.BookMapper;
import com.example.demo.service.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

import static com.example.demo.service.test.BookstoreTestFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DefaultBookstoreUnitTest {

    private Bookstore sut;
    private BookMapper bookMapperMock;
    private BookRepository bookRepositoryMock;

    @BeforeEach
    final void setup() {
        bookMapperMock = mock(BookMapper.class);
        bookRepositoryMock = mock(BookRepository.class);
        sut = new DefaultBookstore(bookRepositoryMock, bookMapperMock);
    }

    @Test
    void addsBook() {
        // given
        long expectedId = 42L;
        Book book = SOME_VALID_BOOK;
        BookEntity bookEntity = SOME_VALID_BOOK_ENTITY;
        bookEntity.setId(expectedId);
        when(bookMapperMock.toEntity(any())).thenReturn(bookEntity);
        when(bookRepositoryMock.saveAndFlush(any())).thenReturn(bookEntity);

        // when
        long actualId = sut.addBook(book);

        // then
        // verify return value
        assertThat(actualId).isEqualTo(expectedId);
        // verify IoC calls
        verify(bookMapperMock, times(1)).toEntity(same(book));
        verify(bookRepositoryMock, times(1)).saveAndFlush(same(bookEntity));
    }

    @Test
    void getsBook() {
        // given
        long expectedId = 42L;
        Book book = SOME_VALID_BOOK;
        BookEntity bookEntity = SOME_VALID_BOOK_ENTITY;
        bookEntity.setId(expectedId);
        when(bookMapperMock.fromEntity(any())).thenReturn(book);
        when(bookRepositoryMock.getById(any())).thenReturn(bookEntity);

        // when
        Book actualBook = sut.getBook(expectedId);

        // then
        // verify return value
        assertThat(actualBook).isSameAs(book);
        // verify IoC calls
        verify(bookMapperMock, times(1)).fromEntity(same(bookEntity));
        verify(bookRepositoryMock, times(1)).getById(expectedId);
    }

    @Test
    @SuppressWarnings("unchecked")
    void findsBookByAuthor() {
        // given
        List<BookEntity> bookEntities = mock(List.class);
        Collection<Book> books = mock(Collection.class);
        when(bookMapperMock.fromEntites(any())).thenReturn(books);
        when(bookRepositoryMock.findAll(any(Specification.class))).thenReturn(bookEntities);

        // when
        Collection<Book> actualBooksByAuthor = sut.findBooksByAuthor(SOME_VALID_AUTHOR);

        // then
        // verify return value
        assertThat(actualBooksByAuthor).isSameAs(books);
        // verify IoC calls
        verify(bookMapperMock, times(1)).fromEntites(same(bookEntities));
        /*
            Verifying the Specification passed in as argument to #findAll would be too messy, so skip it.
            We have it covered by the integration test anyway - sometimes you just have to let it go.
         */
        verify(bookRepositoryMock, times(1)).findAll(any(Specification.class));
    }
}
