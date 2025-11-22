package br.unipar.devbackend.grafica.etiquetas.repository;

import br.unipar.devbackend.grafica.etiquetas.model.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {
}