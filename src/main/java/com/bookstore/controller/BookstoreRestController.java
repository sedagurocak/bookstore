package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.exception.ServerException;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.BookstoreRestService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookstoreRestController {

    private final BookRepository bookRepository;

    private final BookstoreRestService bookStoreRestService;


    @GetMapping
    public Iterable<Book> getBookList(
            @QuerydslPredicate(root = Book.class) Predicate predicate) {
        BooleanBuilder builder = new BooleanBuilder();
        return bookRepository.findAll(builder.and(predicate));
    }


    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("bookId") long bookId){
      return bookRepository.findById(bookId)
              .map(question -> {
                  bookRepository.delete(question);
                  return ResponseEntity.ok().build();
              }).orElseThrow(() -> new ResourceNotFoundException("Book does not exist"));
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Book> insert(@RequestBody Book newBook) {
        Book book = bookStoreRestService.insert(newBook);
        if (book == null) {
            throw new ServerException();
        } else {
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Book> update(@RequestBody Book updateBook) {
        Book book = bookStoreRestService.update(updateBook);
        if (book == null) {
            throw new ServerException();
        } else {
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        }
    }

}
