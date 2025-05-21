import java.time.LocalDate;

public class HistoricoSLA {
    private LocalDate data;
    private boolean slaCumprido;
    private String descricao;

    public HistoricoSLA(LocalDate data, boolean slaCumprido, String descricao) {
        this.data = data;
        this.slaCumprido = slaCumprido;
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public boolean isSlaCumprido() {
        return slaCumprido;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "HistoricoSLA{" +
                "data=" + data +
                ", slaCumprido=" + slaCumprido +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
