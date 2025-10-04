// Define o pacote para os Data Transfer Objects (DTOs).
// Manter DTOs em um pacote separado é uma excelente prática de organização.
package com.example.microblog.dto;

// Importa as classes necessárias.
import com.example.microblog.model.Post; // Importa a entidade Post para que possamos convertê-la.
import lombok.Getter; // Importa a anotação do Lombok para gerar os getters.
import java.time.LocalDateTime;

/**
 * @Getter: Anotação do Lombok que gera automaticamente todos os métodos getter
 * para os campos desta classe (ex: getId(), getConteudo(), etc.) em tempo de compilação.
 * Isso nos poupa de escrever código repetitivo.
 */
@Getter
/**
 * Esta é uma classe DTO (Data Transfer Object). Seu objetivo é moldar os dados
 * de um Post da forma como queremos que eles sejam exibidos na resposta da API.
 * Ela atua como um "filtro", expondo apenas os campos necessários e permitindo
 * renomeá-los para serem mais claros para o cliente (ex: Postman, front-end).
 */
public class PostResponseDTO {

    // Campo para o ID do post.
    private Long id;

    // Campo para o conteúdo do post.
    private String conteudo;

    // Campo para a data de criação. Note que renomeamos de "createdAt" (da entidade)
    // para "dataCriacao", tornando o nome mais amigável em português na resposta JSON.
    private LocalDateTime dataCriacao;

    // Campo para o total de curtidas. Renomeamos de "likesCount" para "totalCurtidas".
    private long totalCurtidas;

    // Campo para o autor do post. Importante: usamos outro DTO (UsuarioResponseDTO) aqui!
    // Isso garante que os dados do autor também sejam filtrados e não exponham a entidade Usuario completa.
    // É uma prática chamada "DTO aninhado" (nested DTO).
    private UsuarioResponseDTO autor;

    /**
     * Este é um construtor de conversão. Ele recebe uma entidade 'Post' completa
     * (vinda do banco de dados) e a transforma neste DTO, mapeando os campos.
     * @param post A entidade Post a ser convertida.
     */
    public PostResponseDTO(Post post) {
        // Mapeia o campo 'id' da entidade para o campo 'id' do DTO.
        this.id = post.getId();
        // Mapeia o campo 'conteudo'.
        this.conteudo = post.getConteudo();
        // Mapeia o campo 'createdAt' da entidade para o campo 'dataCriacao' do DTO.
        this.dataCriacao = post.getCreatedAt();
        // Mapeia o campo 'likesCount' da entidade para o campo 'totalCurtidas' do DTO.
        this.totalCurtidas = post.getLikesCount();
        // Pega a entidade 'Usuario' de dentro do 'Post' e a usa para criar um 'UsuarioResponseDTO'.
        this.autor = new UsuarioResponseDTO(post.getAutor());
    }
}