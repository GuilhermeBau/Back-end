package com.example.microblog.repository;

import com.example.microblog.model.Curtida;
import com.example.microblog.model.Post;
import com.example.microblog.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurtidaRepository extends JpaRepository<Curtida, Long> {
    Optional<Curtida> findByPostAndUsuario(Post post, Usuario usuario);
}