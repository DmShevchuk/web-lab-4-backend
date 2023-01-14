package ru.weblab4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/{path:^(?!api)\\w*}")
    public String sendStaticContent(){
        return "/index.html";
    }
}
