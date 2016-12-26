/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Playlist.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Uniquetrij
 */
@Embeddable
public class PlaylistPK implements Serializable
{

    @Basic(optional = false)
    @Column(name = "user")
    private String user;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public PlaylistPK()
    {
    }

    public PlaylistPK(String user, String name)
    {
        this.user = user;
        this.name = name;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (user != null ? user.hashCode() : 0);
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaylistPK))
            return false;
        PlaylistPK other = (PlaylistPK) object;
        if ((this.user == null && other.user != null) || (this.user != null && !this.user.equals(other.user)))
            return false;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name)))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Playlist.entity.PlaylistPK[ user=" + user + ", name=" + name + " ]";
    }
    
}
