/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Playlist.service;

import Playlist.entity.Artist;
import Playlist.entity.Track;
import java.io.File;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Uniquetrij
 */
public class TrackService
{

    static
    {
        File f = new File("./playlists");
        if (!f.exists())
            f.mkdirs();
    }
    public final EntityManager em;

    public TrackService()
    {
        em = DatabaseService.EMF.createEntityManager();
    }

    public TrackService(EntityManagerFactory emf)
    {
        em = emf.createEntityManager();
    }

    public TrackService(EntityManager em)
    {
        this.em = em;
    }

    public Track find(int id)
    {
        return em.find(Track.class, id);
    }

    public ArrayList<Track> searchArtist(String artist)
    {
        ArrayList<Artist> artists = new ArtistService().search(artist);
        ArrayList<Track> tracks = new ArrayList<>();

        for (Artist a : artists)
            tracks.addAll(em.createNativeQuery("select * from track where "
                                               + "lower(artist_id) =lower('" + a.getArtistId() + "') "
                                               + "", Track.class).getResultList());

        return tracks;
    }

    public static void main(String[] args)
    {
        System.out.println(new TrackService().searchArtist("EX"));
    }

}
