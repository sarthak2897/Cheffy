package com.cooks.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ValidatorServiceTest {

    @Autowired
    ValidatorService validatorService;

    @Test
    public void TestVerifyPasswordsTrue(){
        assertTrue(validatorService.validatePasswordAndVerifyPassword("password_1","password_1"));
    }
    @Test
    public void TestVerifyPasswordsFalse(){
        assertFalse(validatorService.validatePasswordAndVerifyPassword("password_1","password_2"));
    }

    @Test
    public void TestValidateCardDetailsPositive(){
        assertEquals("Card validated successfully!", validatorService.validateCardDetails("4242"));
    }

    @Test
    public void TestValidateCardDetailsNegative(){
        assertEquals("The Card has been declined due to invalid card digits!", validatorService.validateCardDetails("4252"));
    }

}
