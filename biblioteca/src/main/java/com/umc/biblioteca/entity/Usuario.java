package com.umc.biblioteca.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public abstract class Usuario {
    
    @Id
    private String id;
    private String nome;
    private String matricula;
    private int quantidadeEmprestimosAtuais = 0;

    // O método mágico do seu diagrama!
    public abstract boolean verificarLimiteEmprestimos();

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public int getQuantidadeEmprestimosAtuais() { return quantidadeEmprestimosAtuais; }
    public void setQuantidadeEmprestimosAtuais(int quantidadeEmprestimosAtuais) { 
        this.quantidadeEmprestimosAtuais = quantidadeEmprestimosAtuais; 
    }
    public abstract String getTipoUsuario();
}