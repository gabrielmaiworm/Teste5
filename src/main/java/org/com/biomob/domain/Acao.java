package org.com.biomob.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Acao.
 */
@Entity
@Table(name = "acao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Acao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_acao")
    private Instant dataAcao;

    @Column(name = "usuario_criacao_acao")
    private String usuarioCriacaoAcao;

    @Column(name = "pendente")
    private Boolean pendente;

    @Column(name = "data_execucao_acao")
    private LocalDate dataExecucaoAcao;

    @Column(name = "ativa")
    private Boolean ativa;

    @Column(name = "observacoes")
    private String observacoes;

    @JsonIgnoreProperties(value = { "ligacao", "descricao", "acao" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private CadastroDoacao cadastroDoacao;

    @JsonIgnoreProperties(value = { "ligacao", "descricao", "acao" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Solicitacao solicitacao;

    @JsonIgnoreProperties(value = { "user", "acao", "cadastroDoacaos", "solicitacaos" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Ligacao ligacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Acao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAcao() {
        return this.dataAcao;
    }

    public Acao dataAcao(Instant dataAcao) {
        this.setDataAcao(dataAcao);
        return this;
    }

    public void setDataAcao(Instant dataAcao) {
        this.dataAcao = dataAcao;
    }

    public String getUsuarioCriacaoAcao() {
        return this.usuarioCriacaoAcao;
    }

    public Acao usuarioCriacaoAcao(String usuarioCriacaoAcao) {
        this.setUsuarioCriacaoAcao(usuarioCriacaoAcao);
        return this;
    }

    public void setUsuarioCriacaoAcao(String usuarioCriacaoAcao) {
        this.usuarioCriacaoAcao = usuarioCriacaoAcao;
    }

    public Boolean getPendente() {
        return this.pendente;
    }

    public Acao pendente(Boolean pendente) {
        this.setPendente(pendente);
        return this;
    }

    public void setPendente(Boolean pendente) {
        this.pendente = pendente;
    }

    public LocalDate getDataExecucaoAcao() {
        return this.dataExecucaoAcao;
    }

    public Acao dataExecucaoAcao(LocalDate dataExecucaoAcao) {
        this.setDataExecucaoAcao(dataExecucaoAcao);
        return this;
    }

    public void setDataExecucaoAcao(LocalDate dataExecucaoAcao) {
        this.dataExecucaoAcao = dataExecucaoAcao;
    }

    public Boolean getAtiva() {
        return this.ativa;
    }

    public Acao ativa(Boolean ativa) {
        this.setAtiva(ativa);
        return this;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Acao observacoes(String observacoes) {
        this.setObservacoes(observacoes);
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public CadastroDoacao getCadastroDoacao() {
        return this.cadastroDoacao;
    }

    public void setCadastroDoacao(CadastroDoacao cadastroDoacao) {
        this.cadastroDoacao = cadastroDoacao;
    }

    public Acao cadastroDoacao(CadastroDoacao cadastroDoacao) {
        this.setCadastroDoacao(cadastroDoacao);
        return this;
    }

    public Solicitacao getSolicitacao() {
        return this.solicitacao;
    }

    public void setSolicitacao(Solicitacao solicitacao) {
        this.solicitacao = solicitacao;
    }

    public Acao solicitacao(Solicitacao solicitacao) {
        this.setSolicitacao(solicitacao);
        return this;
    }

    public Ligacao getLigacao() {
        return this.ligacao;
    }

    public void setLigacao(Ligacao ligacao) {
        this.ligacao = ligacao;
    }

    public Acao ligacao(Ligacao ligacao) {
        this.setLigacao(ligacao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Acao)) {
            return false;
        }
        return id != null && id.equals(((Acao) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Acao{" +
            "id=" + getId() +
            ", dataAcao='" + getDataAcao() + "'" +
            ", usuarioCriacaoAcao='" + getUsuarioCriacaoAcao() + "'" +
            ", pendente='" + getPendente() + "'" +
            ", dataExecucaoAcao='" + getDataExecucaoAcao() + "'" +
            ", ativa='" + getAtiva() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            "}";
    }
}
