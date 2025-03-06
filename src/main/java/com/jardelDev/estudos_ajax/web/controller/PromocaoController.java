package com.jardelDev.estudos_ajax.web.controller;

import com.jardelDev.estudos_ajax.domain.Categoria;
import com.jardelDev.estudos_ajax.domain.Promocao;
import com.jardelDev.estudos_ajax.repository.CategoriaRepository;
import com.jardelDev.estudos_ajax.repository.PromocaoRepository;
import groovy.util.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {

    private static Logger log = LoggerFactory.getLogger(PromocaoController.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PromocaoRepository promocaoRepository;

    @PostMapping("/save")
    public ResponseEntity<Promocao> salvarPromocao(Promocao promocao) {

        log.info("Promocao {}", promocao.toString());
        promocao.setDtCadastro(LocalDateTime.now());
        promocaoRepository.save(promocao);
        return ResponseEntity.ok().build();
    }


    @ModelAttribute("categorias")
    private List<Categoria> getCategorias(){
        return categoriaRepository.findAll();
    }


    @GetMapping("/add")
    public String abrirCadastro(){

        return "promo-add";
    }

}
