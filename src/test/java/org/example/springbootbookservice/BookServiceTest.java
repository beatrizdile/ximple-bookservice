package org.example.springbootbookservice;

import org.example.springbootbookservice.model.Book;
import org.example.springbootbookservice.model.UpdateBookRequestBody;
import org.example.springbootbookservice.repository.BookRepository;
import org.example.springbootbookservice.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class BookServiceTest {

	@Mock
	BookRepository bookRepository;

	@InjectMocks
	BookService bookService;

	Book book1 = new Book(UUID.randomUUID(),
			"Flowers for Algernon",
			"Daniel Keyes",
			9780151001637L,
			7);

	UpdateBookRequestBody book2 = new UpdateBookRequestBody(
			Optional.of("The Book Thief"),
			Optional.of("Markus Zusak"),
			Optional.of(9781101934180L),
			Optional.of(4));

	@Test
	void shouldReturnAllBooks() {
		ArrayList<Book> expected = new ArrayList<>();
		expected.add(book1);

		Mockito.when(bookRepository.findAll()).thenReturn(expected);

		List<Book> response = bookService.getAllBooks();

		Mockito.verify(bookRepository, Mockito.times(1)).findAll();
		Assertions.assertEquals(expected, response);
	}

	@Test
	void shouldReturnBookById() {
		Optional<Book> expected = Optional.of(book1);

		Mockito.when(bookRepository.findById(book1.getId())).thenReturn(expected);

		Optional<Book> response = bookService.getBookById(book1.getId());

		Mockito.verify(bookRepository, Mockito.times(1)).findById(book1.getId());
		Assertions.assertEquals(expected, response);
	}

	@Test
	void shouldAddBook() {
		Book expected = book1;

		Mockito.when(bookRepository.save(book1)).thenReturn(expected);

		Book response = bookService.addBook(book1);
		Mockito.verify(bookRepository, Mockito.times(1)).save(book1);
		Assertions.assertEquals(expected, response);
	}

	@Test
	void shouldUpdateBook() {
		Book updatedBook = new Book(
				book1.getId(),
				book2.getTitle().get(),
				book2.getAuthor().get(),
				book2.getIsbn().get(),
				book2.getQuantity().get()
		);

		Mockito.when(bookRepository.save(Mockito.any())).thenReturn(updatedBook);

		Book response = bookService.updateBook(book1, book2);
		Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any());
		Assertions.assertEquals(updatedBook, response);
	}

	@Test
	void whenReceiveAnExistentBookIdShouldReturnTrue() {
		Mockito.when(bookRepository.findById(book1.getId())).thenReturn(Optional.of(book1));

		boolean response = bookService.deleteBook(book1.getId());

		Mockito.verify(bookRepository, Mockito.times(1)).findById(book1.getId());
		Mockito.verify(bookRepository, Mockito.times(1)).deleteById(book1.getId());
		
		Assertions.assertTrue(response);
	}

	@Test
	void whenReceiveANonExistentBookIdShouldReturnFalse() {
		boolean response = bookService.deleteBook(book1.getId());

		Mockito.verify(bookRepository, Mockito.times(1)).findById(book1.getId());
		Mockito.verify(bookRepository, Mockito.never()).deleteById(book1.getId());

		Assertions.assertFalse(response);
	}
}
