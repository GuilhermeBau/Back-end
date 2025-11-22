package br.unipar.devbackend.grafica.etiquetas.service;

import br.unipar.devbackend.grafica.etiquetas.dto.EnderecoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {

    private final RestTemplate restTemplate;

    public CepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EnderecoResponse consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, EnderecoResponse.class);
    }
}