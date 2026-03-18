package modelo;
import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author José Armando
 */
public class Emprestimo {
    private Date dataRetirada;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoReal;
    private String situacao; // Ex: "Ativo", "Devolvido", "Atrasado"
    
    // Relacionamentos do diagrama (1 Emprestimo tem 1 Usuario e 1 Livro)
    private Usuario usuario;
    private Livro livro;

    public Emprestimo(Usuario usuario, Livro livro) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataRetirada = new Date(); // Pega a data atual
        this.situacao = "Ativo";
        
        // Exemplo: Define a devolução para 7 dias a partir de hoje
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.dataRetirada);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        this.dataDevolucaoPrevista = cal.getTime();
    }

    // Métodos funcionais do diagrama
    public void finalizarEmprestimo() {
        this.dataDevolucaoReal = new Date(); // Data de hoje
        this.situacao = "Devolvido";
        this.livro.atualizarEstoque(1); // Devolve 1 para o estoque
    }

    public boolean verificarAtraso() {
        Date hoje = new Date();
        // Retorna true se a data de hoje for depois da data prevista e ainda não foi devolvido
        return situacao.equals("Ativo") && hoje.after(this.dataDevolucaoPrevista);
    }

    // Getters e Setters
    public Date getDataRetirada() { return dataRetirada; }
    
    // NOVO: Adicionado para o DAO funcionar
    public void setDataRetirada(Date dataRetirada) { 
        this.dataRetirada = dataRetirada; 
    }
    
    public Date getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    
    // NOVO: Adicionado para o DAO funcionar
    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) { 
        this.dataDevolucaoPrevista = dataDevolucaoPrevista; 
    }
    
    public Date getDataDevolucaoReal() { return dataDevolucaoReal; }
    
    // NOVO: Adicionado para o DAO funcionar
    public void setDataDevolucaoReal(Date dataDevolucaoReal) { 
        this.dataDevolucaoReal = dataDevolucaoReal; 
    }
    
    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }
    public Usuario getUsuario() { return usuario; }
    public Livro getLivro() { return livro; }
}