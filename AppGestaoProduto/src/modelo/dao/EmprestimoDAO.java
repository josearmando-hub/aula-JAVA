package modelo.dao;

import modelo.Emprestimo;
import modelo.Livro;
import modelo.Usuario;
import util.ConectaBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author José Armando
 */
public class EmprestimoDAO {

    // CREATE: Registra um novo empréstimo no banco
    public void adicionar(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimo (usuario_matricula, livro_id, dataRetirada, dataDevolucaoPrevista, situacao) VALUES (?, ?, ?, ?, ?)";
        
        // Chamando a conexão do seu pacote util
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Relacionamentos: pegamos os IDs do Usuário e do Livro
            stmt.setString(1, emprestimo.getUsuario().getMatricula());
            stmt.setInt(2, emprestimo.getLivro().getIdUnico());
            
            // Tratamento de datas do Java para o SQL
            stmt.setDate(3, new java.sql.Date(emprestimo.getDataRetirada().getTime()));
            stmt.setDate(4, new java.sql.Date(emprestimo.getDataDevolucaoPrevista().getTime()));
            stmt.setString(5, emprestimo.getSituacao());
            
            stmt.executeUpdate();
        }
    }

    // UPDATE: Atualiza quando o livro é devolvido (preenche dataDevolucaoReal e muda situação)
    public void atualizarDevolucao(Emprestimo emprestimo, int idEmprestimo) throws SQLException {
        String sql = "UPDATE emprestimo SET dataDevolucaoReal = ?, situacao = ? WHERE id = ?";
        
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, new java.sql.Date(emprestimo.getDataDevolucaoReal().getTime()));
            stmt.setString(2, emprestimo.getSituacao());
            stmt.setInt(3, idEmprestimo);
            
            stmt.executeUpdate();
        }
    }

    // DELETE: Caso o funcionário precise cancelar/excluir um registro incorreto
    public void deletar(int idEmprestimo) throws SQLException {
        String sql = "DELETE FROM emprestimo WHERE id = ?";
        
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idEmprestimo);
            stmt.executeUpdate();
        }
    }
    
    // Método para buscar o Empréstimo pelo ID (Essencial para a Devolução)
    public modelo.Emprestimo buscarPorId(int idEmprestimo) {
        String sql = "SELECT * FROM emprestimo WHERE id = ?";
        try (java.sql.Connection conn = util.ConectaBanco.getConexao();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idEmprestimo);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Busca o usuário e o livro usando os DAOs que já temos
                modelo.dao.UsuarioDAO usuarioDAO = new modelo.dao.UsuarioDAO();
                modelo.dao.LivroDAO livroDAO = new modelo.dao.LivroDAO();
                
                modelo.Usuario usuario = usuarioDAO.buscarPorMatricula(rs.getString("usuario_matricula"));
                modelo.Livro livro = livroDAO.buscarPorId(rs.getInt("livro_id"));
                
                // Recria o objeto Emprestimo
                modelo.Emprestimo emp = new modelo.Emprestimo(usuario, livro);
                emp.setSituacao(rs.getString("situacao"));
                
                // Atualiza as datas para as que estão salvas no banco
                emp.setDataRetirada(rs.getDate("dataRetirada"));
                emp.setDataDevolucaoPrevista(rs.getDate("dataDevolucaoPrevista"));
                
                if (rs.getDate("dataDevolucaoReal") != null) {
                    emp.setDataDevolucaoReal(rs.getDate("dataDevolucaoReal"));
                }
                
                return emp;
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Erro ao buscar empréstimo: " + e.getMessage());
        }
        return null;
    }
    
    // Conta a quantidade de Emprestimos
    public int contarEmprestimosAtivos(String matricula) {
        String sql = "SELECT COUNT(*) FROM emprestimo WHERE usuario_matricula = ? AND situacao = 'Ativo'";
        try (java.sql.Connection conn = util.ConectaBanco.getConexao();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, matricula);
            java.sql.ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Erro ao contar empréstimos: " + e.getMessage());
        }
        return 0;
    }
}
