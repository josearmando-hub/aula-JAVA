package modelo;

/**
 *
 * @author José Armando
 */
public class Funcionario extends Usuario {
    // O construtor repassa os dados para a classe pai (Usuario)
    public Funcionario(String nome, String matricula, String endereco) {
        super(nome, matricula, endereco);
    }

    // Métodos administrativos exigidos no seu diagrama UML
    // (A lógica real de banco de dados acontece nos Controllers, mas 
    // a classe precisa ter esses métodos para respeitar a modelagem Orientada a Objetos)
    
    public void registrarLivro(Livro livro) {
        System.out.println("Livro " + livro.getTitulo() + " registrado no sistema pelo funcionário " + this.getNome());
    }

    public void aprovarEmprestimo(Emprestimo emprestimo) {
        emprestimo.setSituacao("Aprovado");
        System.out.println("Empréstimo aprovado com sucesso.");
    }

    public void gerenciarMulta(Multa multa) {
        System.out.println("Gerenciando multa no valor de: R$ " + multa.getValorTotal());
        multa.quitarMulta(); // Chama o método da classe Multa para zerar o valor
    }
}
