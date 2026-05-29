package app;

import java.util.Scanner;

import model.Pedido;
import model.StatusPedido;
import observer.DashboardUpdater;
import observer.EmailNotifier;
import observer.HistoryLogger;
import observer.Observer;
import observer.SmsNotifier;

public class Main {
    private static boolean primeiraSecao = true;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            exibirSecao("1. Criacao do pedido");
            String nomeCliente = lerNomeCliente(scanner);
            Pedido pedido = new Pedido(nomeCliente, StatusPedido.EM_PROCESSAMENTO);
            System.out.println("Pedido criado: #" + pedido.getId()
                    + " - Cliente: " + pedido.getNomeCliente()
                    + " - Status inicial: " + pedido.getStatus());

            EmailNotifier emailNotifier = new EmailNotifier();
            SmsNotifier smsNotifier = new SmsNotifier();
            DashboardUpdater dashboardUpdater = new DashboardUpdater();
            HistoryLogger historyLogger = new HistoryLogger();

            exibirSecao("2. Cadastro dos observadores");
            cadastrarObserver(pedido, emailNotifier, "EmailNotifier");
            cadastrarObserver(pedido, smsNotifier, "SmsNotifier");
            cadastrarObserver(pedido, dashboardUpdater, "DashboardUpdater");
            cadastrarObserver(pedido, historyLogger, "HistoryLogger");

            exibirSecao("3. Alteracoes de status");
            executarMenuStatus(scanner, pedido);

            exibirSecao("4. Historico registrado pelo HistoryLogger");
            historyLogger.exibirHistoricoCompleto();
        }
    }

    private static void cadastrarObserver(Pedido pedido, Observer observer, String nomeObserver) {
        pedido.addObserver(observer);
        System.out.println(nomeObserver + " cadastrado.");
    }

    private static String lerNomeCliente(Scanner scanner) {
        while (true) {
            System.out.print("Digite o nome do cliente: ");
            String nomeCliente = scanner.nextLine().trim();

            if (!nomeCliente.isEmpty()) {
                return nomeCliente;
            }

            System.out.println("Nome invalido. Tente novamente.");
        }
    }

    private static void executarMenuStatus(Scanner scanner, Pedido pedido) {
        boolean continuar = true;

        while (continuar) {
            exibirMenuStatus(pedido);
            System.out.print("Escolha uma opcao: ");
            String opcao = scanner.nextLine().trim();

            if ("0".equals(opcao)) {
                continuar = false;
                continue;
            }

            StatusPedido novoStatus = buscarStatusPorOpcao(opcao);

            if (novoStatus == null) {
                System.out.println("Opcao invalida. Tente novamente.");
                continue;
            }

            alterarStatus(pedido, novoStatus);
        }
    }

    private static void exibirMenuStatus(Pedido pedido) {
        StatusPedido[] statusDisponiveis = StatusPedido.values();

        System.out.println();
        System.out.println("Status atual: " + pedido.getStatus());
        System.out.println("Escolha o novo status do pedido:");

        for (int i = 0; i < statusDisponiveis.length; i++) {
            System.out.println((i + 1) + " - " + statusDisponiveis[i]);
        }

        System.out.println("0 - Sair");
    }

    private static StatusPedido buscarStatusPorOpcao(String opcao) {
        try {
            int indice = Integer.parseInt(opcao) - 1;
            StatusPedido[] statusDisponiveis = StatusPedido.values();

            if (indice >= 0 && indice < statusDisponiveis.length) {
                return statusDisponiveis[indice];
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return null;
    }

    private static void alterarStatus(Pedido pedido, StatusPedido novoStatus) {
        System.out.println();
        if (pedido.getStatus() == novoStatus) {
            System.out.println("O pedido ja esta com status " + novoStatus + ".");
            System.out.println("Nenhuma notificacao foi enviada porque o status nao mudou.");
            return;
        }

        System.out.println("Alterando status para " + novoStatus + ":");
        pedido.setStatus(novoStatus);
    }

    private static void exibirSecao(String titulo) {
        if (!primeiraSecao) {
            System.out.println();
        }
        System.out.println("=== " + titulo + " ===");
        primeiraSecao = false;
    }
}
