package com.jean.mecanista.repository.spec;

import com.jean.mecanista.model.Chamado;
import com.jean.mecanista.model.ChamadoStatus;
import com.jean.mecanista.model.Prioridade;
import com.jean.mecanista.model.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class ChamadoSpecification {

    private ChamadoSpecification() {
    }
    public static Specification<Chamado> construirEscopoPorPapel(Usuario usuarioLogado) {
        if (usuarioLogado.possuiPapel("gerente")) {
            return semRestricao();
        }
        if (usuarioLogado.possuiPapel("mecanico")) {
            return doMecanico(usuarioLogado.getId());
        }
        return doSolicitante(usuarioLogado.getId());
    }

    public static Specification<Chamado> semRestricao() {
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<Chamado> doSolicitante(Long solicitanteId) {
        return (root, query, cb) -> cb.equal(root.get("solicitante").get("id"), solicitanteId);
    }

    public static Specification<Chamado> doMecanico(Long mecanicoId) {
        return (root, query, cb) -> cb.equal(root.get("mecanico").get("id"), mecanicoId);
    }

    public static Specification<Chamado> comStatus(ChamadoStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Chamado> comPrioridade(Prioridade prioridade) {
        return (root, query, cb) -> prioridade == null ? null : cb.equal(root.get("prioridade"), prioridade);
    }

    public static Specification<Chamado> daMaquina(Long maquinaId) {
        return (root, query, cb) -> maquinaId == null ? null : cb.equal(root.get("maquina").get("id"), maquinaId);
    }
}
