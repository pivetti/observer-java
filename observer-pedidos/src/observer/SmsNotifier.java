package observer;

import model.Pedido;
import model.StatusPedido;

// ConcreteObserver: envia uma notificacao por SMS.
public class SmsNotifier implements Observer {
    @Override
    public void update(Pedido pedido, StatusPedido statusAnterior, StatusPedido novoStatus) {
        System.out.println("[SMS] " + pedido.getNomeCliente()
                + ", atualizacao do pedido #" + pedido.getId()
                + ": " + novoStatus + ".");
    }
}
