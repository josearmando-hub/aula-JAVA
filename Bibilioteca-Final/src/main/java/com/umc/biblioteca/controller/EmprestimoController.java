package com.umc.biblioteca.controller;

import com.umc.biblioteca.entity.Emprestimo;
import com.umc.biblioteca.repository.EmprestimoRepository;
import com.umc.biblioteca.service.EmprestimoService;
import com.umc.biblioteca.repository.LivroRepository;
import com.umc.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/balcao")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;
    
    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository; // Injeção necessária para listar os empréstimos

    @GetMapping
    public String paginaBalcao(Model model) {
        // Envia as listas de livros, usuários e empréstimos para a tela
        model.addAttribute("livros", livroRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("emprestimos", emprestimoRepository.findAll());
        return "balcao";
    }

    @PostMapping("/emprestar")
    public String realizarEmprestimo(
            @RequestParam("usuarioId") String usuarioId, 
            @RequestParam("livroId") String livroId, 
            Model model) {
        
        try {
            emprestimoService.realizarEmprestimo(usuarioId, livroId);
            model.addAttribute("mensagemSucesso", "Empréstimo realizado com sucesso!");
        } catch (Exception e) {
            model.addAttribute("mensagemErro", e.getMessage());
        }
        
        // Recarrega todas as listas após a operação
        model.addAttribute("livros", livroRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("emprestimos", emprestimoRepository.findAll());
        return "balcao";
    }

    @PostMapping("/devolver/{id}")
    public String devolverLivro(@PathVariable String id, Model model) {
        try {
            emprestimoService.devolverLivro(id);
            model.addAttribute("mensagemSucesso", "Livro devolvido com sucesso!");
        } catch (Exception e) {
            model.addAttribute("mensagemErro", e.getMessage());
        }

        // Recarrega todas as listas após a devolução
        model.addAttribute("livros", livroRepository.findAll());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("emprestimos", emprestimoRepository.findAll());
        return "balcao";
    }
}