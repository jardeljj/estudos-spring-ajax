package com.jardelDev.estudos_ajax.web.controller;

import com.jardelDev.estudos_ajax.domain.Categoria;
import com.jardelDev.estudos_ajax.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @ModelAttribute("categorias ")
    private List<Categoria> getCategorias(){
        return categoriaRepository.findAll();
    }


    @GetMapping("/add")
    public String abrirCadastro(){

        return "promo-add";
    }

}
