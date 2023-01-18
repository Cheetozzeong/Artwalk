package com.ssafy.a401.artwalk_backend.domain.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminWebController {
    @GetMapping("/admin/**")
    public String viewMapping() {
        return "forward:/index.html";
    }
}