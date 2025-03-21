package com.jardelDev.estudos_ajax.web.controller;

import com.jardelDev.estudos_ajax.domain.Categoria;
import com.jardelDev.estudos_ajax.domain.Promocao;
import com.jardelDev.estudos_ajax.dto.PromocaoDTO;
import com.jardelDev.estudos_ajax.repository.CategoriaRepository;
import com.jardelDev.estudos_ajax.repository.PromocaoRepository;
import com.jardelDev.estudos_ajax.service.PromocaoDataTablesService;
import groovy.util.logging.Log;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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

    // ============ DataTables =====================

    @GetMapping("/tabela")
    public String showTabela(){
        return "promo-datatables";
    }

    @GetMapping("/datatables/server")
    public ResponseEntity<?> datatables(HttpServletRequest request){
        Map<String, Object> data = new PromocaoDataTablesService().execute(promocaoRepository, request);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> excluirPromocao(@PathVariable("id") Long id){
        promocaoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<?> preEditarPromocao(@PathVariable("id") Long id){
        Promocao promo = promocaoRepository.findById(id).get();
        return ResponseEntity.ok(promo);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editarPromocao(@Valid PromocaoDTO dto, BindingResult result){

        if (result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()){
                errors.put(error.getField(),error.getDefaultMessage());
            }
            return ResponseEntity.unprocessableEntity().body(errors);
        }

        Promocao promo = promocaoRepository.findById(dto.getId()).get();
        promo.setCategoria(dto.getCategoria());
        promo.setDescricao(dto.getDescricao());
        promo.setLinkImagem(dto.getLinkImagem());
        promo.setPreco(dto.getPreco());
        promo.setTitulo(dto.getTitulo());

        promocaoRepository.save(promo);

        return ResponseEntity.ok().build();
    }

    // ============ adicionando autocomplete para busca de sites =====================

    @GetMapping("/site")
    public ResponseEntity<?> autocompleteByTermo(@RequestParam("termo") String termo){
        List<String> sites = promocaoRepository.findSitesByTermo(termo);
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/site/list")
    public String listarPorSite(@RequestParam("site") String site, ModelMap model){
        Sort sort = Sort.by("dtCadastro").descending();
        PageRequest pageRequest = PageRequest.of(0, 8, sort);
        model.addAttribute("promocoes", promocaoRepository.findBySite(site, pageRequest));
        return "promo-card";
    }


    // ============ adicionando likes ===============================================

    @PostMapping("/like/{id}")
    public ResponseEntity<?> adicionarLikes(@PathVariable("id") Long id){
        promocaoRepository.updateSomarLikes(id);
        int likes = promocaoRepository.findLikesByID(id);
        return ResponseEntity.ok(likes);
    }


    // ============ lista de ofertas ================================================

    @GetMapping("/list")
    public String listarOfertas(ModelMap model){
        Sort sort = Sort.by("dtCadastro").descending();
        PageRequest pageRequest = PageRequest.of(0, 8, sort);
        model.addAttribute("promocoes", promocaoRepository.findAll(pageRequest));
        return "promo-list";
    }

    @GetMapping("/list/ajax")
    public String listarCards(@RequestParam(name = "page", defaultValue = "1") int page,
                              @RequestParam(name = "site", defaultValue = "") String site,
                              ModelMap model) {
        Sort sort = Sort.by("dtCadastro").descending();
        PageRequest pageRequest = PageRequest.of(page, 8, sort);
        if(site.isEmpty()){
            model.addAttribute("promocoes", promocaoRepository.findAll(pageRequest));
        }else {
            model.addAttribute("promocoes", promocaoRepository.findBySite(site,pageRequest));
        }
        return "promo-card";
    }

    // ============ adição de ofertas ==============================================

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
