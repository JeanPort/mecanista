package com.jean.mecanista.service;

import com.jean.mecanista.dto.AbrirChamadoRequest;
import com.jean.mecanista.dto.ChamadoResponse;
import com.jean.mecanista.dto.filtro.ChamadaFiltro;
import com.jean.mecanista.model.Chamado;
import com.jean.mecanista.model.ChamadoStatus;
import com.jean.mecanista.model.Prioridade;
import com.jean.mecanista.repository.ChamadoRepository;
import com.jean.mecanista.repository.MaquinaRepository;
import com.jean.mecanista.repository.UsuarioRepository;
import com.jean.mecanista.repository.spec.ChamadoSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChamadoService {

    private final UsuarioRepository usuarioRepository;
    private final ChamadoRepository chamadoRepository;
    private final MaquinaRepository maquinaRepository;

    public ChamadoService(UsuarioRepository usuarioRepository, ChamadoRepository chamadoRepository, MaquinaRepository maquinaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.chamadoRepository = chamadoRepository;
        this.maquinaRepository = maquinaRepository;
    }

    @Transactional
    public ChamadoResponse abrirChamado(Long solicitanteId, AbrirChamadoRequest request){
        var solicitante = usuarioRepository.buscarOuFalhar(solicitanteId);
        var maquina = maquinaRepository.findById(request.maquinaId()).orElseThrow();

        var chamado = Chamado.abrir(solicitante, maquina, request.descricao(), request.prioridade());
        chamadoRepository.save(chamado);
        return ChamadoResponse.de(chamado);
    }

    @Transactional
    public ChamadoResponse analisar(Long chamadoId){
        var chamado = chamadoRepository.buscarOuFalhar(chamadoId);
        chamado.analisar();
        chamadoRepository.save(chamado);
        return ChamadoResponse.de(chamado);
    }

    @Transactional
    public ChamadoResponse atribuirMecanico(Long chamadoId, Long mecanicoId){
        var chamado = chamadoRepository.buscarOuFalhar(chamadoId);
        var mecanico = usuarioRepository.buscarOuFalhar(mecanicoId);
        chamado.atribuirMecanico(mecanico);
        chamadoRepository.save(chamado);
        return ChamadoResponse.de(chamado);
    }

    @Transactional
    public ChamadoResponse iniciar(Long chamadoId, Long mecanicoId){
        var chamado = chamadoRepository.buscarOuFalhar(chamadoId);
        var mecanico = usuarioRepository.buscarOuFalhar(mecanicoId);
        chamado.iniciar(mecanico);
        chamadoRepository.save(chamado);
        return ChamadoResponse.de(chamado);
    }

    @Transactional
    public ChamadoResponse concluir(Long chamadoId, Long mecanicoId){
        var chamado = chamadoRepository.buscarOuFalhar(chamadoId);
        var mecanico = usuarioRepository.buscarOuFalhar(mecanicoId);
        chamado.concluir(mecanico);
        chamadoRepository.save(chamado);
        return ChamadoResponse.de(chamado);
    }

    @Transactional
    public void concelar(Long chamadoId, Long usuarioId){
        var chamado = chamadoRepository.buscarOuFalhar(chamadoId);
        var usuario = usuarioRepository.buscarOuFalhar(usuarioId);
        chamado.cancelar(usuario);
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponse> listar(ChamadaFiltro filtro, Long usuarioLogadoId){
        var usuario = usuarioRepository.buscarOuFalhar(usuarioLogadoId);
        var specification = ChamadoSpecification.construirEscopoPorPapel(usuario)
                .and(ChamadoSpecification.comStatus(filtro.status()))
                .and(ChamadoSpecification.comPrioridade(filtro.prioridade()))
                .and(ChamadoSpecification.daMaquina(filtro.maquinaId()));

        return chamadoRepository.findAll(specification).stream().map(ChamadoResponse::de).toList();
    }




}
