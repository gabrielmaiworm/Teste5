package org.com.biomob.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.com.biomob.domain.enumeration.CategoriaItem;
import org.com.biomob.domain.enumeration.UnidadeMedida;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Lob
    @Column(name = "imagem")
    private byte[] imagem;

    @Column(name = "imagem_content_type")
    private String imagemContentType;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_item")
    private CategoriaItem categoriaItem;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_medida")
    private UnidadeMedida unidadeMedida;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Item id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Item descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public byte[] getImagem() {
        return this.imagem;
    }

    public Item imagem(byte[] imagem) {
        this.setImagem(imagem);
        return this;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getImagemContentType() {
        return this.imagemContentType;
    }

    public Item imagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
        return this;
    }

    public void setImagemContentType(String imagemContentType) {
        this.imagemContentType = imagemContentType;
    }

    public Integer getQuantidade() {
        return this.quantidade;
    }

    public Item quantidade(Integer quantidade) {
        this.setQuantidade(quantidade);
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public CategoriaItem getCategoriaItem() {
        return this.categoriaItem;
    }

    public Item categoriaItem(CategoriaItem categoriaItem) {
        this.setCategoriaItem(categoriaItem);
        return this;
    }

    public void setCategoriaItem(CategoriaItem categoriaItem) {
        this.categoriaItem = categoriaItem;
    }

    public UnidadeMedida getUnidadeMedida() {
        return this.unidadeMedida;
    }

    public Item unidadeMedida(UnidadeMedida unidadeMedida) {
        this.setUnidadeMedida(unidadeMedida);
        return this;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", imagem='" + getImagem() + "'" +
            ", imagemContentType='" + getImagemContentType() + "'" +
            ", quantidade=" + getQuantidade() +
            ", categoriaItem='" + getCategoriaItem() + "'" +
            ", unidadeMedida='" + getUnidadeMedida() + "'" +
            "}";
    }
}
