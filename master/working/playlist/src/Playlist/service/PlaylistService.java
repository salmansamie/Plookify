/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Playlist.service;

import Playlist.entity.Playlist;
import Playlist.entity.PlaylistPK;
import Playlist.entity.Track;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.apache.commons.lang3.SerializationUtils;
import javax.persistence.metamodel.service.Service;

/**
 *
 * @author Uniquetrij
 */
public class PlaylistService
{

    static
    {
        File f = new File("./playlists");
        if (!f.exists())
            f.mkdirs();
    }
    public final EntityManager em;

    public PlaylistService()
    {
        em = DatabaseService.EMF.createEntityManager();
    }

    public PlaylistService(EntityManagerFactory emf)
    {
        em = emf.createEntityManager();
    }

    public PlaylistService(EntityManager em)
    {
        this.em = em;
    }

    public Playlist find(PlaylistPK playlistPK)
    {
        return em.find(Playlist.class, playlistPK);
    }

    public Playlist create(PlaylistPK playlistPK, boolean isFriend, Track... tracks) throws FileNotFoundException
    {

        ArrayList<Track> list = new ArrayList<>(Arrays.asList(tracks));
        Playlist pl = new Playlist(playlistPK);
        pl.setFriend(isFriend);
        pl.setPath("./playlists/" + Calendar.getInstance().getTimeInMillis() + ".pl.ser");

        SerializationUtils.serialize(list, new FileOutputStream(pl.getPath()));

        Service service = new Service(em);
        service.begin();
        service.toPersist(pl);
        service.commit();

        return pl;
    }

    public void delete(PlaylistPK playlistPK)
    {
        Playlist pl = find(playlistPK);
        File list = new File(pl.getPath());
        list.delete();

        Service service = new Service(em);
        service.begin();
        service.toRemove(pl);
        service.commit();
    }

    public void removeTracks(PlaylistPK playlistPK, Track... tracks) throws FileNotFoundException
    {
        Playlist pl = find(playlistPK);
        ArrayList<Track> list = null;

        list = (ArrayList<Track>) SerializationUtils.deserialize(new FileInputStream(pl.getPath()));

        for (Track track : tracks)
            if (list.contains(track))
                list.remove(track);

        SerializationUtils.serialize(list, new FileOutputStream(pl.getPath()));

    }

    public void insertTracks(PlaylistPK playlistPK, int index, Track... tracks) throws FileNotFoundException
    {
        Playlist pl = find(playlistPK);
        ArrayList<Track> list = null;

        list = (ArrayList<Track>) SerializationUtils.deserialize(new FileInputStream(pl.getPath()));

        if (index >= list.size())
            index = -1;
        if (index >= 0)
            list.addAll(index, Arrays.asList(tracks));
        else
            list.addAll(Arrays.asList(tracks));

        SerializationUtils.serialize(list, new FileOutputStream(pl.getPath()));

    }

    public ArrayList<Track> getList(PlaylistPK playlistPK) throws FileNotFoundException
    {
        return (ArrayList<Track>) SerializationUtils.deserialize(new FileInputStream(find(playlistPK).getPath()));
    }

    public Playlist rename(PlaylistPK playlistPK, String name)
    {
        Playlist oldpl = find(playlistPK);
        Playlist newpl = new Playlist(new PlaylistPK(playlistPK.getUser(), name));
        newpl.setFriend(oldpl.getFriend());
        newpl.setPath(oldpl.getPath());

        Service service = new Service(em);
        service.begin();
        service.toRemove(oldpl);
        service.toPersist(newpl);
        service.commit();

        return newpl;
    }

    public Playlist addNow(PlaylistPK playlistPK) throws FileNotFoundException
    {
        Playlist nw = find(new PlaylistPK("now", playlistPK.getUser()));
        insertTracks(nw.getPlaylistPK(), -1, getList(playlistPK).toArray(new Track[0]));

        Service service = new Service(em);

        service.begin();

        service.toPersist(nw);
        service.commit();

        return nw;
    }

    public ArrayList<Playlist> getLists(String user)
    {
        return new ArrayList<Playlist>(em.createNativeQuery("select * from playlist where "
                                                            + "user='" + user + "' "
                                                            + "", Playlist.class).getResultList());

    }

    public ArrayList<Playlist> getFLists(String user)
    {
        return new ArrayList<Playlist>(em.createNativeQuery("select * from playlist where "
                                                            + "user !='" + user + "' and "
                                                            + "friend = 1 "
                                                            + "", Playlist.class).getResultList());

    }

    public ArrayList<Track> geTracks(Playlist playlist) throws FileNotFoundException
    {
        return (ArrayList<Track>) SerializationUtils.deserialize(new FileInputStream(playlist.getPath()));
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        EntityManager em = DatabaseService.EMF.createEntityManager();
//        new PlaylistService(em).create(new PlaylistPK("u1", "pl1"), true);

//        new PlaylistService(em).create(new PlaylistPK("u1", "pl1"), true, new TrackService().find(59),
//                                       new TrackService().find(42),
//                                       new TrackService().find(73));
//        new PlaylistService(em).create(new PlaylistPK("u1", "pl2"), true, new TrackService().find(12),
//                                       new TrackService().find(15),
//                                       new TrackService().find(17));
//        new PlaylistService(em).create(new PlaylistPK("u1", "pl3"), true, new TrackService().find(55),
//                                       new TrackService().find(78),
//                                       new TrackService().find(66));
//        new PlaylistService(em).create(new PlaylistPK("u1", "pl11"), false, new TrackService().find(59),
//                                       new TrackService().find(42),
//                                       new TrackService().find(73));
//        new PlaylistService(em).create(new PlaylistPK("u1", "pl12"), false, new TrackService().find(12),
//                                       new TrackService().find(15),
//                                       new TrackService().find(17));
//        new PlaylistService(em).create(new PlaylistPK("u1", "pl13"), false, new TrackService().find(55),
//                                       new TrackService().find(78),
//                                       new TrackService().find(66));
//        new PlaylistService(em).rename(new PlaylistPK("u1", "pl0"), "pl1");
//        new PlaylistService(em).rename(new PlaylistPK(new UserService(em).find("a").getId(), "pop"),"cop");
        System.out.println(new PlaylistService(em).getFLists("u2"));
    }

}
