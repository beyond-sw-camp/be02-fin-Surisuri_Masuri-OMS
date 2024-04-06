package com.example.Surisuri_Masuri.container.controller;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.container.model.request.ContainerCreateProductReq;
import com.example.Surisuri_Masuri.container.model.request.ContainerStockDto;
import com.example.Surisuri_Masuri.container.model.request.PostCreateContainerReq;
import com.example.Surisuri_Masuri.container.service.ContainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/container")
@CrossOrigin("*")
public class ContainerController {

    private final ContainerService containerService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@RequestHeader(value = "AccessToken") String token, @RequestBody PostCreateContainerReq postCreateContainerReq) {
        BaseResponse baseResponse = containerService.create(token,postCreateContainerReq);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createproduct")
    public ResponseEntity createContainerProduct(@RequestHeader(value = "AccessToken") String token, @RequestBody ContainerCreateProductReq req) {
        return ResponseEntity.ok().body(containerService.createContainerProduct(token,req));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(@RequestHeader(value = "AccessToken") String token, Integer page, Integer size) {
        BaseResponse baseResponse = containerService.list(token,page, size);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/singlestock")
    public ResponseEntity singleStockProduct(@RequestHeader(value = "AccessToken") String token,Integer containerIdx, Integer page, Integer size) {
        BaseResponse baseResponse = containerService.singleStockProduct(token,containerIdx, page, size);
        return ResponseEntity.ok().body(baseResponse);
    }

}
