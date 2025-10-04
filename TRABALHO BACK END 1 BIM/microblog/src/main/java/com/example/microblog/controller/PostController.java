// Define o pacote onde esta classe está localizada.
package com.example.microblog.controller;

// Importa todas as classes necessárias de outros pacotes.
import com.example.microblog.dto.PostResponseDTO;
import com.example.microblog.model.Curtida;
import com.example.microblog.model.Post;
import com.example.microblog.model.Usuario;
import com.example.microblog.repository.CurtidaRepository;
import com.example.microblog.repository.PostRepository;
import com.example.microblog.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @RestController: Anotação do Spring que combina @Controller e @ResponseBody.
 * Indica que esta classe é um controlador REST, e os métodos aqui dentro
 * retornarão dados (como JSON), e não uma página HTML.
 */
@RestController
/**
 * @RequestMapping: Define a URL base para todos os endpoints neste controller.
 * Todas as requisições para este controller devem começar com "/api/posts".
 */
@RequestMapping("/api/posts")
public class PostController {

    // @Autowired: Injeção de Dependência do Spring.
    // O Spring automaticamente cria uma instância do PostRepository e a "injeta" nesta variável.
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CurtidaRepository curtidaRepository;

    /**
     * Endpoint para criar um novo post.
     * Mapeado para requisições HTTP POST na URL base ("/api/posts").
     * @RequestBody: Converte o corpo (body) da requisição JSON em um objeto Post.
     * Retorna um DTO do post criado.
     */
    @PostMapping
    public ResponseEntity<PostResponseDTO> criarPost(@RequestBody Post post) {
        // --- REGRA DE NEGÓCIO: Valida se o autor do post existe no banco de dados. ---
        // Busca o autor pelo ID fornecido no corpo da requisição.
        Usuario autor = usuarioRepository.findById(post.getAutor().getId())
                // Se não encontrar o autor, lança uma exceção que resultará em um erro 400 (Bad Request).
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Autor não encontrado!"));

        // Define o objeto completo do autor no post antes de salvar.
        post.setAutor(autor);
        // Salva o novo post no banco de dados.
        Post novoPost = postRepository.save(post);

        // Retorna uma resposta HTTP 201 (Created) com o DTO do post recém-criado no corpo.
        return ResponseEntity.status(HttpStatus.CREATED).body(new PostResponseDTO(novoPost));
    }

    /**
     * Endpoint para listar posts em ordem cronológica (mais recentes primeiro).
     * Mapeado para requisições HTTP GET em "/api/posts/cronologico".
     * Retorna uma lista de DTOs.
     */
    @GetMapping("/cronologico")
    public ResponseEntity<List<PostResponseDTO>> getPostsCronologicos() {
        // Usa o método mágico do Spring Data JPA para buscar todos os posts já ordenados.
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        // Converte a lista de entidades (Post) para uma lista de DTOs (PostResponseDTO).
        List<PostResponseDTO> postsDTO = posts.stream()
                .map(PostResponseDTO::new) // Para cada post na lista, cria um novo PostResponseDTO.
                .collect(Collectors.toList()); // Coleta os resultados em uma nova lista.
        // Retorna uma resposta HTTP 200 (OK) com a lista de DTOs no corpo.
        return ResponseEntity.ok(postsDTO);
    }

    /**
     * Endpoint para listar posts por relevância (mais curtidos primeiro).
     * Mapeado para requisições HTTP GET em "/api/posts/relevancia".
     * Retorna uma lista de DTOs.
     */
    @GetMapping("/relevancia")
    public ResponseEntity<List<PostResponseDTO>> getPostsPorRelevancia() {
        // Usa o método mágico do Spring Data JPA para buscar os posts ordenados por likesCount.
        List<Post> posts = postRepository.findAllByOrderByLikesCountDesc();
        // Converte a lista de entidades para uma lista de DTOs.
        List<PostResponseDTO> postsDTO = posts.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
        // Retorna uma resposta HTTP 200 (OK) com a lista de DTOs.
        return ResponseEntity.ok(postsDTO);
    }

    /**
     * Endpoint para um usuário curtir um post.
     * Mapeado para POST em "/api/posts/{postId}/curtir/{usuarioId}".
     * @PathVariable: Extrai os valores das variáveis da URL (postId e usuarioId).
     * Retorna o DTO do post atualizado com o novo número de curtidas.
     */
    @PostMapping("/{postId}/curtir/{usuarioId}")
    public ResponseEntity<PostResponseDTO> curtirPost(@PathVariable Long postId, @PathVariable Long usuarioId) {
        // Busca o post pelo ID. Se não encontrar, lança um erro 404 (Not Found).
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado!"));
        // Busca o usuário pelo ID. Se não encontrar, lança um erro 404.
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));

        // --- REGRA DE NEGÓCIO: Impede que um usuário curta o mesmo post duas vezes. ---
        // Verifica se já existe uma curtida para esta combinação de post e usuário.
        if (curtidaRepository.findByPostAndUsuario(post, usuario).isPresent()) {
            // Se já existir, lança um erro 400 (Bad Request).
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já curtiu este post!");
        }

        // Cria uma nova instância da entidade Curtida.
        Curtida novaCurtida = new Curtida();
        novaCurtida.setPost(post);
        novaCurtida.setUsuario(usuario);
        // Salva a nova curtida no banco de dados.
        curtidaRepository.save(novaCurtida);

        // --- REGRA DE NEGÓCIO: Atualiza o contador de curtidas. ---
        post.setLikesCount(post.getLikesCount() + 1);
        // Salva o post com o contador atualizado.
        Post postAtualizado = postRepository.save(post);

        // Retorna uma resposta 200 (OK) com o DTO do post atualizado.
        return ResponseEntity.ok(new PostResponseDTO(postAtualizado));
    }

    /**
     * Endpoint para remover a curtida de um post.
     * Mapeado para DELETE em "/api/posts/{postId}/curtir/{usuarioId}".
     * Retorna o DTO do post atualizado com o novo número de curtidas.
     */
    @DeleteMapping("/{postId}/curtir/{usuarioId}")
    public ResponseEntity<PostResponseDTO> removerCurtida(@PathVariable Long postId, @PathVariable Long usuarioId) {
        // Busca o post e o usuário. Lança 404 se um deles não existir.
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado!"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));

        // Busca a curtida específica que conecta o post e o usuário.
        Curtida curtida = curtidaRepository.findByPostAndUsuario(post, usuario)
                // Se a curtida não for encontrada, lança um 404.
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curtida não encontrada para ser removida!"));

        // Remove a curtida do banco de dados.
        curtidaRepository.delete(curtida);

        // --- REGRA DE NEGÓCIO: Atualiza o contador de curtidas. ---
        post.setLikesCount(post.getLikesCount() - 1);
        // Salva o post com o contador atualizado.
        Post postAtualizado = postRepository.save(post);

        // Retorna uma resposta 200 (OK) com o DTO do post atualizado.
        return ResponseEntity.ok(new PostResponseDTO(postAtualizado));
    }
}