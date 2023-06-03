package org.hzz.gateway;

import org.hzz.data.BookDto;
import org.hzz.mapper.Book;
import org.hzz.mappers.BookMapper;
import org.hzz.ports.spi.BookPersistencePort;
import org.hzz.repository.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookCrudDsGateway implements BookPersistencePort {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookCrudDsGateway(BookRepository bookRepository,
                             BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = changeBookDtoToBook(bookDto);
        Book savedBook = this.bookRepository.save(book);
        return changeBookToBookDto(savedBook);
    }

    @Override
    public void deleteBookById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        Book book = changeBookDtoToBook(bookDto);
        this.bookRepository.save(book);
        return null;
    }

    @Override
    public List<BookDto> getBooks() {
        List<Book> books = this.bookRepository.findAll();
        return this.bookMapper.toDtoList(books);
    }

    @Override
    public BookDto getBookById(Long bookId) {
        Book book = this.bookRepository.findById(bookId).orElse(null);
        return this.bookMapper.toDto(book);
    }

    private Book changeBookDtoToBook(BookDto bookDto) {
        return this.bookMapper.toEntity(bookDto);
    }

    private BookDto changeBookToBookDto(Book book) {
        return this.bookMapper.toDto(book);
    }
}
