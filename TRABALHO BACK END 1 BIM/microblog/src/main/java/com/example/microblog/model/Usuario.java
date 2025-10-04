// Define o pacote para as classes de modelo (entidades).
package com.example.microblog.model;

// Importa as anotações necessárias do Jakarta Persistence (JPA) e do Lombok.
import jakarta.persistence.*;
import lombok.Data;

/**
 * @Data: Uma anotação "guarda-chuva" do Lombok que gera automaticamente
 * o código que sempre escrevemos: getters, setters, toString(), equals() e hashCode().
 * Deixa nossa classe de entidade focada apenas nos dados e relacionamentos.
 */
@Data
/**
 * @Entity: Marca esta classe como uma entidade JPA, o que significa que ela
 * será mapeada para uma tabela no banco de dados. Por padrão, o nome
 * da tabela será "usuario".
 */
@Entity
public class Usuario {

    /**
     * @Id: Especifica que este campo é a chave primária (Primary Key) da tabela.
     * É o identificador único de cada registro (usuário).
     */
    @Id
    /**
     * @GeneratedValue: Configura a estratégia de geração automática da chave primária.
     * "strategy = GenerationType.IDENTITY": Indica que a geração do valor do ID
     * é delegada ao banco de dados, que geralmente usa uma coluna de auto-incremento.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @Column: Permite especificar propriedades da coluna que será mapeada no banco.
     * "nullable = false": Aplica uma restrição "NOT NULL" na coluna "nome",
     * garantindo que todo usuário deva obrigatoriamente ter um nome.
     */
    @Column(nullable = false)
    private String nome;

    /**
     * @Column: Aqui usamos duas propriedades para a coluna "username".
     * "unique = true": Garante que não haverá dois usuários com o mesmo username.
     * O banco de dados aplicará uma restrição de unicidade (unique constraint).
     * "nullable = false": Garante que o campo username não pode ser nulo.
     */
    @Column(unique = true, nullable = false)
    private String username;
}