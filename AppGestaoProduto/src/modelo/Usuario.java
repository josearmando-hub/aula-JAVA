package modelo;

/**
 *
 * @author José Armando
 */

public abstract class Usuario {
    private String nome;
    private String matricula;
    private String endereco;

    public Usuario(String nome, String matricula, String endereco) {
        this.nome = nome;
        this.matricula = matricula;
        this.endereco = endereco;
    }

    // Métodos do diagrama (A lógica real de BD ficaria no Controller/DAO)
    public void solicitarEmprestimo(Livro livro) {
        System.out.println("Solicitação de empréstimo criada para o livro: " + livro.getTitulo());
    }

    public void devolverLivro(Livro livro) {
        System.out.println("Devolução registrada para o livro: " + livro.getTitulo());
    }

    public void fazerReserva(Livro livro) {
        System.out.println("Reserva feita para o livro: " + livro.getTitulo());
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}