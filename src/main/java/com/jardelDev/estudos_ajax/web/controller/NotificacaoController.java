package com.jardelDev.estudos_ajax.web.controller;

import com.jardelDev.estudos_ajax.Emissor;
import com.jardelDev.estudos_ajax.repository.PromocaoRepository;
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

    @GetMapping("/promocao/notificao")
    public SseEmitter enviarNotificacao() throws IOException {

        SseEmitter emitter = new SseEmitter(0L);

        Emissor emissor = new Emissor(emitter, getDtCadastroUltimaPromocao());
        emissor.getEmitter().send(emissor.getUltimaData());

        return emitter;
    }

    private LocalDateTime getDtCadastroUltimaPromocao(){
        return repository.findPromocaoComUltimaData();
    }

}
