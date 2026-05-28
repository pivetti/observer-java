package app;

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
        exibirSecao("1. Criacao do pedido");
        Pedido pedido = new Pedido(1001, "Ana Silva", StatusPedido.EM_PROCESSAMENTO);
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
        alterarStatus(pedido, StatusPedido.PAGO);
        alterarStatus(pedido, StatusPedido.ENVIADO);

        exibirSecao("4. Tentativa de repetir o mesmo status");
        System.out.println("Status atual: " + pedido.getStatus());
        System.out.println("Tentando alterar novamente para " + pedido.getStatus() + ".");
        pedido.setStatus(pedido.getStatus());
        System.out.println("Nenhuma notificacao foi exibida porque o status nao mudou.");

        exibirSecao("5. Remocao de um observador");
        pedido.removeObserver(smsNotifier);
        System.out.println("SmsNotifier removido. Ele nao recebera as proximas notificacoes.");

        exibirSecao("6. Nova alteracao apos a remocao");
        alterarStatus(pedido, StatusPedido.ENTREGUE);

        exibirSecao("7. Historico registrado pelo HistoryLogger");
        historyLogger.exibirHistoricoCompleto();
    }

    private static void cadastrarObserver(Pedido pedido, Observer observer, String nomeObserver) {
        pedido.addObserver(observer);
        System.out.println(nomeObserver + " cadastrado.");
    }

    private static void alterarStatus(Pedido pedido, StatusPedido novoStatus) {
        System.out.println();
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
