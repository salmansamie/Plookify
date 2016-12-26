/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playlist.fx;

import Playlist.entity.Playlist;
import Playlist.entity.PlaylistPK;
import Playlist.entity.Track;
import Playlist.service.ArtistService;
import Playlist.service.PlaylistService;
import Playlist.service.TrackService;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Uniquetrij
 */
public class FXMLDocumentController implements Initializable
{

    private ArrayList<Playlist> pls;
    private ArrayList<Track> plts;

    private ArrayList<Playlist> fpls;
    private ArrayList<Track> fplts;

    private ArrayList<Track> slt;

    private String user;

    @FXML
    private ListView<String> nowlist;

    @FXML
    private ListView<String> mylists;

    @FXML
    private ListView<String> mylisttracks;

    @FXML
    private ListView<String> friendlists;

    @FXML
    private ListView<String> friendlisttracks;

    @FXML
    private ListView<String> searchlist;

    @FXML
    private TextField newname;

    @FXML
    private TextField search;

    @FXML
    private ComboBox<String> playlistcombo;

    @FXML
    private CheckBox friend;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //user=rb.getObject("user");
        user = "u2";
        refreshPLS();
        refreshFPLS();
        updateMyLists();
        updateFriendLists();
        searchlist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        nowlist.setPlaceholder(new Label("You have not added any tracks yet"));
        mylists.setPlaceholder(new Label("You have not created any playlist yet"));
        mylisttracks.setPlaceholder(new Label("Select a playlist above to browse"));
        friendlists.setPlaceholder(new Label("No friend list available yet"));
        friendlisttracks.setPlaceholder(new Label("Select a playlist above to browse"));
    }

    private void refreshPLS()
    {
        pls = new PlaylistService().getLists(user);
    }

    private void refreshFPLS()
    {
        fpls = new PlaylistService().getFLists(user);
    }

    private void updateMyLists()
    {
        ObservableList<String> data1 = mylists.getItems();
        ObservableList<String> data2 = playlistcombo.getItems();
        data1.clear();
        data2.clear();

        int i = 0;
        for (Playlist pl : pls)
        {
            data1.add(++i + ".  " + pl.getPlaylistPK().getName());
            data2.add(pl.getPlaylistPK().getName());
        }
    }

    private void updateFriendLists()
    {
        ObservableList<String> data = friendlists.getItems();
        data.clear();
        int i = 0;
        for (Playlist pl : fpls)
            data.add(++i + ".  " + pl.getPlaylistPK().getName() + " - " + pl.getPlaylistPK().getUser() + "");
    }

    private void addToNowList(Track... tracks)
    {
        ObservableList<String> data = nowlist.getItems();
        for (Track t : tracks)
            data.add(">  " + t.getTrackName()
                     + " - " + new ArtistService().find(t.getArtistId()).getArtistName()
                     + " (" + t.getLength() + ")");
    }

    @FXML
    private void myListsOnClick()
    {
        int index = mylists.getSelectionModel().getSelectedIndex();
        try
        {
            plts = new PlaylistService().geTracks(pls.get(index));
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Playlist could not be loaded. The list is corrutped.", "Load Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        newname.setText(pls.get(index).getPlaylistPK().getName());

        ObservableList<String> data = mylisttracks.getItems();
        data.clear();
        int i = 0;
        for (Track t : plts)
            data.add(++i + ".  " + t.getTrackName()
                     + " - " + new ArtistService().find(t.getArtistId()).getArtistName()
                     + " (" + t.getLength() + ")");

    }

    @FXML
    private void friendListsOnClick()
    {
        int index = friendlists.getSelectionModel().getSelectedIndex();
        try
        {
            fplts = new PlaylistService().geTracks(fpls.get(index));
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Playlist could not be loaded. The list is corrutped.", "Load Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ObservableList<String> data = friendlisttracks.getItems();
        data.clear();
        int i = 0;
        for (Track t : fplts)
            data.add(++i + ".  " + t.getTrackName()
                     + " - " + new ArtistService().find(t.getArtistId()).getArtistName()
                     + " (" + t.getLength() + ")");

    }

    @FXML
    private void plusNowPlayingOnClick()
    {
        addToNowList(plts.toArray(new Track[0]));
        JOptionPane.showMessageDialog(null, plts.size() + " tracks added to Now Playing queue.", "Tracks Added", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    private void plusNowPlayingFOnClick()
    {
        addToNowList(fplts.toArray(new Track[0]));
        JOptionPane.showMessageDialog(null, fplts.size() + " tracks added to Now Playing queue.", "Tracks Added", JOptionPane.INFORMATION_MESSAGE);

    }

    @FXML
    private void removeTrackOnClick()
    {
        int i = mylists.getSelectionModel().getSelectedIndex();
        int j = mylisttracks.getSelectionModel().getSelectedIndex();
        if (i < 0 || j < 0)
        {
            JOptionPane.showMessageDialog(null, "Please select a track to remove.", "Select Track", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try
        {
            new PlaylistService().removeTracks(pls.get(i).getPlaylistPK(), plts.get(j));
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Playlist could not be updated. The list is corrutped.", "Update Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        refreshPLS();

        myListsOnClick();

        JOptionPane.showMessageDialog(null, "Track removed successfully from the playlist", "Track removed", JOptionPane.INFORMATION_MESSAGE);

    }

    @FXML
    private void removePlaylistOnClick()
    {
        int i = mylists.getSelectionModel().getSelectedIndex();
        if (i < 0)
        {
            JOptionPane.showMessageDialog(null, "Please select a playlist to remove.", "Select Playlist", JOptionPane.ERROR_MESSAGE);
            return;
        }
        new PlaylistService().delete(pls.get(i).getPlaylistPK());
        refreshPLS();
        updateMyLists();
        newname.setText("");
        mylisttracks.getItems().clear();
        JOptionPane.showMessageDialog(null, "Playlist removed successfully", "Playlist removed", JOptionPane.INFORMATION_MESSAGE);

    }

    @FXML
    private void renamePlaylistOnClick()
    {
        String name = newname.getText();
        if (new PlaylistService().find(new PlaylistPK(user, name)) != null)
        {
            JOptionPane.showMessageDialog(null, "Playlist with this name already exists. Choose another.", "Name Conflict", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int i = mylists.getSelectionModel().getSelectedIndex();
        new PlaylistService().rename(pls.get(i).getPlaylistPK(), name);
        refreshPLS();
        updateMyLists();
        mylisttracks.getItems().clear();
        newname.setText("");

        JOptionPane.showMessageDialog(null, "Playlist renamed successfully", "Playlist renamed", JOptionPane.INFORMATION_MESSAGE);

    }

    @FXML
    private void goSearchOnClick()
    {
        slt = new TrackService().searchArtist(search.getText().trim());
        ObservableList<String> data = searchlist.getItems();
        data.clear();

        int i = 0;
        for (Track t : slt)
            data.add(++i + ".  " + t.getTrackName()
                     + " - " + new ArtistService().find(t.getArtistId()).getArtistName()
                     + " (" + t.getLength() + ")");

    }

    @FXML
    private void addSearchOnClick()
    {
        Integer[] selected = searchlist.getSelectionModel().getSelectedIndices().toArray(new Integer[0]);
        if (selected.length <= 0)
        {
            JOptionPane.showMessageDialog(null, "Please select atleast one track.", "Select track", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<Track> tracks = new ArrayList<Track>();

        for (Integer s : selected)
            tracks.add(slt.get(s));

        int index = playlistcombo.getSelectionModel().getSelectedIndex();
        String plname = playlistcombo.getSelectionModel().getSelectedItem();
        if(plname==null||plname.length()<=0)
        {
            JOptionPane.showMessageDialog(null, "Please select a playlist or provide a name for new playlist.", "Select playlist", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int i = -1;
        if (plname.contains(","))
        {
            String[] zz = plname.split(",");
            plname = zz[0];
            i = Integer.parseInt(zz[1]) - 1;
        }
        if (index >= 0 && plname.equalsIgnoreCase(pls.get(index).getPlaylistPK().getName()))
            try
            {
                new PlaylistService().insertTracks(pls.get(index).getPlaylistPK(), i, tracks.toArray(new Track[0]));
                JOptionPane.showMessageDialog(null, "Playlist updated successfully", "Playlist updated", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Playlist could not be updated. The list is corrutped.", "Update Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
        else
            try
            {
                new PlaylistService().create(new PlaylistPK(user, plname), friend.isSelected(), tracks.toArray(new Track[0]));
                JOptionPane.showMessageDialog(null, "Playlist created successfully", "Playlist created", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (FileNotFoundException ex)
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Playlist could not be created.", "Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }
        refreshPLS();
        updateMyLists();
        newname.setText("");
        mylisttracks.getItems().clear();
    }

}
