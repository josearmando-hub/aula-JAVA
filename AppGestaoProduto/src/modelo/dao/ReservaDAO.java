package modelo.dao;
import modelo.Reserva;
import util.ConectaBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author José Armando
 */
public class ReservaDAO {

    public boolean adicionar(Reserva reserva) {
        String sql = "INSERT INTO reserva (usuario_matricula, livro_id, dataSolicitacao, dataExpiracao, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, reserva.getUsuario().getMatricula());
            stmt.setInt(2, reserva.getLivro().getIdUnico());
            
            // Converte a data do Java para o SQL
            stmt.setDate(3, new java.sql.Date(reserva.getDataSolicitacao().getTime()));
            stmt.setDate(4, new java.sql.Date(reserva.getDataExpiracao().getTime()));
            
            stmt.setString(5, reserva.getStatus());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao gerar reserva: " + e.getMessage());
            return false;
        }
    }
    
    // Método para atualizar o status (usado quando a reserva é convertida em empréstimo ou expira)
    public boolean atualizarStatus(int idReserva, String novoStatus) {
        String sql = "UPDATE reserva SET status = ? WHERE id = ?";
        try (java.sql.Connection conn = util.ConectaBanco.getConexao();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, novoStatus);
            stmt.setInt(2, idReserva);
            stmt.executeUpdate();
            return true;
        } catch (java.sql.SQLException e) {
            System.err.println("Erro ao atualizar status da reserva: " + e.getMessage());
            return false;
        }
    }
}