// Define o pacote para os Data Transfer Objects (DTOs), mantendo o projeto organizado.
package com.example.microblog.dto;

// Importa as classes necessárias.
import com.example.microblog.model.Usuario; // A entidade 'Usuario' que será a fonte dos dados.
import lombok.Getter; // A anotação do Lombok que cria os getters.

/**
 * @Getter: Anotação do Lombok que, em tempo de compilação, gera automaticamente
 * os métodos públicos getId(), getNome() e getUsername().
 * Isso elimina a necessidade de escrevermos esse código repetitivo manualmente.
 */
@Getter
/**
 * DTO (Data Transfer Object) para representar um Usuário na resposta da API.
 * Esta classe define a estrutura exata dos dados de um usuário que serão
 * enviados para o cliente. É uma boa prática de segurança e organização, pois
 * evita expor a entidade JPA (do banco de dados) diretamente.
 */
public class UsuarioResponseDTO {

    // O ID do usuário.
    private Long id;

    // O nome do usuário.
    private String nome;

    // O nome de usuário (login).
    private String username;

    /**
     * Construtor que serve como um conversor. Ele recebe a entidade JPA 'Usuario'
     * e mapeia seus dados para os campos deste DTO.
     * @param usuario A entidade 'Usuario' vinda do banco de dados.
     */
    public UsuarioResponseDTO(Usuario usuario) {
        // Pega o ID da entidade e atribui ao campo 'id' do DTO.
        this.id = usuario.getId();
        // Pega o nome da entidade e atribui ao campo 'nome' do DTO.
        this.nome = usuario.getNome();
        // Pega o username da entidade e atribui ao campo 'username' do DTO.
        this.username = usuario.getUsername();
    }
}