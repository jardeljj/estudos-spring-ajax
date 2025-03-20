package com.jardelDev.estudos_ajax.web.controller;

import com.jardelDev.estudos_ajax.Emissor;
import com.jardelDev.estudos_ajax.repository.PromocaoRepository;
import com.jardelDev.estudos_ajax.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class NotificacaoController {

    @Autowired
    private PromocaoRepository repository;

    @Autowired
    private NotificacaoService service;

    @GetMapping("/promocao/notificao")
    public SseEmitter enviarNotificacao() throws IOException {

        SseEmitter emitter = new SseEmitter(0L);

        Emissor emissor = new Emissor(emitter, getDtCadastroUltimaPromocao());
        service.onOpen(emissor);
        service.addEmissor(emissor);

        emissor.getEmitter().onCompletion(() -> service.removeEmissor(emissor));

        System.out.println("> tamanho depois de adicionado: " + service.getEmissores().size());

        return emissor.getEmitter();
    }

    private LocalDateTime getDtCadastroUltimaPromocao(){
        return repository.findPromocaoComUltimaData();
    }

}
