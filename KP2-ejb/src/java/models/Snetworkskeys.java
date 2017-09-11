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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Григорий
 */
@Entity
@Table(name = "snetworkskeys")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Snetworkskeys.findAll", query = "SELECT s FROM Snetworkskeys s")
    , @NamedQuery(name = "Snetworkskeys.findByTwOauthConsumerKey", query = "SELECT s FROM Snetworkskeys s WHERE s.twOauthConsumerKey = :twOauthConsumerKey")
    , @NamedQuery(name = "Snetworkskeys.findByTwOauthConsumerSecret", query = "SELECT s FROM Snetworkskeys s WHERE s.twOauthConsumerSecret = :twOauthConsumerSecret")
    , @NamedQuery(name = "Snetworkskeys.findByVkClientId", query = "SELECT s FROM Snetworkskeys s WHERE s.vkClientId = :vkClientId")
    , @NamedQuery(name = "Snetworkskeys.findByVkClientSecret", query = "SELECT s FROM Snetworkskeys s WHERE s.vkClientSecret = :vkClientSecret")
    , @NamedQuery(name = "Snetworkskeys.findById", query = "SELECT s FROM Snetworkskeys s WHERE s.id = :id")})
public class Snetworkskeys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "TwOauthConsumerKey")
    private String twOauthConsumerKey;
    @Size(max = 255)
    @Column(name = "TwOauthConsumerSecret")
    private String twOauthConsumerSecret;
    @Size(max = 255)
    @Column(name = "VkClientId")
    private String vkClientId;
    @Size(max = 255)
    @Column(name = "VkClientSecret")
    private String vkClientSecret;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Snetworkskeys() {
    }

    public Snetworkskeys(Integer id) {
        this.id = id;
    }

    public String getTwOauthConsumerKey() {
        return twOauthConsumerKey;
    }

    public void setTwOauthConsumerKey(String twOauthConsumerKey) {
        this.twOauthConsumerKey = twOauthConsumerKey;
    }

    public String getTwOauthConsumerSecret() {
        return twOauthConsumerSecret;
    }

    public void setTwOauthConsumerSecret(String twOauthConsumerSecret) {
        this.twOauthConsumerSecret = twOauthConsumerSecret;
    }

    public String getVkClientId() {
        return vkClientId;
    }

    public void setVkClientId(String vkClientId) {
        this.vkClientId = vkClientId;
    }

    public String getVkClientSecret() {
        return vkClientSecret;
    }

    public void setVkClientSecret(String vkClientSecret) {
        this.vkClientSecret = vkClientSecret;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Snetworkskeys)) {
            return false;
        }
        Snetworkskeys other = (Snetworkskeys) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Snetworkskeys[ id=" + id + " ]";
    }
    
}
