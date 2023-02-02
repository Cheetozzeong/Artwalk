package com.ssafy.a401.artwalk_backend.domain.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping("admin")
@RestController
@RequiredArgsConstructor
public class AdminTestController {

    @GetMapping("/view")
    public String viewMapping() {
        return "forward:/index.html";
    }
}