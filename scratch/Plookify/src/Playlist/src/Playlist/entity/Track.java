/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Playlist.entity;

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
 * @author Uniquetrij
 */
@Entity
@Table(name = "track")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Track.findAll", query = "SELECT t FROM Track t"),
    @NamedQuery(name = "Track.findByTrackId", query = "SELECT t FROM Track t WHERE t.trackId = :trackId"),
    @NamedQuery(name = "Track.findByGenreId", query = "SELECT t FROM Track t WHERE t.genreId = :genreId"),
    @NamedQuery(name = "Track.findByArtistId", query = "SELECT t FROM Track t WHERE t.artistId = :artistId"),
    @NamedQuery(name = "Track.findByLength", query = "SELECT t FROM Track t WHERE t.length = :length")
})
public class Track implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "track_id")
    private Integer trackId;
    @Column(name = "genre_id")
    private Integer genreId;
    @Column(name = "artist_id")
    private Integer artistId;
    @Lob
    @Column(name = "track_name")
    private String trackName;
    @Column(name = "length")
    private String length;

    public Track()
    {
    }

    public Track(Integer trackId)
    {
        this.trackId = trackId;
    }

    public Integer getTrackId()
    {
        return trackId;
    }

    public void setTrackId(Integer trackId)
    {
        this.trackId = trackId;
    }

    public Integer getGenreId()
    {
        return genreId;
    }

    public void setGenreId(Integer genreId)
    {
        this.genreId = genreId;
    }

    public Integer getArtistId()
    {
        return artistId;
    }

    public void setArtistId(Integer artistId)
    {
        this.artistId = artistId;
    }

    public String getTrackName()
    {
        return trackName;
    }

    public void setTrackName(String trackName)
    {
        this.trackName = trackName;
    }

    public String getLength()
    {
        return length;
    }

    public void setLength(String length)
    {
        this.length = length;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (trackId != null ? trackId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Track))
            return false;
        Track other = (Track) object;
        if ((this.trackId == null && other.trackId != null) || (this.trackId != null && !this.trackId.equals(other.trackId)))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Playlist.entity.Track[ trackId=" + trackId + " ]";
    }
    
}
