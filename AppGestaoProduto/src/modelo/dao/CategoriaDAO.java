package modelo.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;

        
/**
 *
 * @author José Armando
 */
public class CategoriaDAO {
    private static final String LISTAR = "SELECT * FROM dbloja.categorias";
    public List<modelo.Categoria> listarTodas() {
        ResultSet rs = null;
        Statement stmt = null;
        Connection con = null;
        List<modelo.Categoria> categs = new ArrayList<Categoria>();
        try {
            con = util.ConectaBanco.obtemConexao();
            stmt = con.createStatement();
            rs = stmt.executeQuery(LISTAR);
            while (rs.next()) {
                Categoria cat = new Categoria(rs.getInt("id"), rs.getString("nome"));
                categs.add(cat);
            }
        } catch (SQLException err1) {
            throw new RuntimeException(err1);
        } finally {
            try {
                con.close();
                stmt.close();
            } catch (SQLException err2) {
                throw new RuntimeException(err2);
            }
        }
        return categs;
    }
}
