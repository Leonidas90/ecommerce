package com.example.ecommerce.controller;

import com.example.ecommerce.dto.opinion.OpinionDto;
import com.example.ecommerce.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/opinion")
public class OpinionController {

    @Autowired
    OpinionService service;

    @PostMapping("add")
    public String add(@RequestBody OpinionDto dto){
        return service.add(dto);
    }
}
