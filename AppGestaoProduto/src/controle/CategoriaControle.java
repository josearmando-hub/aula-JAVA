package controle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Armando
 */
public class CategoriaControle {
        public static List<String> listarCategorias() throws ClassNotFoundException, SQLException {
        List<String> lista = new ArrayList<>();
        modelo.dao.CategoriaDAO dao = new modelo.dao.CategoriaDAO();
        List<modelo.Categoria> itens = dao.listarTodas();
        for (modelo.Categoria c : itens ) {
            lista.add(c.getNome());
        }
        return lista;
    }

    private static class SQLException extends Exception {

        public SQLException() {
        }
    }
}
