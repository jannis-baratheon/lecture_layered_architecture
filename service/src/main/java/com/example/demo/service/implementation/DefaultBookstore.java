package com.example.demo.service.implementation;

import com.example.demo.persistence.entity.BookEntity;
import com.example.demo.persistence.repository.BookRepository;
import com.example.demo.service.api.Bookstore;
import com.example.demo.service.mapper.BookMapper;
import com.example.demo.service.model.Book;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

public class DefaultBookstore implements Bookstore {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public DefaultBookstore(BookRepository bookRepository,
                            BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public long addBook(Book book) {
        BookEntity bookEntity = bookMapper.toEntity(book);
        return bookRepository.saveAndFlush(bookEntity).getId();
    }

    @Override
    public Book getBook(long id) {
        return bookMapper.fromEntity(bookRepository.getById(id));
    }

    @Override
    public Collection<Book> findBooksByAuthor(String authorPattern) {
        List<BookEntity> booksByAuthor = bookRepository.findAll(
                (Specification<BookEntity>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get("author"), authorPattern));

        return bookMapper.fromEntites(booksByAuthor);
    }
}
