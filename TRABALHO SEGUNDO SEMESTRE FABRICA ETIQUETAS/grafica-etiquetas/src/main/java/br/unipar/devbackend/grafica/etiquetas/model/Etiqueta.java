package br.unipar.devbackend.grafica.etiquetas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "etiquetas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etiqueta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeModelo;
    private String cliente;
    private Double larguraMm;
    private Double alturaMm;
    private String material;
    private String corPrincipal;
    private Boolean ativo;
}