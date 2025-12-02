package com.lixo.gerenciamento.pattern.observer;

import com.lixo.gerenciamento.model.entity.Itinerario;

public interface ItinerarioObserver {
    void onItinerarioCriado(Itinerario itinerario);
    void onItinerarioAtualizado(Itinerario itinerario);
    void onItinerarioCancelado(Itinerario itinerario);
}
