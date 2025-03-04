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
		String url = "https://www.magazinevoce.com.br/magazinepromobitoficial/smartphone-samsung-galaxy-a06-128gb-4gb-ram-azul-escuro-67-cam-dupla-selfie-8mp/p/238657700/te/ga06/";

		//System.out.println("🔎 Testando Open Graph diretamente...");
		//SocialMetaTag tag = service.getSocialMetaTagByUrl(url);
		//System.out.println(tag.toString());
	}


}
