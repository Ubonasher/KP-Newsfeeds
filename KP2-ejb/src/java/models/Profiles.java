/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Григорий
 */
@Entity
@Table(name = "profiles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profiles.findAll", query = "SELECT p FROM Profiles p")
    , @NamedQuery(name = "Profiles.findById", query = "SELECT p FROM Profiles p WHERE p.id = :id")
    , @NamedQuery(name = "Profiles.findByIdTw", query = "SELECT p FROM Profiles p WHERE p.idTw.idTw = :idTw")
    , @NamedQuery(name = "Profiles.findByIdVk", query = "SELECT p FROM Profiles p WHERE p.idVk.idVk = :idVk")})
public class Profiles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idTw", referencedColumnName = "idTw")
    @ManyToOne
    private Tw idTw;
    @JoinColumn(name = "idVk", referencedColumnName = "idVk")
    @ManyToOne
    private Vk idVk;

    public Profiles() {
    }

    public Profiles(Integer id) {
        this.id = id;
    }

    public Profiles(Integer id, Vk idVk) {
        this.id = id;
        this.idVk = idVk;
    }

    public Profiles(Integer id, Tw idTw) {
        this.id = id;
        this.idTw = idTw;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tw getIdTw() {
        return idTw;
    }

    public void setIdTw(Tw idTw) {
        this.idTw = idTw;
    }

    public Vk getIdVk() {
        return idVk;
    }

    public void setIdVk(Vk idVk) {
        this.idVk = idVk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profiles)) {
            return false;
        }
        Profiles other = (Profiles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Profiles[ id=" + id + " ]";
    }

}
