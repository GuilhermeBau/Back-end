package br.unipar.devbackend.grafica.etiquetas.controller;

import br.unipar.devbackend.grafica.etiquetas.model.Etiqueta;
import br.unipar.devbackend.grafica.etiquetas.service.EtiquetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/etiquetas")
@CrossOrigin("*")
@Tag(name = "Etiquetas", description = "CRUD de etiquetas da gráfica")
public class EtiquetaController {

    private final EtiquetaService service;

    public EtiquetaController(EtiquetaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar etiqueta")
    public ResponseEntity<Etiqueta> criar(@RequestBody Etiqueta e) {
        Etiqueta criada = service.criar(e);
        return ResponseEntity.created(URI.create("/etiquetas/" + criada.getId()))
                .body(criada);
    }

    @GetMapping
    @Operation(summary = "Listar etiquetas")
    public ResponseEntity<List<Etiqueta>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar etiqueta por ID")
    public ResponseEntity<Etiqueta> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar etiqueta")
    public ResponseEntity<Etiqueta> atualizar(@PathVariable Long id,
                                              @RequestBody Etiqueta nova) {
        return ResponseEntity.ok(service.atualizar(id, nova));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar etiqueta")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}