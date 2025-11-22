package br.unipar.devbackend.grafica.etiquetas.controller;

import br.unipar.devbackend.grafica.etiquetas.dto.EnderecoResponse;
import br.unipar.devbackend.grafica.etiquetas.service.CepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cep")
@CrossOrigin("*")
@Tag(name = "CEP", description = "Consulta de endereço via API ViaCEP")
public class CepController {

    private final CepService service;

    public CepController(CepService service) {
        this.service = service;
    }

    @GetMapping("/{cep}")
    @Operation(summary = "Consultar CEP via ViaCEP")
    public ResponseEntity<EnderecoResponse> consultar(@PathVariable String cep) {
        return ResponseEntity.ok(service.consultarCep(cep));
    }
}
