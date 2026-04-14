package app;

import model.Pedido;
import model.StatusPedido;
import observer.DashboardUpdater;
import observer.EmailNotifier;
import observer.HistoryLogger;
import observer.SmsNotifier;

public class Main {
    public static void main(String[] args) {
        Pedido pedido = new Pedido(1001, "Ana Silva", StatusPedido.EM_PROCESSAMENTO);

        EmailNotifier emailNotifier = new EmailNotifier();
        SmsNotifier smsNotifier = new SmsNotifier();
        DashboardUpdater dashboardUpdater = new DashboardUpdater();
        HistoryLogger historyLogger = new HistoryLogger();

        pedido.addObserver(emailNotifier);
        pedido.addObserver(smsNotifier);
        pedido.addObserver(dashboardUpdater);
        pedido.addObserver(historyLogger);

        System.out.println("Pedido criado: #" + pedido.getId()
                + " - Cliente: " + pedido.getNomeCliente()
                + " - Status inicial: " + pedido.getStatus());

        alterarStatusComTitulo(pedido, StatusPedido.PAGO);
        alterarStatusComTitulo(pedido, StatusPedido.ENVIADO);
        alterarStatusComTitulo(pedido, StatusPedido.ENTREGUE);

        historyLogger.exibirHistoricoCompleto();
    }

    private static void alterarStatusComTitulo(Pedido pedido, StatusPedido novoStatus) {
        System.out.println();
        System.out.println("--- Alterando status para " + novoStatus + " ---");
        pedido.setStatus(novoStatus);
    }
}
