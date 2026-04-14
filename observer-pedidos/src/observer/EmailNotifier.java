package observer;

import model.Pedido;
import model.StatusPedido;

// ConcreteObserver: envia uma notificacao por e-mail.
public class EmailNotifier implements Observer {
    @Override
    public void update(Pedido pedido, StatusPedido statusAnterior, StatusPedido novoStatus) {
        System.out.println("[E-MAIL] Ola, " + pedido.getNomeCliente()
                + "! Seu pedido #" + pedido.getId()
                + " mudou de " + statusAnterior
                + " para " + novoStatus + ".");
    }
}
