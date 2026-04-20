package com.umc.biblioteca.repository;

import com.umc.biblioteca.entity.Emprestimo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoRepository extends MongoRepository<Emprestimo, String> {
}