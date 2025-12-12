package com.conpany.project;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Tester {
    public static void main(String[] args) {
        PasswordEncoder obj = new BCryptPasswordEncoder();
        System.out.println(obj.encode("password"));
        // $2a$10$zGp91DzVYBmv42B9QJ6FE.iOBKAtyiKP.KqZZmWOIx3r9iOT6nVqi
    }
}



