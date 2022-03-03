package org.com.biomob.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ligacao.
 */
@Entity
@Table(name = "ligacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ligacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @JsonIgnoreProperties(value = { "cadastroDoacao", "solicitacao", "ligacao" }, allowSetters = true)
    @OneToOne(mappedBy = "ligacao")
    private Acao acao;

    @OneToMany(mappedBy = "ligacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ligacao", "descricao", "acao" }, allowSetters = true)
    private Set<CadastroDoacao> cadastroDoacaos = new HashSet<>();

    @OneToMany(mappedBy = "ligacao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ligacao", "descricao", "acao" }, allowSetters = true)
    private Set<Solicitacao> solicitacaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ligacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ligacao user(User user) {
        this.setUser(user);
        return this;
    }

    public Acao getAcao() {
        return this.acao;
    }

    public void setAcao(Acao acao) {
        if (this.acao != null) {
            this.acao.setLigacao(null);
        }
        if (acao != null) {
            acao.setLigacao(this);
        }
        this.acao = acao;
    }

    public Ligacao acao(Acao acao) {
        this.setAcao(acao);
        return this;
    }

    public Set<CadastroDoacao> getCadastroDoacaos() {
        return this.cadastroDoacaos;
    }

    public void setCadastroDoacaos(Set<CadastroDoacao> cadastroDoacaos) {
        if (this.cadastroDoacaos != null) {
            this.cadastroDoacaos.forEach(i -> i.setLigacao(null));
        }
        if (cadastroDoacaos != null) {
            cadastroDoacaos.forEach(i -> i.setLigacao(this));
        }
        this.cadastroDoacaos = cadastroDoacaos;
    }

    public Ligacao cadastroDoacaos(Set<CadastroDoacao> cadastroDoacaos) {
        this.setCadastroDoacaos(cadastroDoacaos);
        return this;
    }

    public Ligacao addCadastroDoacao(CadastroDoacao cadastroDoacao) {
        this.cadastroDoacaos.add(cadastroDoacao);
        cadastroDoacao.setLigacao(this);
        return this;
    }

    public Ligacao removeCadastroDoacao(CadastroDoacao cadastroDoacao) {
        this.cadastroDoacaos.remove(cadastroDoacao);
        cadastroDoacao.setLigacao(null);
        return this;
    }

    public Set<Solicitacao> getSolicitacaos() {
        return this.solicitacaos;
    }

    public void setSolicitacaos(Set<Solicitacao> solicitacaos) {
        if (this.solicitacaos != null) {
            this.solicitacaos.forEach(i -> i.setLigacao(null));
        }
        if (solicitacaos != null) {
            solicitacaos.forEach(i -> i.setLigacao(this));
        }
        this.solicitacaos = solicitacaos;
    }

    public Ligacao solicitacaos(Set<Solicitacao> solicitacaos) {
        this.setSolicitacaos(solicitacaos);
        return this;
    }

    public Ligacao addSolicitacao(Solicitacao solicitacao) {
        this.solicitacaos.add(solicitacao);
        solicitacao.setLigacao(this);
        return this;
    }

    public Ligacao removeSolicitacao(Solicitacao solicitacao) {
        this.solicitacaos.remove(solicitacao);
        solicitacao.setLigacao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ligacao)) {
            return false;
        }
        return id != null && id.equals(((Ligacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ligacao{" +
            "id=" + getId() +
            "}";
    }
}
