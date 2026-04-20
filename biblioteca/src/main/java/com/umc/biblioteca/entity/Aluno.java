package com.umc.biblioteca.entity;

public class Aluno extends Usuario {
    
    @Override
    public boolean verificarLimiteEmprestimos() {
        // Regra: Aluno pode pegar no máximo 3 livros
        return this.getQuantidadeEmprestimosAtuais() < 3;
    }
    @Override
    public String getTipoUsuario() {
        return "Aluno";
    }
}