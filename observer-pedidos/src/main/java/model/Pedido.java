package model;

import java.util.ArrayList;
import java.util.List;

import observer.Observer;
import subject.Subject;

// ConcreteSubject: objeto observado pelos notificadores.
public class Pedido implements Subject {
    private final int id;
    private final String nomeCliente;
    private StatusPedido status;
    private final List<Observer> observers = new ArrayList<>();

    public Pedido(int id, String nomeCliente, StatusPedido statusInicial) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id do pedido deve ser maior que zero.");
        }
        if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente nao pode ser nulo ou vazio.");
        }
        if (statusInicial == null) {
            throw new IllegalArgumentException("O status inicial nao pode ser nulo.");
        }

        this.id = id;
        this.nomeCliente = nomeCliente.trim();
        this.status = statusInicial;
    }

    @Override
    public void addObserver(Observer observer) {
        if (observer == null) {
            throw new IllegalArgumentException("O observador nao pode ser nulo.");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observer != null) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers(StatusPedido statusAnterior) {
        for (Observer observer : new ArrayList<>(observers)) {
            observer.update(this, statusAnterior, status);
        }
    }

    public void setStatus(StatusPedido novoStatus) {
        if (novoStatus == null) {
            throw new IllegalArgumentException("O novo status nao pode ser nulo.");
        }
        if (novoStatus == status) {
            return;
        }

        StatusPedido statusAnterior = status;
        status = novoStatus;

        // Ponto central do padrao Observer: a mudanca de estado dispara a notificacao.
        notifyObservers(statusAnterior);
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
