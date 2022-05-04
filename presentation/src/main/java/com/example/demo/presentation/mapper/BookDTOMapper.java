package com.example.demo.presentation.mapper;

import com.example.demo.presentation.model.BookDTO;
import com.example.demo.service.model.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookDTOMapper {
    BookDTO fromServiceObject(Book book);

    Book toServiceObject(BookDTO book);
}
