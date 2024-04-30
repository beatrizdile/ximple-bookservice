package org.example.springbootbookservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.springbootbookservice.model.Book;
import org.example.springbootbookservice.model.UpdateBookRequestBody;
import org.example.springbootbookservice.repository.BookRepository;
import org.example.springbootbookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@SecurityRequirement(name = "bearerAuth")
public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(
            description = "Get all registered books",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    ),
                    @ApiResponse(
                        description = "No Content Found",
                        responseCode = "204"
                    )
            }
    )
    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            var bookList = bookService.getAllBooks();

            if (bookList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get book by providing it's ID",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    )
            }
    )
    public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        try {
            var book = bookService.getBookById(id);

            if (book.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Operation(
            description = "Register a new book",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    )
            }
    )
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try {
            Book bookObj = bookService.addBook(book);
            return new ResponseEntity<>(bookObj, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    @Operation(
            description = "Update the attributes of a book by providing it's ID and a request body with the new content",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404"
                    )
            }
    )
    public ResponseEntity<Book> updateBookById(@PathVariable UUID id, @RequestBody UpdateBookRequestBody newBook) {
        try {
            Optional<Book> oldBook = bookService.getBookById(id);
            if (oldBook.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Book bookObj = bookService.updateBook(oldBook.get(), newBook);
            return new ResponseEntity<>(bookObj, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Delete a book that contains the provided ID",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    )
            }
    )
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable UUID id) {
        try {
            boolean status = bookService.deleteBook(id);
            if (!status)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
