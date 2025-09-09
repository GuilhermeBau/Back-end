package br.unipar.devbackend.agendaenderecos.service;

import br.unipar.devbackend.agendaenderecos.model.Endereco;
import jakarta.xml.bind.JAXBException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViaCepService {

    public Endereco buscarCep(String cep) throws IOException, JAXBException {
        String url = "http://viacep.com.br/ws/" + cep + "/xml";

        URL urlViaCep = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlViaCep.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader leitor = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder resposta = new StringBuilder();
        String linha;
        while ((linha = leitor.readLine()) != null) {
            resposta.append(linha);
        }
        leitor.close();

        return Endereco.unmarshalFromString(resposta.toString());
    }
}


//package br.unipar.devbackend.agendaenderecos.service;
//
//import br.unipar.devbackend.agendaenderecos.model.Endereco;
//import jakarta.xml.bind.JAXBException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class ViaCepService {
//
//    public Endereco buscarCep(String cep) throws IOException, JAXBException {
//        String url = "http://viacep.com.br/ws/" + cep + "/xml"; //url viacep
//
//        URL urlViaCep = new URL(url); //url com a url do via cep
//        HttpURLConnection connection = (HttpURLConnection) urlViaCep.openConnection(); //abre conexão
//        connection.setRequestMethod("GET"); //metodo da requisição é GET
//
//        //ler o que esta vindo da requisição connection
//        BufferedReader leitor = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String linha;
//        StringBuilder resposta = new StringBuilder();
//
//        while ((linha = leitor.readLine()) != null){
//            resposta.append(linha);
//        }
//
//        leitor.close();
//        return Endereco.unmarshalFromString(resposta.toString());
//    }
//
//}