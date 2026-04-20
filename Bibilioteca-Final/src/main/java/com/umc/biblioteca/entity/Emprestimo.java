package com.umc.biblioteca.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "emprestimos")
public class Emprestimo {

    @Id
    private String id;

    @DBRef
    private Usuario usuario;

    @DBRef
    private Livro livro;

    private LocalDate dataEmprestimo;
    private String status; // "ATIVO", "DEVOLVIDO"

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}