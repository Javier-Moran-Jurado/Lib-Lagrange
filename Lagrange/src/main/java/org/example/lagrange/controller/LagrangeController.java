package org.example.lagrange.controller;

import org.example.lagrange.service.LagrangeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lagrange")
public class LagrangeController {
    private final LagrangeService lagrangeService;

    public LagrangeController(LagrangeService lagrangeService) {
        this.lagrangeService = lagrangeService;
    }

    // Endpoint POST (para clientes)
    @PostMapping("/interpolar")
    public double interpolar(@RequestBody InterpolacionRequest request) {
        return lagrangeService.interpolar(request.getPuntos(), request.getX());
    }

    // Endpoint GET para probar fácilmente desde navegador
    @GetMapping("/test")
    public double testInterpolacion() {
        double[][] puntos = {{1.0, 2.0, 3.0}, {1.0, 4.0, 9.0}};
        double x = 2.5;
        return lagrangeService.interpolar(puntos, x);
    }

    public static class InterpolacionRequest {
        private double[][] puntos;
        private double x;

        // Getters y Setters
        public double[][] getPuntos() {
            return puntos;
        }

        public void setPuntos(double[][] puntos) {
            this.puntos = puntos;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }
    }
}