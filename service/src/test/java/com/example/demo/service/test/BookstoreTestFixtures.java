package com.example.demo.service.test;

import com.example.demo.persistence.entity.BookEntity;
import com.example.demo.service.model.Book;

import java.util.List;

public final class BookstoreTestFixtures {
    public static final Book SOME_VALID_BOOK = new Book("Gone with the wind", "Margaret Mitchell");
    public static final BookEntity SOME_VALID_BOOK_ENTITY = new BookEntity(1L, "Silence of the lambs", "Thomas Harris");
    public static final String SOME_VALID_AUTHOR = "Blanka Lipińska";
    public static final String SOME_BOOKS_CSV_DATASET =
            """
                    Expected title       , Expected author
                    Refactoring          , Martin Fowler
                    Silence of the lambs , Thomas Harris
                    365 dni              , Blanka Lipińska
                    """;
    public static final List<List<String>> SOME_TITLES_WITH_AUTHORS = List.of(
            List.of("Refactoring", "Martin Fowler"),
            List.of("Silence of the lambs", "Thomas Harris"),
            List.of("365 dni", "Blanka Lipińska"));

    private BookstoreTestFixtures() {
    }
}
