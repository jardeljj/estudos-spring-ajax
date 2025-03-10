package com.jardelDev.estudos_ajax.web.controller;

import com.jardelDev.estudos_ajax.domain.Categoria;
import com.jardelDev.estudos_ajax.domain.Promocao;
import com.jardelDev.estudos_ajax.repository.CategoriaRepository;
import com.jardelDev.estudos_ajax.repository.PromocaoRepository;
import groovy.util.logging.Log;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {

    private static Logger log = LoggerFactory.getLogger(PromocaoController.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PromocaoRepository promocaoRepository;
    // ============ lista de ofertas =====================

    @GetMapping("/list")
    public String listarOfertas(ModelMap model){
        Sort sort = Sort.by("dtCadastro").descending();
        model.addAttribute("promocoes", promocaoRepository.findAll(sort));
        return "promo-list";
    }



    // ============ adição de ofertas =====================

    @PostMapping("/save")
    public ResponseEntity<?> salvarPromocao(@Valid Promocao promocao, BindingResult result) {

        if (result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }
            return ResponseEntity.unprocessableEntity().body(errors);
        }


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
