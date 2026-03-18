package modelo;

/**
 *
 * @author José Armando
 */
public class Aluno extends Usuario {
    private String curso;
    private int limiteEmprestimos;

    public Aluno(String nome, String matricula, String endereco, String curso) {
        super(nome, matricula, endereco); // Chama o construtor da classe pai (Usuario)
        this.curso = curso;
        this.limiteEmprestimos = 3; // Exemplo: limite padrão de 3 livros
    }

    // Método funcional do diagrama
    public boolean verificarLimiteEmprestimos(int emprestimosAtuais) {
        return emprestimosAtuais < this.limiteEmprestimos;
    }

    // Getters e Setters
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
    public int getLimiteEmprestimos() { return limiteEmprestimos; }
    public void setLimiteEmprestimos(int limiteEmprestimos) { this.limiteEmprestimos = limiteEmprestimos; }
}