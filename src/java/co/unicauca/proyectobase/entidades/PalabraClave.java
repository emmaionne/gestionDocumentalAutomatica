/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */
@Entity
@Table(name = "palabra_clave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PalabraClave.findAll", query = "SELECT p FROM PalabraClave p")
    , @NamedQuery(name = "PalabraClave.findBypalClaidentificador", query = "SELECT p FROM PalabraClave p WHERE p.palClaidentificador = :palClaidentificador")
    , @NamedQuery(name = "PalabraClave.findBypalClapalabra", query = "SELECT p FROM PalabraClave p WHERE p.palClapalabra = :palClapalabra")})
public class PalabraClave implements Serializable {

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "pub_identificador", referencedColumnName = "pub_identificador")
    @ManyToOne(optional = false)
    private Publicacion pubIdentificador;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "palCla_identificador")
    private Integer palClaidentificador;
    @Size(max = 25)
    @Column(name = "palCla_palabra")
    private String palClapalabra;

    public Publicacion getPubIdentificador() {
        return pubIdentificador;
    }

    public void setPubIdentificador(Publicacion pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public Integer getPalClaidentificador() {
        return palClaidentificador;
    }

    public void setPalClaidentificador(Integer palClaidentificador) {
        this.palClaidentificador = palClaidentificador;
    }

    public String getPalClapalabra() {
        return palClapalabra;
    }

    public void setPalClapalabra(String palClapalabra) {
        this.palClapalabra = palClapalabra;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.pubIdentificador);
        hash = 59 * hash + Objects.hashCode(this.palClaidentificador);
        hash = 59 * hash + Objects.hashCode(this.palClapalabra);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PalabraClave other = (PalabraClave) obj;
        if (!Objects.equals(this.palClapalabra, other.palClapalabra)) {
            return false;
        }
        if (!Objects.equals(this.pubIdentificador, other.pubIdentificador)) {
            return false;
        }
        if (!Objects.equals(this.palClaidentificador, other.palClaidentificador)) {
            return false;
        }
        return true;
    }

}
