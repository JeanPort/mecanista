package com.jean.mecanista.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "usuario_papel")
public class UsuarioPapel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "papel_id", nullable = false)
    private Papel papel;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    protected UsuarioPapel() {
    }

    UsuarioPapel(Usuario usuario, Papel papel) {
        this.usuario = Objects.requireNonNull(usuario);
        this.papel = Objects.requireNonNull(papel);
        this.criadoEm = LocalDateTime.now();
    }

    public boolean possuiPapel(Papel papel){
        return this.papel.getId().equals(papel.getId());
    }
    public boolean possuiPapel(String codigoPapel){
        return this.papel.getCodigo().equals(codigoPapel);
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Papel getPapel() {
        return papel;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioPapel that = (UsuarioPapel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UsuarioPapel{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", papel=" + papel +
                ", criadoEm=" + criadoEm +
                '}';
    }
}

