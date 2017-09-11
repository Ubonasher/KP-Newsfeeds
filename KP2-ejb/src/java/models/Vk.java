/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Григорий
 */
@Entity
@Table(name = "vk")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vk.findAll", query = "SELECT v FROM Vk v")
    , @NamedQuery(name = "Vk.findByIdVk", query = "SELECT v FROM Vk v WHERE v.idVk = :idVk")
    , @NamedQuery(name = "Vk.findByAccessToken", query = "SELECT v FROM Vk v WHERE v.accessToken = :accessToken")
    , @NamedQuery(name = "Vk.findByFirstName", query = "SELECT v FROM Vk v WHERE v.firstName = :firstName")
    , @NamedQuery(name = "Vk.findByLastName", query = "SELECT v FROM Vk v WHERE v.lastName = :lastName")
    , @NamedQuery(name = "Vk.findByPhoto200", query = "SELECT v FROM Vk v WHERE v.photo200 = :photo200")})
public class Vk implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idVk")
    private Integer idVk;
    @Size(max = 255)
    @Column(name = "access_token")
    private String accessToken;
    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 255)
    @Column(name = "photo_200")
    private String photo200;
    @OneToMany(mappedBy = "idVk")
    private Collection<Profiles> profilesCollection;

    public Vk() {
    }

    public Vk(Integer idVk) {
        this.idVk = idVk;
    }

    public Vk(Integer idVk,String accessToken,String firstName,String lastName,String photo200) {
        this.idVk = idVk;
        this.accessToken = accessToken;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo200 = photo200;        
    }

    public Integer getIdVk() {
        return idVk;
    }

    public void setIdVk(Integer idVk) {
        this.idVk = idVk;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto200() {
        return photo200;
    }

    public void setPhoto200(String photo200) {
        this.photo200 = photo200;
    }

    @XmlTransient
    public Collection<Profiles> getProfilesCollection() {
        return profilesCollection;
    }

    public void setProfilesCollection(Collection<Profiles> profilesCollection) {
        this.profilesCollection = profilesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVk != null ? idVk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vk)) {
            return false;
        }
        Vk other = (Vk) object;
        if ((this.idVk == null && other.idVk != null) || (this.idVk != null && !this.idVk.equals(other.idVk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Vk[ idVk=" + idVk + " ]";
    }

}
