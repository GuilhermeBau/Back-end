// Define o pacote para as classes de modelo (entidades do banco de dados).
package com.example.microblog.model;

// Importa as anotações do Jakarta Persistence (para o banco de dados) e do Lombok.
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

/**
 * @Data: Anotação do Lombok que cria automaticamente para todos os campos:
 * - Getters (ex: getId())
 * - Setters (ex: setId())
 * - toString()
 * - equals() e hashCode()
 * - Um construtor com os campos obrigatórios.
 * É uma forma muito conveniente de limpar o código.
 */
@Data
/**
 * @Entity: Anotação do JPA que marca esta classe como uma entidade.
 * Isso significa que ela corresponde a uma tabela no banco de dados.
 * O nome da tabela, por padrão, será o nome da classe ("curtida").
 */
@Entity
/**
 * @Table: Permite especificar detalhes sobre a tabela que será criada.
 * Aqui, estamos usando para definir uma restrição de unicidade (unique constraint).
 * "uniqueConstraints": Garante que não haverá duas linhas na tabela com a mesma
 * combinação de valores nas colunas "post_id" e "usuario_id".
 * É assim que implementamos no nível do banco de dados a regra de negócio:
 * "um usuário só pode curtir um post uma vez".
 */
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"post_id", "usuario_id"})
})
public class Curtida {

    /**
     * @Id: Marca este campo como a chave primária (Primary Key - PK) da tabela.
     */
    @Id
    /**
     * @GeneratedValue: Define a estratégia de geração da chave primária.
     * "strategy = GenerationType.IDENTITY": Informa ao banco de dados (como MySQL ou H2)
     * que ele deve gerenciar a auto-incrementação deste campo.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @ManyToOne: Define um relacionamento "Muitos para Um".
     * Muitas curtidas (@Many) podem pertencer a um Post (@ToOne).
     */
    @ManyToOne
    /**
     * @JoinColumn: Especifica a coluna de chave estrangeira (Foreign Key - FK).
     * "name = 'post_id'": O nome da coluna na tabela "curtida" que irá armazenar o ID do post.
     * "nullable = false": Garante que uma curtida não pode existir sem estar associada a um post.
     */
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * @ManyToOne: Define um relacionamento "Muitos para Um".
     * Muitas curtidas (@Many) podem ser feitas por um Usuário (@ToOne).
     */
    @ManyToOne
    /**
     * @JoinColumn: Especifica a coluna de chave estrangeira.
     * "name = 'usuario_id'": O nome da coluna que irá armazenar o ID do usuário que curtiu.
     * "nullable = false": Uma curtida sempre deve pertencer a um usuário.
     */
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * @CreationTimestamp: Anotação do Hibernate.
     * Faz com que o Hibernate preencha este campo automaticamente com a data e hora
     * exatas do momento em que a curtida é salva no banco de dados pela primeira vez.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;
}