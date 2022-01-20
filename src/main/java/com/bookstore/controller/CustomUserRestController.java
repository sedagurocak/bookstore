package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.entity.CustomUser;
import com.bookstore.exception.ServerException;
import com.bookstore.repository.CustomUserRepository;
import com.bookstore.service.CustomUserRestService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CustomUserRestController {

    private final CustomUserRepository customUserRepository;

    private final CustomUserRestService customUserRestService;


    @GetMapping
    public Iterable<CustomUser> getUserList(
            @QuerydslPredicate(root = CustomUser.class) Predicate predicate) {
        BooleanBuilder builder = new BooleanBuilder();
        return customUserRepository.findAll(builder.and(predicate));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CustomUser> insert(@RequestBody CustomUser newUser) {
        CustomUser user = customUserRestService.insert(newUser);
        if (user == null) {
            throw new ServerException();
        } else {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }
}
