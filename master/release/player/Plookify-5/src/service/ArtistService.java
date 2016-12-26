/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Artist;
import java.io.File;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Farrah Khan
 */
public class ArtistService
{

    static
    {
        File f = new File("./playlists");
        if (!f.exists())
            f.mkdirs();
    }
    public final EntityManager em;

    public ArtistService()
    {
        em = DatabaseService.EMF.createEntityManager();
    }

    public ArtistService(EntityManagerFactory emf)
    {
        em = emf.createEntityManager();
    }

    public ArtistService(EntityManager em)
    {
        this.em = em;
    }

    public Artist find(int id)
    {
        return em.find(Artist.class, id);
    }

    public ArrayList<Artist> search(String name)
    {
        return new ArrayList<Artist>(em.createNativeQuery("select * from artist where "
                                                          + "lower (artist_name) like lower('%" + name + "%') "
                                                          + "", Artist.class).getResultList());
    }

    public static void main(String[] args)
    {
        System.out.println(new ArtistService().search("e"));
    }

}
