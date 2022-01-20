package com.bookstore.service;


import com.bookstore.entity.Book;
import com.bookstore.entity.CustomUser;
import com.bookstore.repository.BookRepository;
import com.bookstore.util.BookPredicatesBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BookstoreRestServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookstoreRestService bookStoreRestService;


    @Test
    void getBooksList() {
        List<Book> bookList = getMockedBookList();

        BookPredicatesBuilder builder = new BookPredicatesBuilder();
        Mockito.when(bookRepository.findAll(builder.build())).thenReturn(bookList);

        List<Book> results = (List<Book>) bookStoreRestService.getBookList(builder.build());
        assertEquals(5, results.size());
        assertEquals(bookList, results);

    }

    @Test
    void getBookWithAuthor1() {
        List<Book> list = new ArrayList<>();
        list.add(getBook(1, "ON THE WAY", null, getCustomUser(1, "LOHGARRA"), null, BigDecimal.valueOf(10.99)));
        list.add(getBook(5, "AMSTERDAM UNDER THE STARS", null, getCustomUser(1, "LOHGARRA"), null, BigDecimal.valueOf(19.99)));

        BookPredicatesBuilder builder = new BookPredicatesBuilder().with("author_id", ":", 1);
        Mockito.when(bookRepository.findAll(builder.build())).thenReturn(list);

        List<Book> results = (List<Book>) bookStoreRestService.getBookList(builder.build());
        assertEquals(2, results.size());
        assertEquals("LOHGARRA", results.get(0).getAuthor().getAuthorPseudonmy());

    }

    @Test
    void getBookWithAuthor1AndTitle() {
        List<Book> list = new ArrayList<>();
        list.add(getBook(1, "ON THE WAY", null, getCustomUser(1, "LOHGARRA"), null, BigDecimal.valueOf(10.99)));

        BookPredicatesBuilder builder = new BookPredicatesBuilder().with("author_id", ":", 1).with("title", ":", "ON THE WAY");
        Mockito.when(bookRepository.findAll(builder.build())).thenReturn(list);

        List<Book> results = (List<Book>) bookStoreRestService.getBookList(builder.build());
        assertEquals(1, results.size());
        assertEquals("LOHGARRA", results.get(0).getAuthor().getAuthorPseudonmy());

    }

    @Test
    void getBookWithTitle() {
        List<Book> list = new ArrayList<>();
        list.add(getBook(1, "ON THE WAY", null, getCustomUser(1, "LOHGARRA"), null, BigDecimal.valueOf(10.99)));

        BookPredicatesBuilder builder = new BookPredicatesBuilder().with("title", ":", "ON THE WAY");
        Mockito.when(bookRepository.findAll(builder.build())).thenReturn(list);

        List<Book> results = (List<Book>) bookStoreRestService.getBookList(builder.build());
        assertEquals(1, results.size());
        assertEquals(results.get(0).getTitle(), "ON THE WAY");

    }

    @Test
    void getBookWithDescription() {
        List<Book> list = new ArrayList<>();

        BookPredicatesBuilder builder = new BookPredicatesBuilder().with("description", ":", "ON THE WAY DESCRIPTION");
        Mockito.when(bookRepository.findAll(builder.build())).thenReturn(list);

        List<Book> results = (List<Book>) bookStoreRestService.getBookList(builder.build());
        assertTrue(results.isEmpty());

    }

    @Test
    void getBookWithPrice() {
        List<Book> list = new ArrayList<>();
        list.add(getBook(4, "HUNGER GAMES", null, getCustomUser(2, "PINKFLOYD"), null, BigDecimal.valueOf(30.50)));

        BookPredicatesBuilder builder = new BookPredicatesBuilder().with("price", ">", 20);
        Mockito.when(bookRepository.findAll(builder.build())).thenReturn(list);

        List<Book> results = (List<Book>) bookStoreRestService.getBookList(builder.build());
        assertEquals(1, results.size());
        assertEquals("HUNGER GAMES", results.get(0).getTitle());

    }

    @Test
    void insert() {
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(getBook(6, "MY LIFE", null, getCustomUser(1, "LOHGARRA"), null, BigDecimal.valueOf(10.99)));
        bookStoreRestService.insert(getBook(6, "MY LIFE", null, getCustomUser(1, "LOHGARRA"), null, BigDecimal.valueOf(10.99)));

        List<Book> bookList = getMockedBookList();
        bookList.add(getBook(6, "MY LIFE", null, getCustomUser(1, "LOHGARRA"), null, BigDecimal.valueOf(10.99)));
        BookPredicatesBuilder builder = new BookPredicatesBuilder();
        Mockito.when(bookRepository.findAll(builder.build())).thenReturn(bookList);

        List<Book> results = (List<Book>) bookStoreRestService.getBookList(builder.build());
        assertEquals(6, results.size());
        assertEquals(bookList, results);
    }

    @Test
    void update() {
        Optional<Book> optBook = Optional.of(getBook(6, "MY LIFE", null, getCustomUser(1, "LOHGARRA"), null, BigDecimal.valueOf(10.99)));

        Mockito.when(bookRepository.findById(Mockito.any())).thenReturn(optBook);
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(getBook(6, "MY LIFE", null, getCustomUser(2, "PINKFLOYD"), null, BigDecimal.valueOf(10.99)));
        bookStoreRestService.update(getBook(6, "MY LIFE", null, getCustomUser(2, "PINKFLOYD"), null, BigDecimal.valueOf(10.99)));

        List<Book> bookList = getMockedBookList();
        bookList.add(getBook(6, "MY LIFE", null, getCustomUser(2, "PINKFLOYD"), null, BigDecimal.valueOf(10.99)));
        BookPredicatesBuilder builder = new BookPredicatesBuilder();
        Mockito.when(bookRepository.findAll(builder.build())).thenReturn(bookList);

        List<Book> results = (List<Book>) bookStoreRestService.getBookList(builder.build());
        assertEquals(6, results.size());
        assertEquals(bookList, results);
    }

    private List<Book> getMockedBookList() {
        List<Book> mockedBookList = new ArrayList<>();
        mockedBookList.add(getBook(1, "ON THE WAY", null, getCustomUser(1, "LOHGARRA"), null, BigDecimal.valueOf(10.99)));
        mockedBookList.add(getBook(2, "SHARD AND THE MOON", null, getCustomUser(3, "ANGEL"), null, BigDecimal.valueOf(5.25)));
        mockedBookList.add(getBook(3, "DARKLY CITIES", null, getCustomUser(2, "PINKFLOYD"), null, BigDecimal.valueOf(2.99)));
        mockedBookList.add(getBook(4, "HUNGER GAMES", null, getCustomUser(2, "PINKFLOYD"), null, BigDecimal.valueOf(30.50)));
        mockedBookList.add(getBook(5, "AMSTERDAM UNDER THE STARS", null, getCustomUser(1, "LOHGARRA"), null, BigDecimal.valueOf(19.99)));
        return mockedBookList;
    }

    private CustomUser getCustomUser(int id, String authorPseudonmy) {
        CustomUser customUser = new CustomUser();
        customUser.setId(id);
        customUser.setAuthorPseudonmy(authorPseudonmy);
        return customUser;
    }

    private Book getBook(int id, String title, String description, CustomUser author, String coverImage, BigDecimal price) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setDescription(description);
        book.setAuthor(author);
        book.setCoverImage(coverImage);
        book.setPrice(price);
        return book;
    }

}
