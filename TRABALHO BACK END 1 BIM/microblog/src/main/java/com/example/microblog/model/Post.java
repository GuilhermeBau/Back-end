// Define o pacote para as classes de modelo (entidades).
package com.example.microblog.model;

// Importa as anotações necessárias do Jakarta Persistence, Lombok e Hibernate.
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

/**
 * @Data: Anotação do Lombok que gera automaticamente os métodos essenciais
 * como getters, setters, toString(), equals() e hashCode() para todos os campos da classe.
 * Isso mantém o código limpo e legível.
 */
@Data
/**
 * @Entity: Marca esta classe como uma entidade JPA.
 * Isso significa que ela representa uma tabela no banco de dados, que por padrão
 * será chamada de "post".
 */
@Entity
public class Post {

    /**
     * @Id: Define este campo como a chave primária (Primary Key) da tabela "post".
     */
    @Id
    /**
     * @GeneratedValue: Configura como a chave primária é gerada.
     * "strategy = GenerationType.IDENTITY": Delega ao banco de dados a responsabilidade
     * de gerar e auto-incrementar o valor do ID para cada novo post.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Define uma coluna para armazenar o conteúdo de texto do post.
    // Como não há anotações de tamanho (@Column(length=...)), o JPA usará um tamanho padrão (geralmente 255 caracteres).
    private String conteudo;

    /**
     * @ManyToOne: Define um relacionamento "Muitos para Um".
     * Muitos Posts (@Many) podem ser escritos por um único Usuário (@ToOne).
     */
    @ManyToOne
    /**
     * @JoinColumn: Especifica a coluna que fará a junção (chave estrangeira).
     * "name = 'autor_id'": Cria uma coluna chamada "autor_id" na tabela "post" para armazenar o ID do usuário autor.
     * "nullable = false": Garante que um post não pode ser criado sem um autor, aplicando uma restrição "NOT NULL" no banco.
     */
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    /**
     * @CreationTimestamp: Uma anotação do Hibernate que instrui o JPA a preencher
     * este campo com a data e hora atuais no momento em que o post é salvo pela primeira vez.
     * Garante que a data de criação seja registrada automaticamente.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Define uma coluna para armazenar o número de curtidas de um post.
    // É inicializada com 0, garantindo que todo novo post comece sem nenhuma curtida.
    private long likesCount = 0;
}