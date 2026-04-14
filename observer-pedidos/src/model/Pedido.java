package model;

import java.util.ArrayList;
import java.util.List;

import observer.Observer;
import subject.Subject;

// Subject concreto: objeto observado pelos notificadores.
public class Pedido implements Subject {
    private final int id;
    private final String nomeCliente;
    private StatusPedido status;
    private final List<Observer> observers = new ArrayList<>();

    public Pedido(int id, String nomeCliente, StatusPedido statusInicial) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.status = statusInicial;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserversAboutStatusChange(StatusPedido statusAnterior) {
        for (Observer observer : observers) {
            observer.update(this, statusAnterior, status);
        }
    }

    public void setStatus(StatusPedido novoStatus) {
        if (novoStatus == status) {
            return;
        }

        StatusPedido statusAnterior = status;
        status = novoStatus;

        // Ponto central do padrão Observer: a mudança de estado dispara a notificação.
        notifyObserversAboutStatusChange(statusAnterior);
    }

    public int getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public StatusPedido getStatus() {
        return status;
    }
}
