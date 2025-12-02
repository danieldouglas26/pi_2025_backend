package com.lixo.gerenciamento.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lixo.gerenciamento.model.dto.ItinerarioDTO;
import com.lixo.gerenciamento.model.entity.Caminhao;
import com.lixo.gerenciamento.model.entity.Itinerario;
import com.lixo.gerenciamento.model.entity.Rota;
import com.lixo.gerenciamento.model.mapper.ItinerarioMapper;
import com.lixo.gerenciamento.pattern.observer.ItinerarioSubject;
import com.lixo.gerenciamento.repository.ItinerarioRepository;
import com.lixo.gerenciamento.repository.RotaRepository;

@Service
public class PlanejamentoService {
    
    @Autowired
    private ItinerarioRepository itinerarioRepository;
    
    @Autowired
    private RotaRepository rotaRepository;
    
    @Autowired
    private CaminhaoService caminhaoService;
    
    @Autowired
    private ItinerarioMapper itinerarioMapper;
    
    @Autowired
    private ItinerarioSubject itinerarioSubject;
    
    public ItinerarioDTO agendarItinerario(Long rotaId, Long caminhaoId, LocalDate data) {
        Rota rota = rotaRepository.findById(rotaId)
                .orElseThrow(() -> new RuntimeException("Rota não encontrada com ID: " + rotaId));
        
        Caminhao caminhao = caminhaoService.findEntityById(caminhaoId);
        
        validarAgendamento(caminhao, data);
        
        Itinerario itinerario = Itinerario.builder()
                .rota(rota)
                .data(data)
                .status(Itinerario.StatusItinerario.AGENDADO)
                .build();
        
        Itinerario salvo = itinerarioRepository.save(itinerario);
        itinerarioSubject.notificarCriacao(salvo);
        
        return itinerarioMapper.toDTO(salvo);
    }
    
    private void validarAgendamento(Caminhao caminhao, LocalDate data) {
        // Verificar se caminhão já tem itinerário na data
        List<Itinerario> itinerarios = itinerarioRepository.findByCaminhaoAndData(caminhao, data);
        if (!itinerarios.isEmpty()) {
            throw new IllegalStateException(
                "Caminhão " + caminhao.getPlaca() + " já possui itinerário agendado para " + data
            );
        }
        
        // Verificar compatibilidade de tipos de resíduo
        // (implementar lógica específica baseada na rota)
    }
    
    public List<ItinerarioDTO> gerarCronogramaMensal(Integer mes, Integer ano) {
        List<Itinerario> itinerarios = itinerarioRepository.findByMesAndAno(mes, ano);
        return itinerarios.stream()
                .map(itinerarioMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<ItinerarioDTO> consultarItinerariosPorCaminhao(Long caminhaoId, LocalDate dataInicio, LocalDate dataFim) {
        Caminhao caminhao = caminhaoService.findEntityById(caminhaoId);
        List<Itinerario> itinerarios = itinerarioRepository.findByCaminhaoAndPeriodo(caminhao, dataInicio, dataFim);
        return itinerarios.stream()
                .map(itinerarioMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<ItinerarioDTO> consultarItinerariosPorData(LocalDate data) {
        List<Itinerario> itinerarios = itinerarioRepository.findByData(data);
        return itinerarios.stream()
                .map(itinerarioMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public ItinerarioDTO atualizarStatusItinerario(Long id, String status) {
        Itinerario itinerario = itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado com ID: " + id));
        
        try {
            Itinerario.StatusItinerario novoStatus = Itinerario.StatusItinerario.valueOf(status.toUpperCase());
            itinerario.setStatus(novoStatus);
            
            Itinerario atualizado = itinerarioRepository.save(itinerario);
            itinerarioSubject.notificarAtualizacao(atualizado);
            
            return itinerarioMapper.toDTO(atualizado);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }
    
    public void cancelarItinerario(Long id) {
        Itinerario itinerario = itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado com ID: " + id));
        
        itinerario.setStatus(Itinerario.StatusItinerario.CANCELADO);
        itinerarioRepository.save(itinerario);
        itinerarioSubject.notificarCancelamento(itinerario);
    }
    
    public ItinerarioDTO findItinerarioById(Long id) {
        Itinerario itinerario = itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado com ID: " + id));
        return itinerarioMapper.toDTO(itinerario);
    }
    
    private Caminhao findCaminhaoEntityById(Long id) {
        // Método auxiliar para buscar entidade Caminhao
        // Implementação similar ao findEntityById do CaminhaoService
        return null; // Placeholder
    }
}