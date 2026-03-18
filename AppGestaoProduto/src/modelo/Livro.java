package modelo;

/**
 *
 * @author José Armando
 */
public class Livro {
    private int idUnico;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private int quantidadeDisponivel;
    private String categoria;

    public Livro() {}

    public Livro(int idUnico, String titulo, String autor, int anoPublicacao, int quantidadeDisponivel, String categoria) {
        this.idUnico = idUnico;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.categoria = categoria;
    }

    // Métodos funcionais exigidos no diagrama
    public boolean verificarDisponibilidade() {
        return this.quantidadeDisponivel > 0;
    }

    public void atualizarEstoque(int quantidade) {
        // Se quantidade for negativa, subtrai (saída). Se positiva, soma (entrada).
        this.quantidadeDisponivel += quantidade;
    }

    // Getters e Setters
    public int getIdUnico() { return idUnico; }
    public void setIdUnico(int idUnico) { this.idUnico = idUnico; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }
    public void setQuantidadeDisponivel(int quantidadeDisponivel) { this.quantidadeDisponivel = quantidadeDisponivel; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}