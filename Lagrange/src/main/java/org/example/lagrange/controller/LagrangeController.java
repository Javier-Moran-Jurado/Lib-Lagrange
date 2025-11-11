package org.example.lagrange.controller;

import org.example.lagrange.service.LagrangeService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/lagrange")
public class LagrangeController {
    private final LagrangeService lagrangeService;

    // Cache y configuración
    private final Map<String, Double> cacheInterpolaciones = new ConcurrentHashMap<>();
    private String metodoActual = "lagrange";
    private int precisionDecimales = 4;

    public LagrangeController(LagrangeService lagrangeService) {
        this.lagrangeService = lagrangeService;
    }

    // POST - Interpolación principal
    @PostMapping("/interpolar")
    public Map<String, Object> interpolar(@RequestBody InterpolacionRequest request) {
        String cacheKey = generarCacheKey(request.getPuntos(), request.getX());

        // Verificar cache
        if (cacheInterpolaciones.containsKey(cacheKey)) {
            return Map.of(
                    "resultado", cacheInterpolaciones.get(cacheKey),
                    "desdeCache", true,
                    "metodo", metodoActual
            );
        }

        // Calcular nuevo resultado
        double resultado = lagrangeService.interpolar(request.getPuntos(), request.getX());
        double resultadoRedondeado = redondear(resultado, precisionDecimales);

        // Guardar en cache
        cacheInterpolaciones.put(cacheKey, resultadoRedondeado);

        return Map.of(
                "resultado", resultadoRedondeado,
                "desdeCache", false,
                "metodo", metodoActual,
                "precision", precisionDecimales
        );
    }

    // GET - Prueba rápida
    @GetMapping("/test")
    public double testInterpolacion() {
        double[][] puntos = {{6.0, 7.0, 8.0}, {2.0, 5.0, 9.0}};
        double x = 6;
        return lagrangeService.interpolar(puntos, x);
    }

    // PUT - Actualizar configuración
    @PutMapping("/configuracion")
    public Map<String, Object> actualizarConfiguracion(@RequestBody ConfiguracionRequest request) {
        // Validar y actualizar configuración
        if (request.getMetodo() != null && !request.getMetodo().isEmpty()) {
            this.metodoActual = request.getMetodo();
        }
        if (request.getPrecision() >= 0 && request.getPrecision() <= 10) {
            this.precisionDecimales = request.getPrecision();
        }

        // Limpiar cache cuando cambia la configuración
        cacheInterpolaciones.clear();

        return Map.of(
                "mensaje", "Configuración actualizada exitosamente",
                "metodo", metodoActual,
                "precision", precisionDecimales,
                "cacheLimpiado", true
        );
    }

    // DELETE - Limpiar cache
    @DeleteMapping("/cache")
    public Map<String, Object> limpiarCache() {
        int elementosEliminados = cacheInterpolaciones.size();
        cacheInterpolaciones.clear();

        return Map.of(
                "mensaje", "Cache limpiado exitosamente",
                "elementosEliminados", elementosEliminados,
                "cacheSize", 0
        );
    }

    // GET - Estadísticas
    @GetMapping("/estadisticas")
    public Map<String, Object> obtenerEstadisticas() {
        return Map.of(
                "metodoActual", metodoActual,
                "precisionDecimales", precisionDecimales,
                "tamanoCache", cacheInterpolaciones.size()
        );
    }

    // Métodos auxiliares
    private String generarCacheKey(double[][] puntos, double x) {
        StringBuilder key = new StringBuilder();
        key.append(x).append("|");
        for (double[] fila : puntos) {
            for (double valor : fila) {
                key.append(valor).append(",");
            }
            key.append(";");
        }
        return key.toString();
    }

    private double redondear(double valor, int decimales) {
        double factor = Math.pow(10, decimales);
        return Math.round(valor * factor) / factor;
    }

    // Clases Request
    public static class InterpolacionRequest {
        private double[][] puntos;
        private double x;

        public double[][] getPuntos() { return puntos; }
        public void setPuntos(double[][] puntos) { this.puntos = puntos; }
        public double getX() { return x; }
        public void setX(double x) { this.x = x; }
    }

    public static class ConfiguracionRequest {
        private String metodo;
        private int precision;

        public String getMetodo() { return metodo; }
        public void setMetodo(String metodo) { this.metodo = metodo; }
        public int getPrecision() { return precision; }
        public void setPrecision(int precision) { this.precision = precision; }
    }
}