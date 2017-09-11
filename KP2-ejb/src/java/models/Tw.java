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
@Table(name = "tw")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tw.findAll", query = "SELECT t FROM Tw t")
    , @NamedQuery(name = "Tw.findByIdTw", query = "SELECT t FROM Tw t WHERE t.idTw = :idTw")
    , @NamedQuery(name = "Tw.findByAccessToken", query = "SELECT t FROM Tw t WHERE t.accessToken = :accessToken")
    , @NamedQuery(name = "Tw.findByName", query = "SELECT t FROM Tw t WHERE t.name = :name")
    , @NamedQuery(name = "Tw.findByScreenName", query = "SELECT t FROM Tw t WHERE t.screenName = :screenName")
    , @NamedQuery(name = "Tw.findByPhoto200", query = "SELECT t FROM Tw t WHERE t.photo200 = :photo200")
    , @NamedQuery(name = "Tw.findByAccessTokenSecret", query = "SELECT t FROM Tw t WHERE t.accessTokenSecret = :accessTokenSecret")})
public class Tw implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTw")
    private Integer idTw;
    @Size(max = 255)
    @Column(name = "AccessToken")
    private String accessToken;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "ScreenName")
    private String screenName;
    @Size(max = 255)
    @Column(name = "photo_200")
    private String photo200;
    @Size(max = 255)
    @Column(name = "AccessTokenSecret")
    private String accessTokenSecret;
    @OneToMany(mappedBy = "idTw")
    private Collection<Profiles> profilesCollection;

    public Tw() {
    }

    public Tw(Integer idTw) {
        this.idTw = idTw;
    }

    public Tw(Integer tid, String accessToken, String name, String screenName, String photo200, String accessTokenSecret) {
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
        this.idTw = tid;
        this.name = name;
        this.screenName = screenName;
        this.photo200 = photo200;
    }

    public Integer getIdTw() {
        return idTw;
    }

    public void setIdTw(Integer idTw) {
        this.idTw = idTw;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getPhoto200() {
        return photo200;
    }

    public void setPhoto200(String photo200) {
        this.photo200 = photo200;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
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
        hash += (idTw != null ? idTw.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tw)) {
            return false;
        }
        Tw other = (Tw) object;
        if ((this.idTw == null && other.idTw != null) || (this.idTw != null && !this.idTw.equals(other.idTw))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Tw[ idTw=" + idTw + " ]";
    }

}
