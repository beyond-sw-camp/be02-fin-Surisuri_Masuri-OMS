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
    public ResponseEntity create(@RequestBody PostCreateContainerReq postCreateContainerReq) {
        BaseResponse baseResponse = containerService.create(postCreateContainerReq);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createproduct")
    public ResponseEntity createContainerProduct(@RequestBody ContainerCreateProductReq req) {
        return ResponseEntity.ok().body(containerService.createContainerProduct(req));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Integer page, Integer size) {
        BaseResponse baseResponse = containerService.list(page, size);
        return ResponseEntity.ok().body(baseResponse);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/singlestock")
    public ResponseEntity singleStockProduct(Integer containerIdx) {
        BaseResponse baseResponse = containerService.singleStockProduct(containerIdx);
        return ResponseEntity.ok().body(baseResponse);
    }

    @PostMapping("/discardExpiredFoodProducts")
    public ResponseEntity<BaseResponse<List<ContainerStockDto>>> discardExpiredFoodProducts() {
        BaseResponse<List<ContainerStockDto>> response = containerService.discardExpiredFoodProducts();
        return ResponseEntity.ok().body(response);
    }

}
