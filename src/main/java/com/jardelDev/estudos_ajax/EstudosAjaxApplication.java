package com.jardelDev.estudos_ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jardelDev.estudos_ajax.domain.SocialMetaTag;
import com.jardelDev.estudos_ajax.service.SocialMetaTagService;

@SpringBootApplication
public class EstudosAjaxApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(EstudosAjaxApplication.class, args);
	}

	
	@Autowired
	SocialMetaTagService service;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		SocialMetaTag og = service.getOpenGraphByUrl("https://www.pichau.com.br/cadeira-gamer-pichau-omega-s-free-fire-kelly-espuma-moldada-braco-3d-preto-e-branco-pg-ff-kelly");
		System.out.println(og.toString());
		
		SocialMetaTag twitter = service.getTwitterCardByUrl("https://www.pichau.com.br/cadeira-gamer-pichau-omega-s-free-fire-kelly-espuma-moldada-braco-3d-preto-e-branco-pg-ff-kelly");
		System.out.println(twitter.toString());
		
	}

}
