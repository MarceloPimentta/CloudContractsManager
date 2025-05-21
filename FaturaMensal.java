import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FaturaMensal {
    private Cliente cliente;
    private LocalDate mesReferencia;
    private double valor;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FaturaMensal(Cliente cliente, LocalDate mesReferencia, double valor) {
        this.cliente = cliente;
        this.mesReferencia = mesReferencia;
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getMesReferencia() {
        return mesReferencia;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Cliente: " + "(" + cliente + ")" + "\n" +
                "    Mês de Referência: " + mesReferencia.format(formatter) + "\n" +
                "    Valor: R$ " + String.format("%.2f", valor) + "\n";
    }
}
