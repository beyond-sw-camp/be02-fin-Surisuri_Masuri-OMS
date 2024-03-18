package com.example.Surisuri_Masuri.cart.controller;

import com.example.Surisuri_Masuri.cart.model.dto.request.CartCreateReq;
import com.example.Surisuri_Masuri.cart.service.CartService;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;

    @RequestMapping(method = RequestMethod.POST, value = "/addcart")
    public ResponseEntity addCart(@AuthenticationPrincipal User user,
                                  @RequestBody CartCreateReq req) {

        return ResponseEntity.ok().body(cartService.addCart(user, req));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Long idx, Integer page, Integer size) {

        return ResponseEntity.ok().body(cartService.list(idx, page, size));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long idx, Long productIdx) {

        return ResponseEntity.ok().body(cartService.delete(idx, productIdx));
    }
}
