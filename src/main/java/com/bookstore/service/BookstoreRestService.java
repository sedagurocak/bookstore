package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.repository.BookRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookstoreRestService {

    private final BookRepository bookRepository;

    public Iterable<Book> getBookList(BooleanExpression predicate) { return bookRepository.findAll(predicate);}

    public Book insert(Book bookRequest) {
        return bookRepository.save(bookRequest);
    }

    public Book update(Book bookRequest) {
        return bookRepository.findById(bookRequest.getId()).map(data -> {
            Book book = null;
            data.setId(bookRequest.getId());
            data.setPrice(bookRequest.getPrice());
            data.setDescription(bookRequest.getDescription());
            data.setAuthor(bookRequest.getAuthor());
            data.setCoverImage(bookRequest.getCoverImage());
            data.setTitle(bookRequest.getTitle());

            book = bookRepository.save(data);
            return book;
        }).orElseThrow(() -> new ResourceNotFoundException("Book Id Not found. Id: " + bookRequest.getId()));
    }
}
