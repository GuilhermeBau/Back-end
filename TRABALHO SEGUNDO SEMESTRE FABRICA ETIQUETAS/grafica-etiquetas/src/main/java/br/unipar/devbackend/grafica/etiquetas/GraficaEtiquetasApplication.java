package br.unipar.devbackend.grafica.etiquetas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GraficaEtiquetasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraficaEtiquetasApplication.class, args);
    }
}
