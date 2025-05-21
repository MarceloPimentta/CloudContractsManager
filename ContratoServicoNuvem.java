import java.time.LocalDate;

public abstract class ContratoServicoNuvem {
    protected Cliente cliente;
    protected double sla;
    protected LocalDate dataInicio;
    protected LocalDate dataFim;
    protected boolean renovacaoAutomatica;

    public ContratoServicoNuvem(Cliente cliente, double sla, LocalDate dataInicio, LocalDate dataFim, boolean renovacaoAutomatica) {
        this.cliente = cliente;
        this.sla = sla;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.renovacaoAutomatica = renovacaoAutomatica;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getSla() {
        return sla;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public boolean isRenovacaoAutomatica() {
        return renovacaoAutomatica;
    }

    public abstract double calcularFatura();

    public abstract boolean verificarSLA();

    public abstract void renovarContrato();


    public String gerarResumoContrato() {
        return "Cliente: " + cliente.getNome() + ", SLA: " + sla + "%, Início: " + dataInicio + ", Fim: " + dataFim + ", Renovação Automática: " + (renovacaoAutomatica ? "Sim" : "Não");
    }
}
