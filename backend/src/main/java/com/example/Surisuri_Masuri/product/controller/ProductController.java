package com.example.Surisuri_Masuri.product.controller;

import com.example.Surisuri_Masuri.product.model.dto.request.ProductCreateReq;
import com.example.Surisuri_Masuri.product.model.dto.request.ProductUpdateReq;
import com.example.Surisuri_Masuri.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {
    private final ProductService productService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@RequestBody ProductCreateReq req) {
        return ResponseEntity.ok().body(productService.create(req));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ResponseEntity search(@RequestParam String productName,
                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(productService.search(productName,page,size));
    }

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(required = false, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(productService.list(page, size));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity update(@RequestBody ProductUpdateReq req) {
        return ResponseEntity.ok().body(productService.update(req));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long idx) {
        return ResponseEntity.ok().body(productService.delete(idx));
    }
}
