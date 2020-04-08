package com.ssmsun.management.util.encryption;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class EncryptedPassword {

    public String getPassword(String password, String salt) {
        StringBuilder builder = new StringBuilder();
        builder.append(salt + password + salt);
        for (int i = 0; i < 10; i++) {
            builder = builder.replace(0,builder.length(), DigestUtils.sha256Hex(builder.toString()));
        }

        return builder.toString().toUpperCase();
    }

}
