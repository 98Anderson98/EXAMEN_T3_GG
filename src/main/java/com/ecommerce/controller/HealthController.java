package com.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping({"/", "/health"})
    public Map<String, String> health() {
        return Map.of(
                "estado", "OK",
                "app", "be-ecommerce-lariosanderson",
                "autor", "Anderson Fabrizzio Larios Tito",
                "swagger", "/swagger-ui.html");
    }
}
