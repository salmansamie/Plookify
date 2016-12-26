/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Farrah Khan
 */
@Entity 
@Table(name = "artist")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a"),
    @NamedQuery(name = "Artist.findByArtistId", query = "SELECT a FROM Artist a WHERE a.artistId = :artistId")
})
public class Artist implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "artist_id")
    private Integer artistId;
    @Lob
    @Column(name = "artist_name")
    private String artistName;

    public Artist()
    {
    }

    public Artist(Integer artistId)
    {
        this.artistId = artistId;
    }

    public Integer getArtistId()
    {
        return artistId;
    }

    public void setArtistId(Integer artistId)
    {
        this.artistId = artistId;
    }

    public String getArtistName()
    {
        return artistName;
    }

    public void setArtistName(String artistName)
    {
        this.artistName = artistName;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (artistId != null ? artistId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artist))
            return false;
        Artist other = (Artist) object;
        if ((this.artistId == null && other.artistId != null) || (this.artistId != null && !this.artistId.equals(other.artistId)))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Playlist.entity.Artist[ artistId=" + artistId + " ]";
    }
    
}
