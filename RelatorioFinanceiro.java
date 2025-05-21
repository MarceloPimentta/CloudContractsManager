import java.util.List;
import java.util.stream.Collectors;

public class RelatorioFinanceiro {
    private List<FaturaMensal> faturas;

    public RelatorioFinanceiro(List<FaturaMensal> faturas) {
        this.faturas = faturas;
    }

    public String gerarRelatorioPorTipoServico(String tipoServico) {
        List<FaturaMensal> faturasFiltradas = faturas.stream()
                .filter(f -> f.getClass().getSimpleName().equalsIgnoreCase(tipoServico))
                .collect(Collectors.toList());

        double total = faturasFiltradas.stream()
                .mapToDouble(FaturaMensal::getValor)
                .sum();

        return "Relatório Financeiro para " + tipoServico + ": Total faturado = " + total;
    }

    public String gerarRelatorioPorCliente(Cliente cliente) {
        List<FaturaMensal> faturasFiltradas = faturas.stream()
                .filter(f -> f.getCliente().equals(cliente))
                .collect(Collectors.toList());

        double total = faturasFiltradas.stream()
                .mapToDouble(FaturaMensal::getValor)
                .sum();

        return "Relatório Financeiro para o(a) cliente " + cliente.getNome() + ": Total faturado = R$" + total;
    }
}
