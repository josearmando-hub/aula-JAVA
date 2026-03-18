package controle;

import modelo.*;
import modelo.dao.EmprestimoDAO;
import modelo.dao.MultaDAO;
import modelo.dao.LivroDAO;
import java.util.concurrent.TimeUnit;
import java.sql.SQLException; // <-- ADICIONE ESTA LINHA PARA PARAR DE DAR ERRO NO CATCH

/**
 *
 * @author José Armando
 */

public class EmprestimoControle {
//... resto do código
    private EmprestimoDAO emprestimoDAO;
    private MultaDAO multaDAO;
    private LivroDAO livroDAO;

    public EmprestimoControle() {
        this.emprestimoDAO = new EmprestimoDAO();
        this.multaDAO = new MultaDAO();
        // Agora o LivroDAO não pede mais a conexão no construtor!
        this.livroDAO = new LivroDAO(); 
    }

    // Lógica para Solicitar Empréstimo
    public String realizarEmprestimo(Usuario usuario, Livro livro, int totalEmprestimosAtivosDoUsuario) {
        // 1. Verificar disponibilidade do livro
        if (!livro.verificarDisponibilidade()) {
            return "Erro: Livro indisponível no momento. Deseja fazer uma reserva?";
        }

        // 2. Verificar limite de empréstimos usando Polimorfismo (Aluno ou Professor)
        boolean limiteOk = true;
        if (usuario instanceof Aluno) {
            limiteOk = ((Aluno) usuario).verificarLimiteEmprestimos(totalEmprestimosAtivosDoUsuario);
        } else if (usuario instanceof Professor) {
            limiteOk = ((Professor) usuario).verificarLimiteEmprestimos(totalEmprestimosAtivosDoUsuario);
        }

        if (!limiteOk) {
            return "Erro: Usuário atingiu o limite máximo de empréstimos.";
        }

        // 3. Se tudo estiver OK, cria o empréstimo
        Emprestimo novoEmprestimo = new Emprestimo(usuario, livro);
        livro.atualizarEstoque(-1); // Subtrai 1 do estoque

        try {
            emprestimoDAO.adicionar(novoEmprestimo);
            livroDAO.atualizar(livro); // Atualiza o estoque no BD
            return "Empréstimo realizado com sucesso! Devolução prevista para: " + novoEmprestimo.getDataDevolucaoPrevista();
        } catch (SQLException e) {
            return "Erro no banco de dados: " + e.getMessage();
        }
    }

    // Lógica para Devolver Livro e Gerar Multa se necessário
    public String realizarDevolucao(Emprestimo emprestimo, int idEmprestimoBD) {
        emprestimo.finalizarEmprestimo(); // Seta a data real, muda status e devolve o estoque no objeto
        String mensagem = "Devolução registrada com sucesso.";

        // Verifica atraso
        if (emprestimo.verificarAtraso()) {
            long diffEmMilissegundos = Math.abs(emprestimo.getDataDevolucaoReal().getTime() - emprestimo.getDataDevolucaoPrevista().getTime());
            long diffEmDias = TimeUnit.DAYS.convert(diffEmMilissegundos, TimeUnit.MILLISECONDS);
            
            Multa novaMulta = new Multa((int) diffEmDias);
            multaDAO.adicionar(novaMulta, idEmprestimoBD);
            mensagem += " Atenção: Multa gerada por " + diffEmDias + " dias de atraso. Valor: R$ " + novaMulta.getValorTotal();
        }

        try {
            emprestimoDAO.atualizarDevolucao(emprestimo, idEmprestimoBD);
            livroDAO.atualizar(emprestimo.getLivro()); // Atualiza estoque devolvido no BD
            return mensagem;
        } catch (SQLException e) {
            return "Erro ao processar devolução no BD: " + e.getMessage();
        }
    }
}
