package com.jardelDev.estudos_ajax.service;

import com.jardelDev.estudos_ajax.domain.SocialMetaTag;
import org.springframework.stereotype.Service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Service
public class SocialMetaTagService {
	
	
	public SocialMetaTag getTwitterCardByUrl(String url){
        SocialMetaTag tag = new SocialMetaTag();
        try {
			Document doc = Jsoup.connect(url).followRedirects(false)  // Não segue redirecionamentos
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
					.referrer("http://www.google.com")
	                .timeout(5000)
	                .ignoreHttpErrors(true)
	                .get();
			
			tag.setTitle(doc.head().select("meta[name=twitter:title]").attr("content"));
			tag.setSite(doc.head().select("meta[name=twitter:site]").attr("content"));
			tag.setImage(doc.head().select("meta[name=twitter:image]").attr("content"));
			tag.setUrl(doc.head().select("meta[name=twitter:url]").attr("content"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return tag;
    }
	
	
	

    public SocialMetaTag getOpenGraphByUrl(String url){
        SocialMetaTag tag = new SocialMetaTag();
        try {
			Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .referrer("http://www.google.com")  // Define um referenciador para parecer uma navegação legítima
                    .timeout(5000)  // Define um tempo limite de 5 segundos
                    .ignoreHttpErrors(true)  
                    .get();
			tag.setTitle(doc.head().select("meta[property=og:title]").attr("content"));
			tag.setSite(doc.head().select("meta[property=og:site]").attr("content"));
			tag.setImage(doc.head().select("meta[property=og:image]").attr("content"));
			tag.setUrl(doc.head().select("meta[property=og:url]").attr("content"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return tag;
    }

}
