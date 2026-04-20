package com.umc.biblioteca.controller;

import com.umc.biblioteca.entity.Livro;
import com.umc.biblioteca.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    // Carrega a tela com a lista de livros e o formulário
    @GetMapping
    public String paginaLivros(Model model) {
        model.addAttribute("livros", livroService.listarTodos());
        model.addAttribute("novoLivro", new Livro()); // Objeto vazio para o form
        return "livros"; // Nome do arquivo HTML
    }

    // Recebe os dados do formulário e salva no Mongo
    @PostMapping("/salvar")
    public String salvarLivro(Livro livro) {
        livroService.salvar(livro);
        return "redirect:/livros"; // Recarrega a página
    }
}