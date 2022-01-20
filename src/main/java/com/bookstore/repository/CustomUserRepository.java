package com.bookstore.repository;


import com.bookstore.entity.CustomUser;
import com.bookstore.entity.QCustomUser;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser, Long>,
        QuerydslPredicateExecutor<CustomUser>, QuerydslBinderCustomizer<QCustomUser> {

    @Override
    default public void customize(
            QuerydslBindings bindings, QCustomUser root) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.contains(value));

    }
}
