package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.CategoryDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author LEE JESEOK
 */
@Controller
public class ProductController {

    @GetMapping(value = "/category/{large}_{medium}_{small}_{pageNum}_{brand}_{color}_{size}")
    public ModelAndView category(@PathVariable("large") String large, @PathVariable("medium") String medium, @PathVariable("small") String small,
                                 @PathVariable(value = "pageNum", required = false) Integer pageNum, @PathVariable("brand") String brand,
                                 @PathVariable("color") String color, @PathVariable("size") String size,
                                 @RequestParam(value = "minPrice", required = false) Integer minPrice, @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                 @RequestParam(value = "sortType", required = false) Integer sortType, Model model) {
        ModelAndView mav = new ModelAndView("category");
        System.out.println("category");
        CategoryDTO categoryDTO = new CategoryDTO(large, medium, small);
        model.addAttribute("category", categoryDTO);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("brand", brand);
        model.addAttribute("color", color);
        model.addAttribute("size", size);


        return mav;
    }
}
