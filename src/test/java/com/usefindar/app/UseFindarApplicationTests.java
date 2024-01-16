package com.usefindar.app;

import com.usefindar.app.entities.books.Book;
import com.usefindar.app.implementation.books.BookServiceImpl;
import com.usefindar.app.models.request.BookRequest;
import com.usefindar.app.repository.books.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UseFindarApplicationTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getBookById_ValidId_ReturnsBook() {
        // Arrange
        Long id = 1L;
        var expectedBook = new Book();
        expectedBook.setId(id);
        when(bookRepository.findById(id)).thenReturn(Optional.of(expectedBook));

        // Act
        var result = bookService.getBookById(id);

        // Assert
        assertEquals(expectedBook, result.orElse(null));
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void getAllBooks_ReturnsListOfBooks() {
        // Arrange
        List<Book> expectedBooks = Arrays.asList(
                new Book(1L, "Book 1", "Author 1", true),
                new Book(2L, "Book 2", "Author 2", true)
        );
        when(bookService.getAllBooks()).thenReturn(expectedBooks);

        // Act
        var result = bookService.getAllBooks();

        // Assert
        assertEquals(expectedBooks, result);
    }

    @Test
    void getAllBooks_ReturnsEmptyList() {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());

        // Act
        var result = bookService.getAllBooks();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void addBook_ReturnsCreatedBook() {
        // Arrange
        var newBook = new BookRequest("New Book", "New Author", true);
        var expectedBook = new Book(1L, "New Book", "New Author", true);
        when(bookService.saveBook(newBook)).thenReturn(expectedBook);

        // Act
        var response = bookService.saveBook(newBook);

        // Assert
        assertEquals(expectedBook, response);
    }

    @Test
    void updateBook_InvalidId_ReturnsNotFound() {
        // Arrange
        Long id = 999L;
        var updatedBook = new BookRequest("Updated Book", "Updated Author", true);
        when(bookService.getBookById(id)).thenReturn(Optional.empty());

        // Act
        var response = bookService.updateBook(id, updatedBook);

        // Assert
        assertNull(response);
    }

}
