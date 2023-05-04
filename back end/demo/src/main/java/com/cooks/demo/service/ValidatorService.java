package com.cooks.demo.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ValidatorService {

    //Static map having card's last 4 digits as key and cvv number as value
    Map<String,String> cardInfo = Map.of("4242", "484");
//                                            ,"5556","145","4444","541", "3222","467", "8210","587", "5100","879", "10005","345", "98431","856", "1117","531");
            //, "9424","112");

    /**
     * validates passwords by comparing for equality
     * @param password
     * @param verifyPassword
     * @return
     */
    public boolean validatePasswordAndVerifyPassword(String password, String verifyPassword){
        if(password.equals(verifyPassword))
            return true;
         else
             return false;
    }

    public String validateCardDetails(String cardDigits){
            if(cardInfo.containsKey(cardDigits)){
                return "Card validated successfully!";
            }else{
                return "The Card has been declined due to invalid card digits!";
            }
    }


}
