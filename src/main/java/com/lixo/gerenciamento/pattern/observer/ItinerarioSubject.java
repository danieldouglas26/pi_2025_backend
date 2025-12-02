package com.lixo.gerenciamento.pattern.observer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lixo.gerenciamento.model.entity.Itinerario;

@Component
public class ItinerarioSubject {
    
    private final List<ItinerarioObserver> observers;
    
    @Autowired
    public ItinerarioSubject(List<ItinerarioObserver> observers) {
        this.observers = new ArrayList<>();
        if (observers != null) {
            this.observers.addAll(observers);
        }
    }
    
    public void addObserver(ItinerarioObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    public void removeObserver(ItinerarioObserver observer) {
        observers.remove(observer);
    }
    
    public void notificarCriacao(Itinerario itinerario) {
        for (ItinerarioObserver observer : observers) {
            try {
                observer.onItinerarioCriado(itinerario);
            } catch (Exception e) {
                System.err.println("Erro ao notificar observer: " + e.getMessage());
            }
        }
    }
    
    public void notificarAtualizacao(Itinerario itinerario) {
        for (ItinerarioObserver observer : observers) {
            try {
                observer.onItinerarioAtualizado(itinerario);
            } catch (Exception e) {
                System.err.println("Erro ao notificar observer: " + e.getMessage());
            }
        }
    }
    
    public void notificarCancelamento(Itinerario itinerario) {
        for (ItinerarioObserver observer : observers) {
            try {
                observer.onItinerarioCancelado(itinerario);
            } catch (Exception e) {
                System.err.println("Erro ao notificar observer: " + e.getMessage());
            }
        }
    }
}
