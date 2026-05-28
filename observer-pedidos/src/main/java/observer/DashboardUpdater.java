package observer;

import model.Pedido;
import model.StatusPedido;

// ConcreteObserver: atualiza a visao administrativa.
public class DashboardUpdater implements Observer {
    @Override
    public void update(Pedido pedido, StatusPedido statusAnterior, StatusPedido novoStatus) {
        System.out.println("[DASHBOARD] Pedido #" + pedido.getId()
                + " agora esta com status " + novoStatus + ".");
    }
}
