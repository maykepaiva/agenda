package br.com.project.x.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acesso")
public class AcessoController {

    @GetMapping("/admin")
    public String AcessoAdmin() {
        return "Acesso permitido admin!!!";
    }
    @GetMapping("/user")
    public String AcessoUser() {
        return "Acesso permitido usuario!!!";
    }
}
