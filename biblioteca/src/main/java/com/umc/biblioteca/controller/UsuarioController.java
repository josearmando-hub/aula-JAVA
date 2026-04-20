package com.umc.biblioteca.controller;

import com.umc.biblioteca.entity.Aluno;
import com.umc.biblioteca.entity.Professor;
import com.umc.biblioteca.entity.Usuario;
import com.umc.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios"; // Nome do arquivo HTML
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@RequestParam("nome") String nome, 
                                @RequestParam("matricula") String matricula, 
                                @RequestParam("tipo") String tipo) {
        Usuario novoUsuario;
        
        // Lógica para instanciar a classe correta baseada no formulário
        if ("ALUNO".equals(tipo)) {
            novoUsuario = new Aluno();
        } else {
            novoUsuario = new Professor();
        }
        
        novoUsuario.setNome(nome);
        novoUsuario.setMatricula(matricula);
        
        usuarioRepository.save(novoUsuario);
        return "redirect:/usuarios";
    }
    
    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable String id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios";
    }
}