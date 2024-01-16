package com.usefindar.app.implementation.books;

import com.usefindar.app.entities.books.Book;
import com.usefindar.app.models.request.BookRequest;
import com.usefindar.app.repository.books.BookRepository;
import com.usefindar.app.service.books.BookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepository bookRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Book> getAllBooks() {
        logger.info("Find all books start");
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            logger.info("Find all books failed - ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book saveBook(BookRequest request) {
        logger.info("Book save start - {}", request);
        try {
            var book = new Book();
            book.setAuthor(request.getAuthor());
            book.setAvailable(request.isAvailable());
            book.setTitle(request.getTitle());
            return bookRepository.save(book);
        } catch (Exception e) {
            logger.info("Book save failed - ", e);
            return null;
        }
    }

    @Override
    public Book updateBook(Long id, BookRequest request) {
        logger.info("Book update start - {}", request);
        try {
            var book = getBookById(id).orElse(null);
            if (!isNull(book)) {
                book.setAuthor(request.getAuthor());
                book.setAvailable(request.isAvailable());
                book.setTitle(request.getTitle());
                return bookRepository.save(book);
            } else {
                logger.info("Book update failed, Book not found - {}", request);
                return null;
            }
        } catch (Exception e) {
            logger.info("Book update failed - ", e);
            return null;
        }
    }

    @Override
    public void deleteBook(Long id) {
        logger.info("Book delete start - {}", id);
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            logger.info("Book delete failed - ", e);
        }
    }
}
