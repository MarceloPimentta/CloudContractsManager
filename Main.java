import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<FaturaMensal> faturas = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();
        List<ContratoServicoNuvem> contratos = new ArrayList<>();

        while (true) {
            System.out.println("\n=== Menu Interativo ===");
            System.out.println("1- Cadastrar usuário");
            System.out.println("2- Cadastrar contrato (SaaS, PaaS, IaaS)");
            System.out.println("3- Registrar utilização mensal e calcular fatura");
            System.out.println("4- Verificar se SLA foi cumprido");
            System.out.println("5- Gerar relatório de faturamento por cliente");
            System.out.println("0- Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do cliente: ");
                    String nomeCliente = scanner.nextLine();
                    System.out.print("Digite o identificador do cliente: ");
                    String idCliente = scanner.nextLine();
                    clientes.add(new Cliente(nomeCliente, idCliente));
                    System.out.println("Cliente cadastrado com sucesso!");
                    break;

                case 2:
                    if (clientes.isEmpty()) {
                        System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
                        break;
                    }
                    System.out.println("Selecione o cliente pelo índice:");
                    for (int i = 0; i < clientes.size(); i++) {
                        System.out.println(i + "- " + clientes.get(i).getNome());
                    }
                    int clienteIndex = scanner.nextInt();
                    scanner.nextLine();

                    if (clienteIndex < 0 || clienteIndex >= clientes.size()) {
                        System.out.println("Índice inválido. Tente novamente.");
                        break;
                    }
                    Cliente cliente = clientes.get(clienteIndex);
                    double sla;
                    while (true) {
                        System.out.print("Digite o SLA (ex: 99.9): ");
                        try {
                            sla = scanner.nextDouble();
                            if (sla >= 0.1 && sla <= 100.0) {
                                break; // Valor válido, sai do loop
                            } else {
                                System.out.println("O SLA deve estar entre 0.1 e 100.0. Tente novamente.");
                            }
                        } catch (NumberFormatException | InputMismatchException e) {
                            System.out.println("Entrada inválida. Por favor, digite um número decimal.");
                            scanner.nextLine(); // Limpa o buffer do scanner e solicita novo valor ao usuário
                        }
                    }
                    scanner.nextLine();
                    LocalDate dataInicio = null;
                    LocalDate dataFim = null;
                    while (true) {
                        try {
                            if (dataInicio == null) {
                                System.out.print("Digite a data de início (dd/mm/yyyy): ");
                                String inputDataInicio = scanner.nextLine();
                                dataInicio = LocalDate.parse(inputDataInicio, formatter);
                            }

                            if (dataFim == null) {
                                System.out.print("Digite a data de fim (dd/mm/yyyy): ");
                                String inputDataFim = scanner.nextLine();
                                dataFim = LocalDate.parse(inputDataFim, formatter);
                            }

                            if (dataInicio.isEqual(dataFim)) {
                                System.out.println("A data de início não pode ser igual à data de fim. Por favor, insira novamente.");
                                dataInicio = null;
                                dataFim = null;
                            } else if (dataInicio.isAfter(dataFim)) {
                                System.out.println("A data de início não pode ser posterior à data de fim. Por favor, insira novamente.");
                                dataInicio = null;
                                dataFim = null;
                            } else {
                                break; // Sai do loop se as datas forem válidas
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Formato inválido. Por favor, use o formato dd/mm/yyyy.");
                        }
                    }

                    System.out.print("Renovação automática? (S/N): ");
                    boolean renovacaoAutomatica = scanner.nextLine().equalsIgnoreCase("S");

                    System.out.println("Escolha o tipo de contrato: 1- SaaS, 2- PaaS, 3- IaaS");
                    int tipoContrato = scanner.nextInt();
                    scanner.nextLine();

                    switch (tipoContrato) {
                        case 1:
                            System.out.print("Digite o número de usuários: ");
                            int numeroUsuarios = scanner.nextInt();
                            contratos.add(new ContratoSaaS(cliente, sla, dataInicio, dataFim, renovacaoAutomatica, numeroUsuarios));
                            System.out.println("Contrato SaaS cadastrado com sucesso!");
                            break;
                        case 2:
                            System.out.print("Digite o uso de aplicações: ");
                            int usoAplicacoes = scanner.nextInt();
                            System.out.print("Digite o uso de banco de dados (em GB): ");
                            int usoBancoDados = scanner.nextInt();
                            contratos.add(new ContratoPaaS(cliente, sla, dataInicio, dataFim, renovacaoAutomatica, usoAplicacoes, usoBancoDados));
                            System.out.println("Contrato PaaS cadastrado com sucesso!");
                            break;
                        case 3:
                            System.out.print("Digite o uso de CPU (em horas): ");
                            double usoCPU = scanner.nextDouble();
                            System.out.print("Digite o uso de armazenamento (em GB): ");
                            double usoArmazenamento = scanner.nextDouble();
                            contratos.add(new ContratoIaaS(cliente, sla, dataInicio, dataFim, renovacaoAutomatica, usoCPU, usoArmazenamento));
                            System.out.println("Contrato IaaS cadastrado com sucesso!");
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                    break;

                case 3:
                    if (contratos.isEmpty()) {
                        System.out.println("Nenhum contrato cadastrado. Cadastre um contrato primeiro.");
                        break;
                    }
                    System.out.println("Selecione o contrato pelo índice:");
                    for (int i = 0; i < contratos.size(); i++) {
                        System.out.println(i + "- " + contratos.get(i).gerarResumoContrato());
                    }
                    int contratoIndex = scanner.nextInt();
                    scanner.nextLine();

                    ContratoServicoNuvem contrato = contratos.get(contratoIndex);
                    System.out.print("Digite o mês de referência (MM/yyyy): ");
                    LocalDate mesReferencia = LocalDate.parse("01/" + scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    double valorFatura = contrato.calcularFatura();
                    faturas.add(new FaturaMensal(contrato.getCliente(), mesReferencia, valorFatura));
                    System.out.println("Fatura registrada com sucesso!");
                    break;

                case 4:
                    if (contratos.isEmpty()) {
                        System.out.println("Nenhum contrato cadastrado. Cadastre um contrato primeiro.");
                        break;
                    }
                    System.out.println("Selecione o contrato pelo índice:");
                    for (int i = 0; i < contratos.size(); i++) {
                        System.out.println(i + "- " + contratos.get(i).gerarResumoContrato());
                    }
                    contratoIndex = scanner.nextInt();
                    scanner.nextLine();

                    contrato = contratos.get(contratoIndex);
                    boolean slaCumprido = contrato.verificarSLA();
                    System.out.println("SLA cumprido? " + slaCumprido);
                    break;

                case 5:
                    if (clientes.isEmpty()) {
                        System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
                        break;
                    }
                    System.out.println("Selecione o cliente pelo índice:");
                    for (int i = 0; i < clientes.size(); i++) {
                        System.out.println(i + "- " + clientes.get(i).getNome());
                    }
                    clienteIndex = scanner.nextInt();
                    scanner.nextLine();

                    cliente = clientes.get(clienteIndex);
                    RelatorioFinanceiro relatorio = new RelatorioFinanceiro(faturas);
                    System.out.println(relatorio.gerarRelatorioPorCliente(cliente));
                    break;

                case 0:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}