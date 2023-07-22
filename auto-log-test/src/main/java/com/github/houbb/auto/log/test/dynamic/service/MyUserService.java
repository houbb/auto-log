package com.github.houbb.auto.log.test.dynamic.service;

import org.springframework.stereotype.Service;

@Service
public class MyUserService {

    public String queryUser(String id) {
        return "user-" + id;
    }

}
