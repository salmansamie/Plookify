/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Farrah Khan
 */
@Entity
@Table(name = "playlist")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Playlist.findAll", query = "SELECT p FROM Playlist p"),
    @NamedQuery(name = "Playlist.findByUser", query = "SELECT p FROM Playlist p WHERE p.playlistPK.user = :user"),
    @NamedQuery(name = "Playlist.findByName", query = "SELECT p FROM Playlist p WHERE p.playlistPK.name = :name"),
    @NamedQuery(name = "Playlist.findByPath", query = "SELECT p FROM Playlist p WHERE p.path = :path"),
    @NamedQuery(name = "Playlist.findByFriend", query = "SELECT p FROM Playlist p WHERE p.friend = :friend")
})
public class Playlist implements Serializable
{

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlaylistPK playlistPK;
    @Basic(optional = false)
    @Column(name = "path")
    private String path;
    @Column(name = "friend")
    private Boolean friend;

    public Playlist()
    {
    }

    public Playlist(PlaylistPK playlistPK)
    {
        this.playlistPK = playlistPK;
    }

    public Playlist(PlaylistPK playlistPK, String path)
    {
        this.playlistPK = playlistPK;
        this.path = path;
    }

    public Playlist(String user, String name)
    {
        this.playlistPK = new PlaylistPK(user, name);
    }

    public PlaylistPK getPlaylistPK()
    {
        return playlistPK;
    }

    public void setPlaylistPK(PlaylistPK playlistPK)
    {
        this.playlistPK = playlistPK;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public Boolean getFriend()
    {
        return friend;
    }

    public void setFriend(Boolean friend)
    {
        this.friend = friend;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (playlistPK != null ? playlistPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playlist))
            return false;
        Playlist other = (Playlist) object;
        if ((this.playlistPK == null && other.playlistPK != null) || (this.playlistPK != null && !this.playlistPK.equals(other.playlistPK)))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Playlist.entity.Playlist[ playlistPK=" + playlistPK + " ]";
    }
    
}
