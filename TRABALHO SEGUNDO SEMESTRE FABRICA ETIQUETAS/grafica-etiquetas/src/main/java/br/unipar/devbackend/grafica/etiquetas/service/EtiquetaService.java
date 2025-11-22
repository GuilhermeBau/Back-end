package br.unipar.devbackend.grafica.etiquetas.service;

import br.unipar.devbackend.grafica.etiquetas.model.Etiqueta;
import br.unipar.devbackend.grafica.etiquetas.repository.EtiquetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetaService {

    private final EtiquetaRepository repository;

    public EtiquetaService(EtiquetaRepository repository) {
        this.repository = repository;
    }

    public Etiqueta criar(Etiqueta etiqueta) {
        etiqueta.setId(null);
        return repository.save(etiqueta);
    }

    public List<Etiqueta> listarTodas() {
        return repository.findAll();
    }

    public Etiqueta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etiqueta não encontrada"));
    }

    public Etiqueta atualizar(Long id, Etiqueta nova) {
        Etiqueta existente = buscarPorId(id);

        existente.setNomeModelo(nova.getNomeModelo());
        existente.setCliente(nova.getCliente());
        existente.setLarguraMm(nova.getLarguraMm());
        existente.setAlturaMm(nova.getAlturaMm());
        existente.setMaterial(nova.getMaterial());
        existente.setCorPrincipal(nova.getCorPrincipal());
        existente.setAtivo(nova.getAtivo());

        return repository.save(existente);
    }

    public void deletar(Long id) {
        repository.delete(buscarPorId(id));
    }
}