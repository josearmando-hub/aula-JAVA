package util;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author José Armando
 */
public class ConectaBanco {
    public static java.sql.Connection getConexao() {
        java.sql.Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/dbbiblioteca", "root", "");
        }catch(ClassNotFoundException | java.sql.SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
        return con;
    }

    public static Connection obtemConexao() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    }
