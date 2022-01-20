package com.bookstore.service;

import com.bookstore.entity.CustomUser;
import com.bookstore.repository.CustomUserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserRestService {

    private final CustomUserRepository customUserRepository;

    public Iterable<CustomUser> getCustomUserList(BooleanExpression predicate) { return customUserRepository.findAll(predicate);}
    public CustomUser insert(CustomUser newUser) {
        return customUserRepository.save(newUser);
    }
}
