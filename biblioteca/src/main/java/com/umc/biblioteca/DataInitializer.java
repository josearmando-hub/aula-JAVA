package com.umc.biblioteca;

import com.umc.biblioteca.entity.Aluno;
import com.umc.biblioteca.entity.Livro;
import com.umc.biblioteca.entity.Professor;
import com.umc.biblioteca.repository.LivroRepository;
import com.umc.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository; // Adicionamos isso

    @Override
    public void run(String... args) throws Exception {
        
        // Mantemos os livros que já tínhamos feito
        if (livroRepository.count() == 0) {
            Livro livro1 = new Livro(); livro1.setTitulo("Java para Iniciantes"); livro1.setCopiasDisponiveis(3);
            livroRepository.save(livro1);
        }

        // Inserimos os usuários se o banco estiver vazio
        if (usuarioRepository.count() == 0) {
            System.out.println("Inserindo usuários de teste...");

            Aluno aluno = new Aluno();
            aluno.setNome("Jonathas - Estudante");
            aluno.setMatricula("RA123456");
            
            Professor professor = new Professor();
            professor.setNome("Carlos - Docente");
            professor.setMatricula("MAT98765");

            usuarioRepository.save(aluno);
            usuarioRepository.save(professor);
            
            System.out.println("Usuários inseridos com sucesso!");
        }
    }
}