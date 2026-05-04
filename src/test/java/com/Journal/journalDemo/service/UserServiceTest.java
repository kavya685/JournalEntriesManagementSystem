//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.Journal.journalDemo.service;

import com.Journal.journalDemo.repository.UserEntryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserEntryRepository userEntryRepository;

    @Disabled
    @Test
    public void testFindUser() {
        Assertions.assertNotNull(this.userEntryRepository.findByUsername("Radha"));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource(
            {
                    "ram",
                    "shyam",
                    "vallabh"
            }
    )
    public void test(String name)
    {
        Assertions.assertNotNull(this.userEntryRepository.findByUsername(name));
    }
}
