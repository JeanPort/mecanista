package com.jean.mecanista.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "maquina")
public class Maquina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String codigo;
    private String nome;
    private String localizacao;
    private String setor;
    private boolean ativa;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    protected Maquina() {
    }

    private Maquina(String nome, String localizacao, String setor) {
        this.codigo = gerarCodigo();
        this.nome = nome;
        this.localizacao = localizacao;
        this.setor = setor;
        this.ativa = true;
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = this.criadoEm;
    }

    public static Maquina criar(String nome, String localizacao, String setor){
        return new Maquina(nome, localizacao, setor);
    }

    private String gerarCodigo(){
        var prefix = "MAQ-";
        var uuid = UUID.randomUUID();
        return prefix + uuid.toString().substring(0, 8).toUpperCase();
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getSetor() {
        return setor;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maquina maquina = (Maquina) o;
        return Objects.equals(id, maquina.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Maquina{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", localizacao='" + localizacao + '\'' +
                ", setor='" + setor + '\'' +
                ", ativa=" + ativa +
                ", criadoEm=" + criadoEm +
                ", atualizadoEm=" + atualizadoEm +
                '}';
    }
}
