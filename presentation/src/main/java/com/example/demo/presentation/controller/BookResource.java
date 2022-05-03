package com.example.demo.presentation.controller;

import com.example.demo.service.api.BookService;
import com.example.demo.service.model.Book;
import com.example.demo.presentation.mapper.BookDTOMapper;
import com.example.demo.presentation.model.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class BookResource {
    private final BookService bookService;
    private final BookDTOMapper bookDTOMapper;

    public BookResource(BookService bookService,
                        BookDTOMapper bookDTOMapper) {
        this.bookService = bookService;
        this.bookDTOMapper = bookDTOMapper;
    }

    @PostMapping("/books")
    @ResponseBody
    public ResponseEntity<Long> createBook(@RequestBody BookDTO bookDTO) throws URISyntaxException {
        Book book = bookDTOMapper.toServiceObject(bookDTO);

        long id = bookService.addBook(book);

        return ResponseEntity
                .created(new URI("/api/books/" + id))
                .body(id);
    }

    @GetMapping("/books/{id}")
    @ResponseBody
    public BookDTO getBook(@PathVariable long id) {
        Book book = bookService.getBook(id);

        return bookDTOMapper.fromServiceObject(book);
    }
}
