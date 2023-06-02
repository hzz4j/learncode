package org.hzz.spring.service;

import org.springframework.stereotype.Service;

@Service
public class CarService {
    public String enrichName(String name) {
        return "-:: " + name + " ::-";
    }
}
