import java.time.LocalDate;

public class ContratoPaaS extends ContratoServicoNuvem implements Faturavel, Renovavel, RelatorioGeravel {
    private double usoAplicacoes; // número de aplicações usadas
    private double usoBancoDados; // em GB

    public ContratoPaaS(Cliente cliente, double sla, LocalDate dataInicio, LocalDate dataFim, boolean renovacaoAutomatica,
                        double usoAplicacoes, double usoBancoDados) {
        super(cliente, sla, dataInicio, dataFim, renovacaoAutomatica);
        this.usoAplicacoes = usoAplicacoes;
        this.usoBancoDados = usoBancoDados;
    }

    @Override
    public double calcularFatura() {
        double custoAplicacoes = usoAplicacoes * 10.0; // exemplo: 10 por aplicação
        double custoBancoDados = usoBancoDados * 0.5; // exemplo: 0.5 por GB
        double custoTotal = custoAplicacoes + custoBancoDados;

        // Aplicar penalidade se SLA violado
        if (!verificarSLA()) {
            custoTotal *= 0.85; // desconto de 15% como penalidade
        }
        return custoTotal;
    }

    @Override
    public boolean verificarSLA() {
        // Implementar lógica de verificação de SLA para PaaS
        // Exemplo simplificado: verificar se SLA é "99.5%" ou superior
        return sla >= 99.5;
    }

    @Override
    public void renovarContrato() {
        if (renovacaoAutomatica) {
            dataInicio = dataFim;
            dataFim = dataFim.plusMonths(1);
        } else {
            // Renovação manual - pode ser implementada conforme necessidade
            System.out.println("Renovação manual necessária para o contrato PaaS do cliente " + cliente.getNome());
        }
    }

    @Override
    public String gerarResumoContrato() {
        return "Contrato PaaS para cliente " + cliente.getNome() +
                ", SLA: " + sla +
                ", Período: " + dataInicio + " a " + dataFim +
                ", Renovação automática: " + renovacaoAutomatica;
    }
}
