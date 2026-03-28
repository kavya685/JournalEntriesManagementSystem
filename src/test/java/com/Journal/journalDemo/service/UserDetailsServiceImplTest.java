package com.Journal.journalDemo.service;

import com.Journal.journalDemo.entity.User;
import com.Journal.journalDemo.repository.UserEntryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserEntryRepository userEntryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameTest() {
        when(userEntryRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(User.builder()
                        .username("ram")
                        .password("arerfsf")
                        .roles(new ArrayList<>())
                        .build());

        UserDetails user = userDetailsService.loadUserByUsername("ram");

        Assertions.assertNotNull(user);
    }
}
