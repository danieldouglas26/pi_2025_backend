package com.senaifatesg.pi2025.core.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senaifatesg.pi2025.common.BusinessException;
import com.senaifatesg.pi2025.common.ResourceNotFoundException;
import com.senaifatesg.pi2025.common.adapters.JGraphTAdapter;
import com.senaifatesg.pi2025.common.enums.TipoResiduo;
import com.senaifatesg.pi2025.core.models.Bairro;
import com.senaifatesg.pi2025.core.models.Caminhao;
import com.senaifatesg.pi2025.core.models.ParadaRota;
import com.senaifatesg.pi2025.core.models.PontoColeta;
import com.senaifatesg.pi2025.core.models.Rota;
import com.senaifatesg.pi2025.core.models.Rua;
import com.senaifatesg.pi2025.core.repositories.BairroRepository;
import com.senaifatesg.pi2025.core.repositories.CaminhaoRepository;
import com.senaifatesg.pi2025.core.repositories.ParadaPontoColeta;
import com.senaifatesg.pi2025.core.repositories.ParadaPontoColetaRepository;
import com.senaifatesg.pi2025.core.repositories.ParadaRotaRepository;
import com.senaifatesg.pi2025.core.repositories.PontoColetaRepository;
import com.senaifatesg.pi2025.core.repositories.RotaRepository;
import com.senaifatesg.pi2025.core.repositories.RuaRepository;
import com.senaifatesg.pi2025.presentation.dtos.mappers.RotaMapper;
import com.senaifatesg.pi2025.presentation.dtos.requests.RotaRequestDTO;
import com.senaifatesg.pi2025.presentation.dtos.responses.RotaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RotaService {

    private final RotaRepository rotaRepository;
    private final CaminhaoRepository caminhaoRepository;
    private final PontoColetaRepository pontoColetaRepository;
    private final BairroRepository bairroRepository;
    private final ParadaRotaRepository paradaRotaRepository;
    private final JGraphTAdapter grafoAdapter;
    private final RotaMapper rotaMapper;
    private final ParadaPontoColetaRepository paradaPontoColetaRepository;
    private final RuaRepository ruaRepository;


    @Transactional
    public RotaResponseDTO criarRota(RotaRequestDTO requestDTO) {
        Caminhao caminhao = caminhaoRepository.findById(requestDTO.getCaminhaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Caminhão não encontrado"));
        
        Bairro origem = bairroRepository.findById(requestDTO.getOrigemId())
                .orElseThrow(() -> new ResourceNotFoundException("Bairro de origem não encontrado"));
        
        Bairro destino = bairroRepository.findById(requestDTO.getDestinoId())
                .orElseThrow(() -> new ResourceNotFoundException("Bairro de destino não encontrado"));

        if (!caminhao.getTipoResiduos().contains(requestDTO.getTipoResiduo())) {
            throw new BusinessException("O caminhão não coleta este tipo de resíduo");
        }

        adicionarVertices();
        adicionarArestas();
        
        Map<Double, List<String>> resultadoRota = grafoAdapter.calcularRotaMaisCurta(origem, destino);
        
        if (resultadoRota.isEmpty() || resultadoRota.values().iterator().next().isEmpty()) {
            throw new BusinessException("Não foi possível calcular uma rota entre os bairros especificados");
        }

        Rota rota = new Rota();
        rota.setNome(generateRouteName(caminhao, origem, destino));
        rota.setCaminhao(caminhao);
        rota.setDistanciaTotalKm(resultadoRota.keySet().iterator().next());
        rota.setTiposResiduos(requestDTO.getTipoResiduo());
        
        Rota savedRota = rotaRepository.save(rota);

        criarParadasRota(savedRota, resultadoRota.values().iterator().next(), requestDTO.getTipoResiduo());

        grafoAdapter.removerTodasArestas();
        grafoAdapter.removerTodosVertices();
        return rotaMapper.toResponseDTO(savedRota);
    }


    private void criarParadasRota(Rota rota, List<String> bairrosIds, TipoResiduo tipoResiduo) {
        int ordem = 1;
        
        for (String bairroId : bairrosIds) {
            Long id = Long.parseLong(bairroId);
            Bairro bairro = bairroRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bairro não encontrado: " + id));

            ParadaRota parada = new ParadaRota();
            parada.setRota(rota);
            parada.setBairro(bairro);
            parada.setOrdem(ordem++);
            ParadaRota savedParada = paradaRotaRepository.save(parada);

            adicionarPontosColetaParada(savedParada, bairro, tipoResiduo);
        }
    }

    private String generateRouteName(Caminhao caminhao, Bairro origem, Bairro destino) {
        return String.format("Rota %s - %s para %s", 
                caminhao.getPlaca(), 
                origem.getNome(), 
                destino.getNome());
    }

    @Transactional(readOnly = true)
    public RotaResponseDTO buscarRotaCompleta(Long id) {
        Rota rota = rotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));
        
        return rotaMapper.toResponseDTO(rota);
    }

    @Transactional(readOnly = true)
    public List<RotaResponseDTO> listarRotasPorCaminhao(Long caminhaoId) {
        return rotaRepository.findByCaminhaoId(caminhaoId).stream()
                .map(rotaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

     @Transactional(readOnly = true)
    public List<RotaResponseDTO> listarTodasAsRotas() {
        return rotaRepository.findAll().stream()
                .map(rotaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RotaResponseDTO atualizarRota(Long id, RotaRequestDTO requestDTO) {
        Rota rota = rotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));
        
        if (requestDTO.getNome() != null) {
            rota.setNome(requestDTO.getNome());
        }
        
        if (requestDTO.getTipoResiduo() != null && 
            !requestDTO.getTipoResiduo().equals(rota.getTiposResiduos())) {
            throw new BusinessException("Não é possível alterar o tipo de resíduo de uma rota existente");
        }
        
        return rotaMapper.toResponseDTO(rotaRepository.save(rota));
    }

    @Transactional
    public void removerRota(Long id) {
        Rota rota = rotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));
        
        paradaRotaRepository.deleteByRotaId(id);
        
        rotaRepository.delete(rota);
    }
    
    private void adicionarPontosColetaParada(ParadaRota parada, Bairro bairro, TipoResiduo tipoResiduo) {
        List<PontoColeta> pontos = pontoColetaRepository.findByBairroAndTiposDeResiduoContaining(bairro, tipoResiduo);
        
        for (PontoColeta ponto : pontos) {
            ParadaPontoColeta paradaPonto = new ParadaPontoColeta();
            paradaPonto.setParadaRota(parada);
            paradaPonto.setPontoColeta(ponto);
            paradaPonto.setColetado(false);
            paradaPontoColetaRepository.save(paradaPonto);
        }
    }

    @Transactional
    public RotaResponseDTO recalcularRota(Long id) {
        Rota rota = rotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));
        
        List<ParadaRota> paradas = paradaRotaRepository.findByRotaOrderByOrdem(rota);
        if (paradas.size() < 2) {
            throw new BusinessException("Rota não contém paradas suficientes para recalculo");
        }
        
        Bairro origem = paradas.get(0).getBairro();
        Bairro destino = paradas.get(paradas.size() - 1).getBairro();
        adicionarVertices();
        adicionarArestas();
        
        Map<Double, List<String>> resultadoRota = grafoAdapter.calcularRotaMaisCurta(origem, destino);
        
        if (resultadoRota.isEmpty() || resultadoRota.values().iterator().next().isEmpty()) {
            throw new BusinessException("Não foi possível recalcular a rota");
        }
        
        paradaPontoColetaRepository.deleteByParadaRotaIn(paradas);
        paradaRotaRepository.deleteByRotaId(rota.getId());
        
        rota.setDistanciaTotalKm(resultadoRota.keySet().iterator().next());
        criarParadasRota(rota, resultadoRota.values().iterator().next(), rota.getTiposResiduos());
        
        grafoAdapter.removerTodasArestas();
        grafoAdapter.removerTodosVertices();
        return rotaMapper.toResponseDTO(rotaRepository.save(rota));
    }
    
    @Transactional
    public void recalcularRotasParaNovaRua(Rua novaRua) {
        Long origemId = novaRua.getOrigem().getId();
        Long destinoId = novaRua.getDestino().getId();
        
        // Encontrar rotas que passam por esses bairros
        List<Long> rotasIds = paradaRotaRepository.findDistinctRotaIdsByBairroId(origemId);
        rotasIds.addAll(paradaRotaRepository.findDistinctRotaIdsByBairroId(destinoId));
        Set<Long> rotasUnicasIds = new HashSet<>(rotasIds);
        
        if (rotasUnicasIds.isEmpty()) {
            return;
        }
        

        
        
        for (Long rotaId : rotasUnicasIds) {
            try {
                Rota rota = rotaRepository.findById(rotaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada: " + rotaId));
                
                List<ParadaRota> paradas = paradaRotaRepository.findByRotaOrderByOrdem(rota);
                if (paradas.size() < 2) {
                    continue; 
                }
                
                Bairro origem = paradas.get(0).getBairro();
                Bairro destino = paradas.get(paradas.size() - 1).getBairro();
                
                Map<Double, List<String>> resultadoRota = grafoAdapter.calcularRotaMaisCurta(origem, destino);
                
                if (resultadoRota.isEmpty() || resultadoRota.values().iterator().next().isEmpty()) {
                    continue;
                }
                
                paradaPontoColetaRepository.deleteByParadaRotaIn(paradas);
                paradaRotaRepository.deleteByRotaId(rota.getId());
                
                rota.setDistanciaTotalKm(resultadoRota.keySet().iterator().next());
                criarParadasRota(rota, resultadoRota.values().iterator().next(), rota.getTiposResiduos());
                
                rotaRepository.save(rota);
                

                
            } catch (Exception e) {
                System.err.println("Erro ao recalcular rota " + rotaId + ": " + e.getMessage());
            }
        }
    }
    
    private void adicionarVertices() {
    	List<Bairro> bairros = bairroRepository.findAll();
    	for(Bairro b : bairros) {
    		grafoAdapter.adicionarVertice(b);
    	}
    }
    
    private void adicionarArestas() {
    	List<Rua> ruas = ruaRepository.findAll();
    	for(Rua r: ruas) {
    		grafoAdapter.adicionarAresta(r);
    	}
    }
    
}