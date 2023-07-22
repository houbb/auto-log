package com.github.houbb.auto.log.test.dynamic.service;

import org.springframework.stereotype.Service;

@Service
public class MyAddressService {

    public String queryAddress(String id) {
        return "address-" + id;
    }

}
