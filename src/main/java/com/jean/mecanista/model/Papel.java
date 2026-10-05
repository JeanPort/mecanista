package com.jean.mecanista.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "papel")
public class Papel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String descricao;

    public Papel() {
    }

    public Papel(Long id, String codigo, String descricao) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Papel papel = (Papel) o;
        return Objects.equals(id, papel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Papel{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
