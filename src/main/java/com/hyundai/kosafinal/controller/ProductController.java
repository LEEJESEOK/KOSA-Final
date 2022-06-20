package com.hyundai.kosafinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LEE JESEOK
 */
@Controller
public class ProductController {

    @GetMapping("/category")
    public String category() {
        System.out.println("category");
        return "category";
    }
}
