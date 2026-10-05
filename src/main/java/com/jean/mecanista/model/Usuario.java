package com.jean.mecanista.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    @Column(name = "senha_hash")
    private String senha;
    private boolean ativo;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "usuario")
    private Set<UsuarioPapel> papeis = new HashSet<>();

    protected Usuario() {
    }

    private Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = true;
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = criadoEm;
    }

    public static Usuario criar(String nome, String senha, String email, Papel papel){
        var usuario = new Usuario(nome, email, senha);
        usuario.adicionarPapel(papel);
        return usuario;
    }

    public void adicionarPapel(Papel papel) {
        if (papel == null) {
            throw new IllegalArgumentException("Papel não pode ser nulo");
        }
        boolean jaPossui = papeis.stream()
                .anyMatch(vinculo -> vinculo.possuiPapel(papel));
        if (jaPossui) {
            return;
        }
        papeis.add(new UsuarioPapel(this, papel));
        tocarAtualizacao();
    }

    public void removerPapel(Long papelId){
        if (papeis.size() == 1){
            throw new IllegalArgumentException("Deve conter ao menos um papel");
        }
        papeis.removeIf(usuarioPapel -> usuarioPapel.getPapel().getId().equals(papelId));
    }

    public boolean possuiPapel(String codigoPapel){
        return papeis.stream().anyMatch(usuarioPapel -> usuarioPapel.possuiPapel(codigoPapel));
    }

    private void tocarAtualizacao(){
        atualizadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public Set<UsuarioPapel> getPapeis() {
        return Set.copyOf(papeis);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "atualizadoEm=" + atualizadoEm +
                ", criadoEm=" + criadoEm +
                ", ativo=" + ativo +
                ", senha='" + senha + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", id=" + id +
                '}';
    }
}
