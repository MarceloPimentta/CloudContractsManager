public class Cliente {
    private String nome;
    private String identificador;

    public Cliente(String nome, String identificador) {
        this.nome = nome;
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public String getIdentificador() {
        return identificador;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", identificador='" + identificador + '\'' +
                '}';
    }
}
