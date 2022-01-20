package com.bookstore.service;

import com.bookstore.entity.CustomUser;
import com.bookstore.repository.CustomUserRepository;
import com.bookstore.util.BookPredicatesBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CustomUserRestServiceTest {

    @Mock
    CustomUserRepository customUserRepository;

    @InjectMocks
    CustomUserRestService customUserRestService;

    @Test
    void insert() {
        Mockito.when(customUserRepository.save(Mockito.any())).thenReturn(getCustomUser(10, "SEDOS"));
        customUserRestService.insert(getCustomUser(10, "SEDOS"));

        List<CustomUser> customUserList = getMockedCustomUserList();
        customUserList.add(getCustomUser(10, "SEDOS"));
        BookPredicatesBuilder builder = new BookPredicatesBuilder();
        Mockito.when(customUserRepository.findAll(builder.build())).thenReturn(customUserList);

        List<CustomUser> results = (List<CustomUser>) customUserRestService.getCustomUserList(builder.build());
        assertEquals(4, results.size());
        assertEquals(customUserList, results);
    }

    private List<CustomUser> getMockedCustomUserList(){
        List<CustomUser> mockedCustomUserList = new ArrayList<>();
        mockedCustomUserList.add(getCustomUser(1, "LOHGARRA"));
        mockedCustomUserList.add(getCustomUser(2, "PINKFLOYD"));
        mockedCustomUserList.add(getCustomUser(3, "ANGEL"));
        return  mockedCustomUserList;
    }

    private CustomUser getCustomUser(int id, String authorPseudonmy) {
        CustomUser customUser = new CustomUser();
        customUser.setId(id);
        customUser.setAuthorPseudonmy(authorPseudonmy);
        return customUser;
    }

}