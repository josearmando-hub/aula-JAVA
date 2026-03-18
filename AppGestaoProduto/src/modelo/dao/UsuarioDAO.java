package modelo.dao;

import modelo.Aluno;
import modelo.Professor;
import modelo.Usuario;
import modelo.Funcionario;
import util.ConectaBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Armando
 */
public class UsuarioDAO {
    
    // 1. Método para Salvar (Insert) - Ajustado para 'adicionar' e com boolean!
    public boolean adicionar(Usuario usuario) {
        String sql = "INSERT INTO usuario (matricula, nome, endereco, tipo, curso, departamento) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getMatricula());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEndereco());
            
            // A mágica do Polimorfismo limpa e sem repetições
            if (usuario instanceof Aluno) {
                stmt.setString(4, "ALUNO");
                stmt.setString(5, ((Aluno) usuario).getCurso());
                stmt.setString(6, null); // Aluno não tem departamento
            } else if (usuario instanceof Professor) {
                stmt.setString(4, "PROFESSOR");
                stmt.setString(5, null); // Professor não tem curso
                stmt.setString(6, ((Professor) usuario).getDepartamento());
            } else if (usuario instanceof Funcionario) {
                stmt.setString(4, "FUNCIONARIO");
                stmt.setString(5, null);
                stmt.setString(6, null);
            }

            stmt.executeUpdate();
            return true; // Retorna true indicando sucesso
            
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false; // Retorna false se der erro
        }
    }

    // 2. Método para Listar (Select All)
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                Usuario u = null;
                
                // Recria o objeto correto baseado no tipo salvo no banco (Incluindo Funcionario!)
                if ("ALUNO".equals(tipo)) {
                    u = new Aluno(
                        rs.getString("nome"), 
                        rs.getString("matricula"), 
                        rs.getString("endereco"), 
                        rs.getString("curso")
                    );
                } else if ("PROFESSOR".equals(tipo)) {
                    u = new Professor(
                        rs.getString("nome"), 
                        rs.getString("matricula"), 
                        rs.getString("endereco"), 
                        rs.getString("departamento")
                    );
                } else if ("FUNCIONARIO".equals(tipo)) {
                    u = new Funcionario(
                        rs.getString("nome"), 
                        rs.getString("matricula"), 
                        rs.getString("endereco")
                    );
                }
                
                if (u != null) {
                    usuarios.add(u);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }

    // 3. Método para Buscar Específico (Select One)
    public Usuario buscarPorMatricula(String matricula) {
        String sql = "SELECT * FROM usuario WHERE matricula = ?";
        
        try (Connection conn = ConectaBanco.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                
                // Incluindo a busca pelo Funcionario também
                if ("ALUNO".equals(tipo)) {
                    return new Aluno(
                        rs.getString("nome"), 
                        rs.getString("matricula"), 
                        rs.getString("endereco"), 
                        rs.getString("curso")
                    );
                } else if ("PROFESSOR".equals(tipo)) {
                    return new Professor(
                        rs.getString("nome"), 
                        rs.getString("matricula"), 
                        rs.getString("endereco"), 
                        rs.getString("departamento")
                    );
                } else if ("FUNCIONARIO".equals(tipo)) {
                    return new Funcionario(
                        rs.getString("nome"), 
                        rs.getString("matricula"), 
                        rs.getString("endereco")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário pela matrícula: " + e.getMessage());
        }
        return null; 
    }
}