package com.umc.biblioteca.repository;

import com.umc.biblioteca.entity.Livro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends MongoRepository<Livro, String> {
    // O Spring já te dá save(), findAll(), deleteById(), etc. de graça aqui.
}