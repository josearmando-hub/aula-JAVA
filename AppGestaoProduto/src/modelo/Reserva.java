package modelo;
import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author José Armando
 */
public class Reserva {
    private Date dataSolicitacao;
    private Date dataExpiracao;
    private String status; // Ex: "Ativa", "Expirada", "Convertida"
    
    // Relacionamentos do diagrama UML
    private Usuario usuario;
    private Livro livro;

    public Reserva(Usuario usuario, Livro livro) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataSolicitacao = new Date();
        this.status = "Ativa";
        
        // Exemplo: A reserva vale por 5 dias. Se ele não vier buscar, ela expira.
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.dataSolicitacao);
        cal.add(Calendar.DAY_OF_MONTH, 5);
        this.dataExpiracao = cal.getTime();
    }

    // Métodos do seu Diagrama UML
    public boolean verificarExpiracao() {
        Date hoje = new Date();
        if (this.status.equals("Ativa") && hoje.after(this.dataExpiracao)) {
            this.status = "Expirada";
            return true;
        }
        return false;
    }

    public Emprestimo converterParaEmprestimo() {
        this.status = "Convertida";
        // Cria um novo empréstimo usando o usuário e o livro desta reserva
        return new Emprestimo(this.usuario, this.livro);
    }

    // Getters e Setters
    public Date getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(Date dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }
    
    public Date getDataExpiracao() { return dataExpiracao; }
    public void setDataExpiracao(Date dataExpiracao) { this.dataExpiracao = dataExpiracao; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Usuario getUsuario() { return usuario; }
    public Livro getLivro() { return livro; }
}