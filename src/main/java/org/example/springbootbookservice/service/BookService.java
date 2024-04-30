package org.example.springbootbookservice.service;

import org.example.springbootbookservice.model.Book;
import org.example.springbootbookservice.model.UpdateBookRequestBody;
import org.example.springbootbookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>(bookRepo.findAll());
        return bookList;
    }

    public Optional<Book> getBookById(UUID id) {
        Optional<Book> book = bookRepo.findById(id);
        return book;
    }

    public Book addBook(Book book) {
        Book bookObj = bookRepo.save(book);
        return (bookObj);
    }

    public Book updateBook(Book oldBook, UpdateBookRequestBody newBook) {
        if (newBook.getTitle().isPresent())
            oldBook.setTitle(newBook.getTitle().get());
        if (newBook.getAuthor().isPresent())
            oldBook.setAuthor(newBook.getAuthor().get());
        if (newBook.getIsbn().isPresent())
            oldBook.setIsbn(newBook.getIsbn().get());
        if (newBook.getQuantity().isPresent())
            oldBook.setQuantity(newBook.getQuantity().get());

        Book bookObj = bookRepo.save(oldBook);
        return bookObj;
    }

    public boolean deleteBook(UUID id) {
        Optional<Book> book = bookRepo.findById(id);
        if (book.isEmpty()) {
            return false;
        }
        bookRepo.deleteById(id);
        return true;
    }
}
