package modelo;

/**
 *
 * @author José Armando
 */
public class Professor extends Usuario {
    private String departamento;
    private int limiteEmprestimo;

    public Professor(String nome, String matricula, String endereco, String departamento) {
        super(nome, matricula, endereco);
        this.departamento = departamento;
        this.limiteEmprestimo = 5; // Exemplo: limite maior para professores
    }

    // Método funcional do diagrama
    public boolean verificarLimiteEmprestimos(int emprestimosAtuais) {
        return emprestimosAtuais < this.limiteEmprestimo;
    }

    // Getters e Setters
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public int getLimiteEmprestimo() { return limiteEmprestimo; }
    public void setLimiteEmprestimo(int limiteEmprestimo) { this.limiteEmprestimo = limiteEmprestimo; }
}