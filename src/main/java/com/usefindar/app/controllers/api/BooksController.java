package com.usefindar.app.controllers.api;

import com.usefindar.app.entities.books.Book;
import com.usefindar.app.models.request.BookRequest;
import com.usefindar.app.models.response.system.ApiResponse;
import com.usefindar.app.security.JwtTokenProvider;
import com.usefindar.app.service.books.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BooksController {

    @Autowired
    private final BookService bookService;

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks(@RequestHeader String authorization) {
        if (isUserAuthorized(authorization)) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.UNAUTHORIZED), null);
        }

        var books = bookService.getAllBooks();
        var response = new ApiResponse<List<Book>>(HttpStatus.OK);
        response.setMessage("getAllBooks");
        response.setData(books);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@RequestHeader String authorization, @PathVariable Long id) {
        if (isUserAuthorized(authorization)) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        var book = bookService.getBookById(id).orElse(null);
        var response = (isNull(book)) ? new ApiResponse<Book>(HttpStatus.NOT_FOUND) : new ApiResponse<Book>(HttpStatus.OK);

        response.setMessage("getBookById");
        response.setData(book);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> addBook(@RequestHeader String authorization, @RequestBody BookRequest request) {
        if (isUserAuthorized(authorization)) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        var book = bookService.saveBook(request);
        var response = (isNull(book)) ? new ApiResponse<Book>(HttpStatus.BAD_REQUEST) : new ApiResponse<Book>(HttpStatus.OK);

        response.setMessage("addBook");
        response.setData(book);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@RequestHeader String authorization, @PathVariable Long id, @RequestBody BookRequest request) {
        if (isUserAuthorized(authorization)) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        var book = bookService.updateBook(id, request);
        var response = (isNull(book)) ? new ApiResponse<Book>(HttpStatus.BAD_REQUEST) : new ApiResponse<Book>(HttpStatus.OK);

        response.setMessage("updateBooks");
        response.setData(book);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBook(@RequestHeader String authorization, @PathVariable Long id) {
        if (isUserAuthorized(authorization)) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        bookService.deleteBook(id);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.GONE), HttpStatus.GONE);
    }

    /***
     * Validate auth tokens
     * @param authorization
     * @return
     */
    private boolean isUserAuthorized(String authorization) {
        return !jwtTokenProvider.validateToken(authorization) || jwtTokenProvider.isTokenExpired(authorization);
    }
}
