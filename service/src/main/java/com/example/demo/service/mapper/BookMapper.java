package com.example.demo.service.mapper;

import com.example.demo.persistence.entity.BookEntity;
import com.example.demo.service.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    BookEntity toEntity(Book book);

    Book fromEntity(BookEntity bookEntity);

    Collection<Book> fromEntites(Collection<BookEntity> bookEntities);
}
