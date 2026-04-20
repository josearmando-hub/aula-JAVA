package com.umc.biblioteca.entity;

public class Professor extends Usuario {

    @Override
    public boolean verificarLimiteEmprestimos() {
        // Regra: Professor pode pegar no máximo 5 livros
        return this.getQuantidadeEmprestimosAtuais() < 5;
    }
    @Override
    public String getTipoUsuario() {
        return "Professor";
    }
}