// Define que esta classe é uma entidade JPA, ou seja, será mapeada para uma tabela no banco de dados
package br.unipar.devbackend.agendaenderecos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Anotações do Lombok para gerar automaticamente os getters e setters
@Getter
@Setter
@Entity
@ToString
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "clientes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    public void adicionarEndereco(Endereco endereco) {
        enderecos.add(endereco);
        endereco.setClientes(this);
    }

    public void removerEndereco(Endereco endereco) {
        enderecos.remove(endereco);
        endereco.setClientes(null);
    }
}

//    // Anotação que indica que este campo é a chave primária da entidade
//    @Id
//    // Configura a estratégia de geração automática de valores para a chave primária
//    // IDENTITY utiliza auto incremento do banco de dados
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;  // Campo para armazenar o identificador único do cliente
//    private String nome; // Campo para armazenar o nome do cliente
//    private String email; // Campo para armazenar o email do cliente
//    private Date dataNascimento; // Campo para armazenar a data de nascimento do cliente
//
//    @OneToMany(mappedBy = "clientes", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Endereco> enderecos = new ArrayList<>();
