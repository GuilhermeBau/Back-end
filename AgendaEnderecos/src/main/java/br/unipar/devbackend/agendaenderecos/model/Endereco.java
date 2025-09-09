package br.unipar.devbackend.agendaenderecos.model;

import jakarta.persistence.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.StringReader;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString(exclude = "clientes")
@XmlRootElement(name = "xmlcep")
@XmlAccessorType(XmlAccessType.FIELD)
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;

    private LocalDateTime dataCadastro;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Clientes clientes;

    public static Endereco unmarshalFromString(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Endereco.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Endereco) unmarshaller.unmarshal(new StringReader(xml));
    }
}

//package br.unipar.devbackend.agendaenderecos.model;
//
//import jakarta.persistence.*;
//import jakarta.xml.bind.JAXBContext;
//import jakarta.xml.bind.JAXBException;
//import jakarta.xml.bind.Unmarshaller;
//import jakarta.xml.bind.annotation.XmlAccessType;
//import jakarta.xml.bind.annotation.XmlAccessorType;
//import jakarta.xml.bind.annotation.XmlRootElement;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import java.io.StringReader;
//
//@Getter
//@Setter
//@Entity
//@ToString
//@XmlRootElement(name = "xmlcep")
//@XmlAccessorType(XmlAccessType.FIELD)
//public class Endereco {
//
//    @Id //anotacao para chave primaria
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //anotacao
//    private Long id;  //chave primaria
//    private String cep;
//    private String logradouro;
//    private String complemento;
//    private String bairro;
//    private String localidade;
//    private String uf;
//
//    @ManyToOne
//    private Clientes clientes;
//
//    //metodo que converte STRING em ENDERECO
//    public static Endereco unmarshalFromString(String xml) throws JAXBException {
//        JAXBContext context = null;
//        context = JAXBContext.newInstance(Endereco.class);
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        StringReader reader = new StringReader(xml);
//        return (Endereco) unmarshaller.unmarshal(reader);
//
//    }
//}

