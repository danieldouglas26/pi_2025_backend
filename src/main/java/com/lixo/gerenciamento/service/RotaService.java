package com.lixo.gerenciamento.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lixo.gerenciamento.adapter.JGraphTAdapter;
import com.lixo.gerenciamento.exception.BusinessException;
import com.lixo.gerenciamento.exception.ResourceNotFoundException;
import com.lixo.gerenciamento.model.dto.request.RotaRequestDTO;
import com.lixo.gerenciamento.model.dto.response.RotaResponseDTO;
import com.lixo.gerenciamento.model.entity.Bairro;
import com.lixo.gerenciamento.model.entity.Caminhao;
import com.lixo.gerenciamento.model.entity.ParadaPontoColeta;
import com.lixo.gerenciamento.model.entity.ParadaRota;
import com.lixo.gerenciamento.model.entity.PontoColeta;
import com.lixo.gerenciamento.model.entity.Rota;
import com.lixo.gerenciamento.model.entity.Rua;
import com.lixo.gerenciamento.model.enums.TipoResiduo;
import com.lixo.gerenciamento.model.mapper.RotaMapper;
import com.lixo.gerenciamento.repository.BairroRepository;
import com.lixo.gerenciamento.repository.CaminhaoRepository;
import com.lixo.gerenciamento.repository.ParadaPontoColetaRepository;
import com.lixo.gerenciamento.repository.ParadaRotaRepository;
import com.lixo.gerenciamento.repository.PontoColetaRepository;
import com.lixo.gerenciamento.repository.RotaRepository;
import com.lixo.gerenciamento.repository.RuaRepository;

@Service
@Transactional
public class RotaService {

    private  RotaRepository rotaRepository;
    private  CaminhaoRepository caminhaoRepository;
    private  PontoColetaRepository pontoColetaRepository;
    private  BairroRepository bairroRepository;
    private  ParadaRotaRepository paradaRotaRepository;
    private  JGraphTAdapter grafoAdapter;
    private  RotaMapper rotaMapper;
    private  ParadaPontoColetaRepository paradaPontoColetaRepository;
    private  RuaRepository ruaRepository;


    @Autowired
    public RotaService(RotaRepository rotaRepository, CaminhaoRepository caminhaoRepository,
			PontoColetaRepository pontoColetaRepository, BairroRepository bairroRepository,
			ParadaRotaRepository paradaRotaRepository, JGraphTAdapter grafoAdapter, RotaMapper rotaMapper,
			ParadaPontoColetaRepository paradaPontoColetaRepository, RuaRepository ruaRepository) {
		super();
		this.rotaRepository = rotaRepository;
		this.caminhaoRepository = caminhaoRepository;
		this.pontoColetaRepository = pontoColetaRepository;
		this.bairroRepository = bairroRepository;
		this.paradaRotaRepository = paradaRotaRepository;
		this.grafoAdapter = grafoAdapter;
		this.rotaMapper = rotaMapper;
		this.paradaPontoColetaRepository = paradaPontoColetaRepository;
		this.ruaRepository = ruaRepository;
	}


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
