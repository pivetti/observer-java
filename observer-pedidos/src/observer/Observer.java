package observer;

import model.Pedido;
import model.StatusPedido;

// Observer: contrato implementado por todos que reagem a mudancas no pedido.
public interface Observer {
    void update(Pedido pedido, StatusPedido statusAnterior, StatusPedido novoStatus);
}
