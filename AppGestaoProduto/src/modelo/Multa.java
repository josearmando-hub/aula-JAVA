package modelo;

/**
 *
 * @author José Armando
 */

public class Multa {
    private int diasAtraso;
    private float valorTotal;
    private String statusPagamento; // "Pendente", "Pago"

    public Multa(int diasAtraso) {
        this.diasAtraso = diasAtraso;
        this.statusPagamento = "Pendente";
        this.valorTotal = calcularMulta();
    }

    // Método funcional do diagrama
    public float calcularMulta() {
        // Exemplo de regra: R$ 2.50 por dia de atraso
        float taxaDiaria = 2.50f;
        this.valorTotal = this.diasAtraso * taxaDiaria;
        return this.valorTotal;
    }

    public void quitarMulta() {
        this.statusPagamento = "Pago";
        this.valorTotal = 0.0f;
    }

    // Getters
    public int getDiasAtraso() { return diasAtraso; }
    public float getValorTotal() { return valorTotal; }
    public String getStatusPagamento() { return statusPagamento; }
    
    
    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}