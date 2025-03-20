package com.jardelDev.estudos_ajax.service;

import com.jardelDev.estudos_ajax.Emissor;
import com.jardelDev.estudos_ajax.repository.PromocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificacaoService {

    @Autowired
    private PromocaoRepository repository;

    private CopyOnWriteArrayList<Emissor> emissores =  new CopyOnWriteArrayList<>();

    public void onOpen(Emissor emissor) throws IOException {
        emissor.getEmitter().send(SseEmitter.event()
                .data(" ")
                .id(emissor.getId()));
    }

    public void addEmissor(Emissor emissor){
        this.emissores.add(emissor);
    }

    public void removeEmissor (Emissor emissor){
        this.emissores.remove(emissor);
    }

    public CopyOnWriteArrayList<Emissor> getEmissores() {
        return emissores;
    }
}
