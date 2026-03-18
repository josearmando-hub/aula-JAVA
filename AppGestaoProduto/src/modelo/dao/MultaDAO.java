package modelo.dao;
import modelo.Multa;
import util.ConectaBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author José Armando
 */
public class MultaDAO {

    // 1. Método para Salvar a multa recém-gerada no banco de dados
    public boolean adicionar(Multa multa, int idEmprestimo) {
        String sql = "INSERT INTO multa (emprestimo_id, diasAtraso, valorTotal, statusPagamento) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idEmprestimo);
            stmt.setInt(2, multa.getDiasAtraso());
            stmt.setFloat(3, multa.getValorTotal());
            stmt.setString(4, multa.getStatusPagamento());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao gerar multa: " + e.getMessage());
            return false;
        }
    }

    // 2. Método para Buscar a multa pelo ID (Usado na hora em que o aluno vai pagar)
    public Multa buscarPorId(int idMulta) {
        String sql = "SELECT * FROM multa WHERE id = ?";
        
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idMulta);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Recria o objeto Multa baseado no que está no banco
                Multa multa = new Multa(rs.getInt("diasAtraso"));
                
                // Setters para atualizar o objeto com os dados do banco
                multa.setValorTotal(rs.getFloat("valorTotal"));
                multa.setStatusPagamento(rs.getString("statusPagamento"));
                
                return multa;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar multa: " + e.getMessage());
        }
        return null;
    }

    // 3. Método para Atualizar a multa (Usado pelo Funcionário após o método quitarMulta() do UML)
    public boolean atualizarMulta(Multa multa, int idMulta) {
        String sql = "UPDATE multa SET statusPagamento = ?, valorTotal = ? WHERE id = ?";
        
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, multa.getStatusPagamento());
            stmt.setFloat(2, multa.getValorTotal());
            stmt.setInt(3, idMulta);
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar multa: " + e.getMessage());
            return false;
        }
    }
    
    // Método para quitar a multa diretamente pelo ID
    public boolean quitarMulta(int idMulta) {
        String sql = "UPDATE multa SET statusPagamento = 'Pago', valorTotal = 0.0 WHERE id = ?";
        
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idMulta);
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0; // Retorna true se alguma linha foi atualizada no banco
            
        } catch (SQLException e) {
            System.err.println("Erro ao quitar a multa: " + e.getMessage());
            return false;
        }
    }
}