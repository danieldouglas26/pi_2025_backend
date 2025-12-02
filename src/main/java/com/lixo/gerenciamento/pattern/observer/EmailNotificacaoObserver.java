package com.lixo.gerenciamento.pattern.observer;

import org.springframework.stereotype.Component;

import com.lixo.gerenciamento.model.entity.Itinerario;

@Component
public class EmailNotificacaoObserver implements ItinerarioObserver {
    
    @Override
    public void onItinerarioCriado(Itinerario itinerario) {
        String mensagem = String.format(
            "📧 Email de Notificação:\n" +
            "Novo itinerário criado!\n" +
            "ID: %d\n" +
            "Data: %s\n" +
            "Caminhão: %s\n" +
            "Motorista: %s",
            itinerario.getId(),
            itinerario.getData(),
            itinerario.getRota().getCaminhao().getPlaca(),
            itinerario.getRota().getCaminhao().getMotorista()
        );
        System.out.println(mensagem);
    }
    
    @Override
    public void onItinerarioAtualizado(Itinerario itinerario) {
        String mensagem = String.format(
            "📧 Email de Notificação:\n" +
            "Itinerário atualizado!\n" +
            "ID: %d\n" +
            "Status: %s\n" +
            "Data: %s",
            itinerario.getId(),
            itinerario.getStatus(),
            itinerario.getData()
        );
        System.out.println(mensagem);
    }
    
    @Override
    public void onItinerarioCancelado(Itinerario itinerario) {
        String mensagem = String.format(
            "📧 Email de Notificação:\n" +
            "Itinerário CANCELADO!\n" +
            "ID: %d\n" +
            "Data: %s\n" +
            "Caminhão: %s",
            itinerario.getId(),
            itinerario.getData(),
            itinerario.getRota().getCaminhao().getPlaca()
        );
        System.out.println(mensagem);
    }
}
