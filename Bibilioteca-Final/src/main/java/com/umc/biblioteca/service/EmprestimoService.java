package com.umc.biblioteca.service;

import com.umc.biblioteca.entity.Emprestimo;
import com.umc.biblioteca.entity.Livro;
import com.umc.biblioteca.entity.Usuario;
import com.umc.biblioteca.repository.EmprestimoRepository;
import com.umc.biblioteca.repository.LivroRepository;
import com.umc.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Emprestimo realizarEmprestimo(String usuarioId, String livroId) throws Exception {
        
        // 1. Busca no banco
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new Exception("Usuário não encontrado!"));
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new Exception("Livro não encontrado!"));

        // 2. Regra do Fluxograma: Verifica se tem cópia
        if (!livro.verificarDisponibilidade()) {
            throw new Exception("Empréstimo Recusado: Livro fora de estoque.");
        }

        // 3. Regra do Fluxograma: Polimorfismo (Aluno x Professor)
        if (!usuario.verificarLimiteEmprestimos()) {
            throw new Exception("Empréstimo Recusado: Você atingiu o limite máximo de livros.");
        }

        // 4. Gera o Registro
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setStatus("ATIVO");
        
        // 5. Dá baixa no inventário e atualiza o usuário
        livro.atualizarEstoque(-1);
        usuario.setQuantidadeEmprestimosAtuais(usuario.getQuantidadeEmprestimosAtuais() + 1);

        // Salva as 3 alterações no MongoDB
        livroRepository.save(livro);
        usuarioRepository.save(usuario);
        return emprestimoRepository.save(emprestimo);
    }
    
    public void devolverLivro(String emprestimoId) throws Exception {
        // 1. Busca o registro do empréstimo
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new Exception("Empréstimo não encontrado."));

        if ("DEVOLVIDO".equals(emprestimo.getStatus())) {
            throw new Exception("Este livro já foi devolvido.");
        }

        // 2. Recupera o usuário e o livro vinculados
        Usuario usuario = emprestimo.getUsuario();
        Livro livro = emprestimo.getLivro();

        // 3. Faz a matemática inversa
        livro.atualizarEstoque(1); // Devolve 1 cópia para a prateleira
        usuario.setQuantidadeEmprestimosAtuais(usuario.getQuantidadeEmprestimosAtuais() - 1); // Libera o limite do aluno/professor
        
        // 4. Atualiza o status do empréstimo
        emprestimo.setStatus("DEVOLVIDO");

        // 5. Salva tudo no banco
        livroRepository.save(livro);
        usuarioRepository.save(usuario);
        emprestimoRepository.save(emprestimo);
    }
}