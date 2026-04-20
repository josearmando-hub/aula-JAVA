package com.umc.biblioteca.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "livro")
public class Livro {
    @Id
    private String id;
    private String titulo;
    private int copiasDisponiveis;

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getCopiasDisponiveis() { return copiasDisponiveis; }
    public void setCopiasDisponiveis(int copiasDisponiveis) { this.copiasDisponiveis = copiasDisponiveis; }

    // Regra de negócio simples
    public boolean verificarDisponibilidade() {
        return this.copiasDisponiveis > 0;
    }
    public void atualizarEstoque(int valor) {
        this.copiasDisponiveis += valor;
    }
}