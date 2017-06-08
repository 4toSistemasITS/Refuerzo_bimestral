/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Valeria
 */
@Entity
@Table(name = "titulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Titulo.findAll", query = "SELECT t FROM Titulo t")
    , @NamedQuery(name = "Titulo.findByIdTitulo", query = "SELECT t FROM Titulo t WHERE t.idTitulo = :idTitulo")
    , @NamedQuery(name = "Titulo.findByNivel", query = "SELECT t FROM Titulo t WHERE t.nivel = :nivel")
    , @NamedQuery(name = "Titulo.findByNombre", query = "SELECT t FROM Titulo t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Titulo.findByUniversidad", query = "SELECT t FROM Titulo t WHERE t.universidad = :universidad")
    , @NamedQuery(name = "Titulo.findByFechaCreacion", query = "SELECT t FROM Titulo t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Titulo.findByEliminado", query = "SELECT t FROM Titulo t WHERE t.eliminado = :eliminado")})
public class Titulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_titulo")
    private Integer idTitulo;
    @Size(max = 45)
    @Column(name = "nivel")
    private String nivel;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "universidad")
    private String universidad;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "eliminado")
    private Boolean eliminado;
    @OneToMany(mappedBy = "idTitulo")
    private List<Docente> docenteList;
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne
    private Area idArea;

    public Titulo() {
    }

    public Titulo(String nivel, String nombre, String universidad, Date fechaCreacion, Boolean eliminado, Area idArea) {
        this.nivel = nivel;
        this.nombre = nombre;
        this.universidad = universidad;
        this.fechaCreacion = fechaCreacion;
        this.eliminado = eliminado;
        this.idArea = idArea;
    }
    

    public Titulo(Integer idTitulo) {
        this.idTitulo = idTitulo;
    }

    public Integer getIdTitulo() {
        return idTitulo;
    }

    public void setIdTitulo(Integer idTitulo) {
        this.idTitulo = idTitulo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    @XmlTransient
    public List<Docente> getDocenteList() {
        return docenteList;
    }

    public void setDocenteList(List<Docente> docenteList) {
        this.docenteList = docenteList;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTitulo != null ? idTitulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Titulo)) {
            return false;
        }
        Titulo other = (Titulo) object;
        if ((this.idTitulo == null && other.idTitulo != null) || (this.idTitulo != null && !this.idTitulo.equals(other.idTitulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Titulo[ idTitulo=" + idTitulo + " ]";
    }
    
}
