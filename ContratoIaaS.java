import java.time.LocalDate;

public class ContratoIaaS extends ContratoServicoNuvem implements Faturavel, Renovavel, RelatorioGeravel {
    private double usoCPU; // em horas
    private double usoArmazenamento; // em GB

    public ContratoIaaS(Cliente cliente, double sla, LocalDate dataInicio, LocalDate dataFim, boolean renovacaoAutomatica,
                        double usoCPU, double usoArmazenamento) {
        super(cliente, sla, dataInicio, dataFim, renovacaoAutomatica);
        this.usoCPU = usoCPU;
        this.usoArmazenamento = usoArmazenamento;
    }

    @Override
    public double calcularFatura() {
        double custoCPU = usoCPU * 0.50; // Custo por hora de processamento
        double custoArmazenamento = usoArmazenamento * 0.10; // Custo por GB
        double custoTotal = custoCPU + custoArmazenamento;

        // Aplicar penalidade se SLA violado
        if (!verificarSLA()) {
            custoTotal *= 0.9; // desconto de 10% como penalidade
        }
        return custoTotal;
    }

    @Override
    public boolean verificarSLA() {
        // Implementar lógica de verificação de SLA para IaaS
        // Exemplo simplificado: verificar se SLA é "99.9%" ou superior
        return sla >=99.9;
    }

    @Override
    public void renovarContrato() {
        if (renovacaoAutomatica) {
            dataInicio = dataFim;
            dataFim = dataFim.plusMonths(1);
        } else {
            // Renovação manual - pode ser implementada conforme necessidade
            System.out.println("Renovação manual necessária para o contrato IaaS do cliente " + cliente.getNome());
        }
    }

    @Override
    public String gerarResumoContrato() {
        return "Contrato IaaS para cliente " + cliente.getNome() +
                ", SLA: " + sla +
                ", Período: " + dataInicio + " a " + dataFim +
                ", Renovação automática: " + renovacaoAutomatica;
    }
}
