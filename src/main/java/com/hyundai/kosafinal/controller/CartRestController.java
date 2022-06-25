package com.hyundai.kosafinal.controller;

import com.hyundai.kosafinal.domain.CartDTO;
import com.hyundai.kosafinal.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/cart")
public class CartRestController {

    @Autowired
    private CartService service;

    @PostMapping("")
    public ResponseEntity<CartDTO> insertCart(@RequestBody CartDTO dto) {
        ResponseEntity<CartDTO> entry = null;

        service.insert(dto);
        log.info("INSERT CART : " + dto);

        entry = new ResponseEntity<CartDTO>(dto, HttpStatus.OK);

        return entry;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Boolean> updateCart(@RequestBody CartDTO dto) {
        ResponseEntity<Boolean> entry = null;

        service.update(dto);
        log.info("UPDATE CART : " + dto);

        entry = new ResponseEntity<Boolean>(true, HttpStatus.OK);

        return entry;
    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteCart(int id, String userEmail) {
        ResponseEntity<Boolean> entry = null;

        service.delete(id, userEmail);
        log.info("DELETE CART : " + id);

        entry = new ResponseEntity<Boolean>(true, HttpStatus.OK);

        return entry;
    }
}
