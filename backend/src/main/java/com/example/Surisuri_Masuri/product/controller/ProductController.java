package com.example.Surisuri_Masuri.product.controller;

import com.example.Surisuri_Masuri.product.model.dto.request.ProductCreateReq;
import com.example.Surisuri_Masuri.product.model.dto.request.ProductUpdateReq;
import com.example.Surisuri_Masuri.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {
    private final ProductService productService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(ProductCreateReq req) {
        return ResponseEntity.ok().body(productService.create(req));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity search(String productName) {
        return ResponseEntity.ok().body(productService.search(productName));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Integer page, Integer size) {
        return ResponseEntity.ok().body(productService.list(page, size));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(ProductUpdateReq req) {
        return ResponseEntity.ok().body(productService.update(req));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long idx) {
        return ResponseEntity.ok().body(productService.delete(idx));
    }
}
