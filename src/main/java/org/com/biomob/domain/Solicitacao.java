package org.com.biomob.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Solicitacao.
 */
@Entity
@Table(name = "solicitacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Solicitacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "solicitante")
    private String solicitante;

    @Column(name = "anonima")
    private Boolean anonima;

    @Column(name = "data_solicitacao")
    private Instant dataSolicitacao;

    @Column(name = "endereco_entrega")
    private String enderecoEntrega;

    @Column(name = "local_de_entrega")
    private String localDeEntrega;

    @Column(name = "data_aprovacao")
    private LocalDate dataAprovacao;

    @Column(name = "aprovado")
    private Boolean aprovado;

    @Column(name = "ativa")
    private Boolean ativa;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "acao", "cadastroDoacaos", "solicitacaos" }, allowSetters = true)
    private Ligacao ligacao;

    @ManyToOne
    private Item descricao;

    @JsonIgnoreProperties(value = { "cadastroDoacao", "solicitacao", "ligacao" }, allowSetters = true)
    @OneToOne(mappedBy = "solicitacao")
    private Acao acao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Solicitacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolicitante() {
        return this.solicitante;
    }

    public Solicitacao solicitante(String solicitante) {
        this.setSolicitante(solicitante);
        return this;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public Boolean getAnonima() {
        return this.anonima;
    }

    public Solicitacao anonima(Boolean anonima) {
        this.setAnonima(anonima);
        return this;
    }

    public void setAnonima(Boolean anonima) {
        this.anonima = anonima;
    }

    public Instant getDataSolicitacao() {
        return this.dataSolicitacao;
    }

    public Solicitacao dataSolicitacao(Instant dataSolicitacao) {
        this.setDataSolicitacao(dataSolicitacao);
        return this;
    }

    public void setDataSolicitacao(Instant dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getEnderecoEntrega() {
        return this.enderecoEntrega;
    }

    public Solicitacao enderecoEntrega(String enderecoEntrega) {
        this.setEnderecoEntrega(enderecoEntrega);
        return this;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getLocalDeEntrega() {
        return this.localDeEntrega;
    }

    public Solicitacao localDeEntrega(String localDeEntrega) {
        this.setLocalDeEntrega(localDeEntrega);
        return this;
    }

    public void setLocalDeEntrega(String localDeEntrega) {
        this.localDeEntrega = localDeEntrega;
    }

    public LocalDate getDataAprovacao() {
        return this.dataAprovacao;
    }

    public Solicitacao dataAprovacao(LocalDate dataAprovacao) {
        this.setDataAprovacao(dataAprovacao);
        return this;
    }

    public void setDataAprovacao(LocalDate dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public Boolean getAprovado() {
        return this.aprovado;
    }

    public Solicitacao aprovado(Boolean aprovado) {
        this.setAprovado(aprovado);
        return this;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }

    public Boolean getAtiva() {
        return this.ativa;
    }

    public Solicitacao ativa(Boolean ativa) {
        this.setAtiva(ativa);
        return this;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public Ligacao getLigacao() {
        return this.ligacao;
    }

    public void setLigacao(Ligacao ligacao) {
        this.ligacao = ligacao;
    }

    public Solicitacao ligacao(Ligacao ligacao) {
        this.setLigacao(ligacao);
        return this;
    }

    public Item getDescricao() {
        return this.descricao;
    }

    public void setDescricao(Item item) {
        this.descricao = item;
    }

    public Solicitacao descricao(Item item) {
        this.setDescricao(item);
        return this;
    }

    public Acao getAcao() {
        return this.acao;
    }

    public void setAcao(Acao acao) {
        if (this.acao != null) {
            this.acao.setSolicitacao(null);
        }
        if (acao != null) {
            acao.setSolicitacao(this);
        }
        this.acao = acao;
    }

    public Solicitacao acao(Acao acao) {
        this.setAcao(acao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Solicitacao)) {
            return false;
        }
        return id != null && id.equals(((Solicitacao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Solicitacao{" +
            "id=" + getId() +
            ", solicitante='" + getSolicitante() + "'" +
            ", anonima='" + getAnonima() + "'" +
            ", dataSolicitacao='" + getDataSolicitacao() + "'" +
            ", enderecoEntrega='" + getEnderecoEntrega() + "'" +
            ", localDeEntrega='" + getLocalDeEntrega() + "'" +
            ", dataAprovacao='" + getDataAprovacao() + "'" +
            ", aprovado='" + getAprovado() + "'" +
            ", ativa='" + getAtiva() + "'" +
            "}";
    }
}
