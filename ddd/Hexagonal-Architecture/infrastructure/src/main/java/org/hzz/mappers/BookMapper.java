package org.hzz.mappers;

import org.hzz.data.BookDto;
import org.hzz.mapper.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookMapper {
    public abstract BookDto toDto(Book book);
    public abstract Book toEntity(BookDto bookDto);

    public abstract List<BookDto> toDtoList(List<Book> books);
    public abstract List<Book> toEntityList(List<BookDto> bookDtos);
}
