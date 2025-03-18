package com.jardelDev.estudos_ajax.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Controller
public class NotificacaoController {

    @GetMapping("/promocao/notificao")
    public SseEmitter enviarNotificacao() throws IOException {

        SseEmitter emitter = new SseEmitter(0L);
        emitter.send("Olá Mundo");
        return emitter;
    }

}
