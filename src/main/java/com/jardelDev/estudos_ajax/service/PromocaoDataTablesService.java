package com.jardelDev.estudos_ajax.service;

import com.jardelDev.estudos_ajax.domain.Promocao;
import com.jardelDev.estudos_ajax.repository.PromocaoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class PromocaoDataTablesService {

    private String[] cols = {
            "id", "titulo", "site", "linkPromocao", "descricao", "linkImagem", "preco", "likes", "dtCadastro", "categoria"
    };

    public Map<String, Object> execute(PromocaoRepository repository, HttpServletRequest request) {

        int start = Integer.parseInt(request.getParameter("start"));
        int lenght = Integer.parseInt(request.getParameter("length"));
        int draw = Integer.parseInt(request.getParameter("draw"));


        int current = currentPage(start, lenght);
        String column = columnName(request);
        Sort.Direction direction = orderBy(request);
        String search = searchBy(request);

        Pageable pageable = PageRequest.of(current, lenght, direction, column);

        Page<Promocao> page = queryBy(search, repository, pageable);

        Map<String, Object> json = new LinkedHashMap<>();
        json.put("draw", draw);
        json.put("recordsTotal", page.getTotalElements());
        json.put("recordsFiltered", page.getTotalElements());
        json.put("data", page.getContent());

        return json;
    }


    private Page<Promocao> queryBy(String search, PromocaoRepository repository, Pageable pageable) {
        if (search.isEmpty()){
            return repository.findAll(pageable);
        }
        return repository.findByTituloOrSiteOrCategoria(search, pageable);
    }

    private String searchBy(HttpServletRequest request) {

        return request.getParameter("search[value]").isEmpty()
                ? ""
                : request.getParameter("search[value]");
    }

    private Sort.Direction orderBy(HttpServletRequest request) {

        String order = request.getParameter("order[0][dir]");
        Sort.Direction sort = Sort.Direction.ASC;
        if (order.equalsIgnoreCase("desc")) {
            sort = Sort.Direction.DESC;
        }
        return sort;
    }

    private String columnName(HttpServletRequest request) {
        int col = Integer.parseInt(request.getParameter("order[0][column]"));

        return cols[col];
    }

    private int currentPage(int start, int lenght) {

        return start / lenght;
    }
}
