package me.chayan.springrestapi.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {

    @RequestMapping("/")
    public String getHelloWorld() {
        return "Hello World";
    }
}
