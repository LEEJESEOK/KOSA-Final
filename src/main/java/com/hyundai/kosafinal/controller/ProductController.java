package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.CategoryDTO;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.util.resources.cldr.teo.CalendarData_teo_KE;

/**
 * @author LEE JESEOK
 */
@Controller
public class ProductController {

    @GetMapping("/category/{large}_{medium}_{small}")
    public ModelAndView category(@PathVariable("large") String large, @PathVariable("medium") String medium,
                         @PathVariable("small") String small, Model model) {
        ModelAndView mav = new ModelAndView("category");
        System.out.println("category");
        CategoryDTO categoryDTO = new CategoryDTO(large, medium, small);
        model.addAttribute("category", categoryDTO);

        return mav;
    }
}
