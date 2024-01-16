package com.usefindar.app.service.books;

import com.usefindar.app.entities.books.Book;
import com.usefindar.app.models.request.BookRequest;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    Book saveBook(BookRequest book);

    Book updateBook(Long id, BookRequest book);

    void deleteBook(Long id);
}
