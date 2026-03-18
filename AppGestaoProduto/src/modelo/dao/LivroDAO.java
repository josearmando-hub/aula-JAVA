package modelo.dao;

import modelo.Livro;
import util.ConectaBanco; 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Armando
 */
public class LivroDAO {

    public LivroDAO() {
    }

    // CREATE: ATENÇÃO AQUI! Mudei de "void" para "boolean" e tirei o "throws SQLException"
    public boolean adicionar(Livro livro) {
        String sql = "INSERT INTO livro (titulo, autor, anoPublicacao, quantidadeDisponivel, categoria) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAnoPublicacao());
            stmt.setInt(4, livro.getQuantidadeDisponivel());
            stmt.setString(5, livro.getCategoria());
            stmt.executeUpdate();
            
            return true; // Retorna verdadeiro se deu tudo certo
            
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar livro: " + e.getMessage());
            return false; // Retorna falso se quebrar
        }
    }

    // READ
    public List<Livro> listarTodos() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro";
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Livro l = new Livro(
                    rs.getInt("idUnico"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("anoPublicacao"),
                    rs.getInt("quantidadeDisponivel"),
                    rs.getString("categoria")
                );
                livros.add(l);
            }
        }
        return livros;
    }

    // UPDATE
    public void atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE livro SET titulo=?, autor=?, anoPublicacao=?, quantidadeDisponivel=?, categoria=? WHERE idUnico=?";
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAnoPublicacao());
            stmt.setInt(4, livro.getQuantidadeDisponivel());
            stmt.setString(5, livro.getCategoria());
            stmt.setInt(6, livro.getIdUnico());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deletar(int idUnico) throws SQLException {
        String sql = "DELETE FROM livro WHERE idUnico=?";
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUnico);
            stmt.executeUpdate();
        }
    }
    
    // BUSCAR POR ID O LIVRO
    public Livro buscarPorId(int idUnico) {
        String sql = "SELECT * FROM livro WHERE idUnico = ?";
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idUnico);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Livro(
                    rs.getInt("idUnico"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("anoPublicacao"),
                    rs.getInt("quantidadeDisponivel"),
                    rs.getString("categoria")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livro: " + e.getMessage());
        }
        return null;
    }
}