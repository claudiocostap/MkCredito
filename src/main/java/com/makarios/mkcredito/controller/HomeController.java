package com.makarios.mkcredito.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"/"})
    String index(HttpSession session) {
        session.setAttribute("mySessionAttribute", "someValue");
        return "home";
    }
}
