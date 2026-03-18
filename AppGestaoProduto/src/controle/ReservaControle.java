package controle;
import modelo.Reserva;
import modelo.Emprestimo;
import modelo.dao.ReservaDAO;
/**
 *
 * @author José Armando
 */
public class ReservaControle {
    
    private ReservaDAO reservaDAO;

    public String checarEConverterReserva(Reserva reserva, int idReservaBD) {
        // Verifica se a reserva já passou da validade
        if (reserva.verificarExpiracao()) {
            reservaDAO.atualizarStatus(idReservaBD, "Expirada");
            return "A reserva expirou e foi cancelada.";
        }

        // Tenta converter para empréstimo (só funciona se o status for Aguardando e o livro tiver disponibilidade)
        Emprestimo novoEmprestimo = reserva.converterParaEmprestimo();
        
        if (novoEmprestimo != null) {
            reservaDAO.atualizarStatus(idReservaBD, "Atendida");
            // Aqui você chamaria o EmprestimoController para salvar o novoEmprestimo no BD
            return "Reserva convertida em empréstimo com sucesso!";
        } else {
            return "O livro ainda não está disponível para conversão da reserva.";
        }
    }
}
