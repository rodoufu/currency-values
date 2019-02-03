package com.github.rodoufu.currencyvalues;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class CurrencyValuesApplication {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    HttpEntity<String> entity() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        return new HttpEntity<>("parameters", headers);
    }

    public static void main(String[] args) {
        SpringApplication.run(CurrencyValuesApplication.class, args);
    }

}
