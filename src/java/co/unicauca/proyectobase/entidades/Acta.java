/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.proyectobase.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Asus
 */
@Entity
@Table(name = "acta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acta.findAll", query = "SELECT a FROM Acta a")
    , @NamedQuery(name = "Acta.findByActIdentificador", query = "SELECT a FROM Acta a WHERE a.actIdentificador = :actIdentificador")
    , @NamedQuery(name = "Acta.findByActNoActa", query = "SELECT a FROM Acta a WHERE a.actNoActa = :actNoActa")
    , @NamedQuery(name = "Acta.findByActDescripcion", query = "SELECT a FROM Acta a WHERE a.actDescripcion = :actDescripcion")
    , @NamedQuery(name = "Acta.findByActnombrePreside", query = "SELECT a FROM Acta a WHERE a.actnombrePreside = :actnombrePreside")
    , @NamedQuery(name = "Acta.findByActSecretario", query = "SELECT a FROM Acta a WHERE a.actSecretario = :actSecretario")
    , @NamedQuery(name = "Acta.findByActDependencia", query = "SELECT a FROM Acta a WHERE a.actDependencia = :actDependencia")
    , @NamedQuery(name = "Acta.findByActFormato", query = "SELECT a FROM Acta a WHERE a.actFormato = :actFormato")})
public class Acta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "act_identificador")
    private Integer actIdentificador;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "act_no_acta")
    private String actNoActa;
    @Size(max = 5000)
    @Column(name = "act_descripcion")
    private String actDescripcion;
    @Size(max = 60)
    @Column(name = "act_nombrePreside")
    private String actnombrePreside;
    @Size(max = 60)
    @Column(name = "act_secretario")
    private String actSecretario;
    @Size(max = 65)
    @Column(name = "act_dependencia")
    private String actDependencia;
    @Size(max = 65)
    @Column(name = "act_formato")
    private String actFormato;
    @Size(max = 200)
    @Column(name = "act_nombre")
    private String actNombre;
    @Column(name = "act_fecha")
    @Temporal(TemporalType.DATE)
    private Date actFecha;
    @JoinColumn(name = "act_identificador", referencedColumnName = "pub_identificador", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Publicacion publicacion;
    
    /* Constructores */
    public Acta() {
    }

    public Acta(Integer actIdentificador) {
        this.actIdentificador = actIdentificador;
    }

    public Acta(Integer actIdentificador, String actNoActa) {
        this.actIdentificador = actIdentificador;
        this.actNoActa = actNoActa;
    }
    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    
    public Integer getActIdentificador() {
        return actIdentificador;
    }

    public void setActIdentificador(Integer actIdentificador) {
        this.actIdentificador = actIdentificador;
    }

    public String getActNoActa() {
        return actNoActa;
    }

    public void setActNoActa(String actNoActa) {
        this.actNoActa = actNoActa;
    }

    public String getActDescripcion() {
        return actDescripcion;
    }

    public void setActDescripcion(String actDescripcion) {
        this.actDescripcion = actDescripcion;
    }

    public String getActnombrePreside() {
        return actnombrePreside;
    }

    public void setActnombrePreside(String actnombrePreside) {
        this.actnombrePreside = actnombrePreside;
    }

    public String getActSecretario() {
        return actSecretario;
    }

    public void setActSecretario(String actSecretario) {
        this.actSecretario = actSecretario;
    }

    public String getActDependencia() {
        return actDependencia;
    }

    public void setActDependencia(String actDependencia) {
        this.actDependencia = actDependencia;
    }

    public String getActFormato() {
        return actFormato;
    }

    public void setActFormato(String actFormato) {
        this.actFormato = actFormato;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
    public String getActNombre() {
        return actNombre;
    }

    public void setActNombre(String actNombre) {
        this.actNombre = actNombre;
    }

    public Date getActFecha() {
        return actFecha;
    }

    public void setActFecha(Date actFecha) {
        this.actFecha = actFecha;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Hash y Equals">
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actIdentificador != null ? actIdentificador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acta)) {
            return false;
        }
        Acta other = (Acta) object;
        if ((this.actIdentificador == null && other.actIdentificador != null) || (this.actIdentificador != null && !this.actIdentificador.equals(other.actIdentificador))) {
            return false;
        }
        return true;
    }
    //</editor-fold> 
    
    @Override
    public String toString() {
        return "co.unicauca.proyectobase.entidades.Acta[ actIdentificador=" + actIdentificador + " ]";
    }
    
}
