package com.example.demo.presentation.controller;

import com.example.demo.service.api.Bookstore;
import com.example.demo.service.model.Book;
import com.example.demo.presentation.mapper.BookDTOMapper;
import com.example.demo.presentation.model.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@ResponseBody
@RequestMapping("/api")
public class BookResource {
    private final Bookstore bookstore;
    private final BookDTOMapper bookDTOMapper;

    public BookResource(Bookstore bookstore,
                        BookDTOMapper bookDTOMapper) {
        this.bookstore = bookstore;
        this.bookDTOMapper = bookDTOMapper;
    }

    @PostMapping("/books")
    public ResponseEntity<Long> createBook(@RequestBody BookDTO bookDTO) throws URISyntaxException {
        Book book = bookDTOMapper.toServiceObject(bookDTO);

        long id = bookstore.addBook(book);

        return ResponseEntity
                .created(new URI("/api/books/" + id))
                .body(id);
    }

    @GetMapping("/books/{id}")
    public BookDTO getBook(@PathVariable long id) {
        Book book = bookstore.getBook(id);

        return bookDTOMapper.fromServiceObject(book);
    }
}
