package com.bookstore.model;

import com.bookstore.entity.Book;
import lombok.Data;

import java.util.List;

@Data
public class Bookstore {
    private List<Book> books;
}
