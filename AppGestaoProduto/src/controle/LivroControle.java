package controle;

import modelo.Livro;
import modelo.dao.LivroDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author José Armando
 */
public class LivroControle {
    private LivroDAO livroDAO;

    // Construtor limpo: não pede mais a Connection!
    public LivroControle() {
        this.livroDAO = new LivroDAO(); 
    }

    // Método para cadastrar (usado pelo Funcionario, conforme o diagrama)
    public boolean registrarLivro(String titulo, String autor, int ano, int qtd, String categoria) {
        // Como o DAO agora devolve true ou false e trata os erros, o código fica super limpo!
        Livro novoLivro = new Livro(0, titulo, autor, ano, qtd, categoria);
        return livroDAO.adicionar(novoLivro);
    }

    // Método para listar livros na interface
    public List<Livro> buscarTodosOsLivros() {
        try {
            return livroDAO.listarTodos();
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
            return null;
        }
    }

    // Método funcional de controle de estoque e empréstimo
    public boolean processarEmprestimo(Livro livro) {
        if (livro.verificarDisponibilidade()) {
            livro.atualizarEstoque(-1); // Retira 1 do estoque
            try {
                livroDAO.atualizar(livro); // Persiste a mudança no BD
                return true;
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar banco: " + e.getMessage());
            }
        }
        return false; // Livro indisponível
    }
}