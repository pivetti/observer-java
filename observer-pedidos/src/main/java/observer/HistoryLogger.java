package observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Pedido;
import model.StatusPedido;

// ConcreteObserver: registra cada mudanca de status.
public class HistoryLogger implements Observer {
    private final List<String> historico = new ArrayList<>();

    @Override
    public void update(Pedido pedido, StatusPedido statusAnterior, StatusPedido novoStatus) {
        String registro = "Pedido #" + pedido.getId()
                + ": " + statusAnterior
                + " -> " + novoStatus;

        historico.add(registro);
        System.out.println("[HISTORICO] " + registro);
    }

    public List<String> getHistorico() {
        return Collections.unmodifiableList(historico);
    }

    public void exibirHistoricoCompleto() {
        System.out.println();
        System.out.println("=== Historico completo ===");

        if (historico.isEmpty()) {
            System.out.println("Nenhuma alteracao de status registrada.");
            return;
        }

        for (int i = 0; i < historico.size(); i++) {
            System.out.println((i + 1) + ". " + historico.get(i));
        }
    }
}
