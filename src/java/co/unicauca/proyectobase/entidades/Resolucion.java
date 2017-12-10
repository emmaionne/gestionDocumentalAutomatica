/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */
@Entity
@Table(name = "resolucion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resolucion.findAll", query = "SELECT r FROM Resolucion r")
    , @NamedQuery(name = "Resolucion.findByPubIdentificador", query = "SELECT r FROM Resolucion r WHERE r.pubIdentificador = :pubIdentificador")
    , @NamedQuery(name = "Resolucion.findByResNumero", query = "SELECT r FROM Resolucion r WHERE r.resNumero = :resNumero")
    , @NamedQuery(name = "Resolucion.findByResDescripcion", query = "SELECT r FROM Resolucion r WHERE r.resDescripcion = :resDescripcion")})
public class Resolucion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pub_identificador")
    private Integer pubIdentificador;
    @Size(max = 65)
    @Column(name = "res_numero")
    private String resNumero;
    @Size(max = 5000)
    @Column(name = "res_descripcion")
    private String resDescripcion;
    @JoinColumn(name = "pub_identificador", referencedColumnName = "pub_identificador", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Publicacion publicacion;

    public Resolucion() {
    }

    public Resolucion(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public Integer getPubIdentificador() {
        return pubIdentificador;
    }

    public void setPubIdentificador(Integer pubIdentificador) {
        this.pubIdentificador = pubIdentificador;
    }

    public String getResNumero() {
        return resNumero;
    }

    public void setResNumero(String resNumero) {
        this.resNumero = resNumero;
    }

    public String getResDescripcion() {
        return resDescripcion;
    }

    public void setResDescripcion(String resDescripcion) {
        this.resDescripcion = resDescripcion;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pubIdentificador != null ? pubIdentificador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resolucion)) {
            return false;
        }
        Resolucion other = (Resolucion) object;
        if ((this.pubIdentificador == null && other.pubIdentificador != null) || (this.pubIdentificador != null && !this.pubIdentificador.equals(other.pubIdentificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.Resolucion[ pubIdentificador=" + pubIdentificador + " ]";
    }
    
}
