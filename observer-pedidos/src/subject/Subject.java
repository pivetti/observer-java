package subject;

import model.StatusPedido;
import observer.Observer;

// Subject: define como observadores podem se cadastrar, sair e ser notificados.
public interface Subject {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(StatusPedido statusAnterior);
}
