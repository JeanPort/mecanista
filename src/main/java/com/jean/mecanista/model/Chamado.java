package com.jean.mecanista.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "chamado")
public class Chamado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maquina_id")
    private Maquina maquina;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante_id")
    private Usuario solicitante;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mecanico_id")
    private Usuario mecanico;
    @Enumerated(EnumType.STRING)
    private ChamadoStatus status;
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;
    private String descricao;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private LocalDateTime concluidoEm;

    protected Chamado() {
    }

    private Chamado(Usuario solicitante, Maquina maquina, String descricao, Prioridade prioridade) {
        this.solicitante = Objects.requireNonNull(solicitante, "Solicitante é obrigatorio");
        this.maquina = Objects.requireNonNull(maquina, "Maquina é obrigatorio");
        this.descricao = descricao;
        this.prioridade = prioridade != null ? prioridade : Prioridade.BAIXA;
        this.status = ChamadoStatus.ABERTO;
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = this.criadoEm;
    }

    public static Chamado abrir(Usuario solicitante, Maquina maquina, String descricao, Prioridade prioridade) {
        return new Chamado(solicitante, maquina, descricao, prioridade);
    }

    public void analisar() {
        alterarStatus(ChamadoStatus.EM_ANALISE);
    }

    public void atribuirMecanico(Usuario mecanico) {
        if (status != ChamadoStatus.EM_ANALISE) {
            throw new IllegalArgumentException("Não posso atribuir mecico com status diferente de em_analize");
        }
        if (mecanico == null) {
            throw new IllegalArgumentException("Mecânico é obrigatório");
        }
        if (!mecanico.possuiPapel("mecanico")) {
            throw new IllegalStateException("Usuário informado não possui o papel de mecânico");
        }
        this.mecanico = mecanico;
        tocarAtualizacao();
    }

    public void iniciar(Usuario mecanico) {
        garantirMecanicoResponsavel(mecanico);
        alterarStatus(ChamadoStatus.EM_EXECUCAO);
    }

    public void concluir(Usuario mecanico) {
        garantirMecanicoResponsavel(mecanico);
        alterarStatus(ChamadoStatus.CONCLUIDO);
        this.concluidoEm = LocalDateTime.now();
    }

    public void cancelar(Usuario usuario) {
        var autorizadoComoGerente = usuario.possuiPapel("gerente");
        var autorizadoComoSolicitante = this.solicitante.equals(usuario) && this.status == ChamadoStatus.ABERTO;

        if (!autorizadoComoGerente || !autorizadoComoSolicitante) {
            throw new IllegalArgumentException("Não tem permição para cancelar");
        }

        alterarStatus(ChamadoStatus.CANCELADO);
    }

    private void garantirMecanicoResponsavel(Usuario mecanico) {
        if (!this.mecanico.equals(mecanico)) {
            throw new IllegalArgumentException("O mecanico não é responsavel");
        }
    }


    private void alterarStatus(ChamadoStatus novoStatus) {
        var podeAlterar = status.podeMudarPara(novoStatus);
        if (!podeAlterar) {
            throw new IllegalArgumentException("Não pode alterar status");
        }
        status = novoStatus;
        tocarAtualizacao();
    }

    private void tocarAtualizacao() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public Usuario getMecanico() {
        return mecanico;
    }

    public ChamadoStatus getStatus() {
        return status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public LocalDateTime getConcluidoEm() {
        return concluidoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chamado chamado = (Chamado) o;
        return Objects.equals(id, chamado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Chamado{" +
                "id=" + id +
                ", status=" + status +
                ", prioridade=" + prioridade +
                ", descricao='" + descricao + '\'' +
                ", criadoEm=" + criadoEm +
                ", atualizadoEm=" + atualizadoEm +
                ", concluidoEm=" + concluidoEm +
                '}';
    }
}

