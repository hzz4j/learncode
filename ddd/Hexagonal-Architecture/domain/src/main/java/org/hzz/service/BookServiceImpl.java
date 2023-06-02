package org.hzz.service;

import org.hzz.data.BookDto;
import org.hzz.ports.api.BookServicePort;
import org.hzz.ports.spi.BookPersistencePort;

import java.util.List;

public class BookServiceImpl implements BookServicePort {
    private BookPersistencePort bookPersistencePort;

    public BookServiceImpl(BookPersistencePort bookPersistencePort) {
        this.bookPersistencePort = bookPersistencePort;
    }
    @Override
    public BookDto addBook(BookDto bookDto) {
        return this.bookPersistencePort.addBook(bookDto);
    }

    @Override
    public void deleteBookById(Long id) {
        this.bookPersistencePort.deleteBookById(id);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return this.bookPersistencePort.updateBook(bookDto);
    }

    @Override
    public List<BookDto> getBooks() {
        return this.bookPersistencePort.getBooks();
    }

    @Override
    public BookDto getBookById(Long bookId) {
        return this.getBookById(bookId);
    }
}
