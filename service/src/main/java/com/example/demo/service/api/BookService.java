package com.example.demo.service.api;

import com.example.demo.service.model.Book;

import java.util.Collection;

public interface BookService {
    long addBook(Book book);
    Book getBook(long id);
    Collection<Book> findBooksByAuthor(String author);
}
