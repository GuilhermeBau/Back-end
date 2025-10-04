// Define o pacote onde esta classe reside, seguindo a convenção de nomenclatura.
package com.example.microblog.controller;

// Importa as classes que serão utilizadas neste arquivo.
import com.example.microblog.dto.UsuarioResponseDTO;
import com.example.microblog.model.Usuario;
import com.example.microblog.repository.UsuarioRepository;
import jakarta.validation.Valid; // Importa a anotação para ativar a validação.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @RestController: Marca esta classe como um Controller do Spring MVC onde os métodos
 * retornam diretamente um corpo de resposta (geralmente em JSON), e não uma view.
 */
@RestController
/**
 * @RequestMapping: Define a rota base para todos os endpoints deste controller.
 * Qualquer requisição para "/api/usuarios" será direcionada para esta classe.
 */
@RequestMapping("/api/usuarios")
public class UsuarioController {

    /**
     * @Autowired: Mecanismo de injeção de dependência do Spring.
     * O Spring irá gerenciar o ciclo de vida do UsuarioRepository e fornecer uma
     * instância pronta para uso aqui, nos poupando de criar o objeto manualmente.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Endpoint para criar um novo usuário.
     * Mapeado para o método HTTP POST na rota "/api/usuarios".
     * @Valid: Ativa as validações definidas na entidade Usuario (ex: @NotBlank).
     * @RequestBody: Converte o JSON do corpo da requisição em um objeto Usuario.
     * Retorna um DTO com os dados do usuário criado.
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody Usuario usuario) {
        // Usa o repositório para salvar a entidade Usuario no banco de dados.
        Usuario novoUsuario = usuarioRepository.save(usuario);

        // Converte a entidade 'Usuario' que foi salva para um 'UsuarioResponseDTO'.
        // Isso é uma boa prática para não expor a estrutura do seu banco de dados na API.
        return ResponseEntity
                .status(HttpStatus.CREATED) // Define o status HTTP para 201 Created, o padrão para criação de recursos.
                .body(new UsuarioResponseDTO(novoUsuario)); // Coloca o DTO no corpo da resposta.
    }

    /**
     * Endpoint para listar todos os usuários.
     * Mapeado para o método HTTP GET na rota "/api/usuarios".
     * Retorna uma lista de DTOs, protegendo os detalhes internos da entidade.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        // Busca todas as entidades Usuario do banco de dados.
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Converte a lista de entidades 'Usuario' para uma lista de 'UsuarioResponseDTO'.
        List<UsuarioResponseDTO> usuariosDTO = usuarios.stream()
                // Para cada 'usuario' na lista, cria um novo 'UsuarioResponseDTO'.
                .map(UsuarioResponseDTO::new) // O mesmo que .map(usuario -> new UsuarioResponseDTO(usuario))
                // Coleta todos os DTOs criados em uma nova lista.
                .collect(Collectors.toList());

        // Retorna uma resposta HTTP 200 OK com a lista de DTOs no corpo.
        return ResponseEntity.ok(usuariosDTO);
    }

    /**
     * Endpoint para editar um usuário existente, identificado pelo ID na URL.
     * Mapeado para o método HTTP PUT na rota "/api/usuarios/{id}".
     * @PathVariable: Extrai o valor do 'id' da URL e o passa como parâmetro para o método.
     * Retorna um DTO com os dados do usuário atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> editarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuarioDetalhes) {
        // Busca o usuário pelo ID. Retorna um Optional, que pode conter o usuário ou estar vazio.
        return usuarioRepository.findById(id)
                // O método .map() é executado apenas se o usuário for encontrado.
                .map(usuarioExistente -> {
                    // Atualiza os dados do usuário encontrado com os detalhes da requisição.
                    usuarioExistente.setNome(usuarioDetalhes.getNome());
                    usuarioExistente.setUsername(usuarioDetalhes.getUsername());
                    // Salva o usuário com as alterações no banco de dados.
                    Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
                    // Converte para DTO e retorna uma resposta HTTP 200 OK.
                    return ResponseEntity.ok(new UsuarioResponseDTO(usuarioAtualizado));
                })
                // O método .orElse() é executado se o usuário não for encontrado.
                // Retorna uma resposta HTTP 404 Not Found.
                .orElse(ResponseEntity.notFound().build());
    }
}