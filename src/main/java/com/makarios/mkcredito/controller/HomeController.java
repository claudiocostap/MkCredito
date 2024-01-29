package com.makarios.mkcredito.controller;

import com.makarios.mkcredito.model.Pessoa;
import com.makarios.mkcredito.repository.PessoaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private PessoaRepository pessoaRepository;


    @RequestMapping({"/"})
    String index(HttpSession session) {
        session.setAttribute("mySessionAttribute", "someValue");
        return "home";
    }

    @RequestMapping("/pessoas")
    @GetMapping
    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

}
