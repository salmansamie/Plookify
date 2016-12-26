package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.util.prefs.*;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;

/**
 * Created by Salman on 16-Mar-16.
 */

public class RadioFXMLController {
    Main main = new Main();

    @FXML
    private Label radioLabel;

    @FXML
    private Label plName;

    @FXML
    private Label arName;

    @FXML
    Button buttonGenerate = new Button();

    @FXML
    TextField playlistNameInsert = new TextField();     //User Input field for Playlist Name

    @FXML
    TextField artistNameInsert = new TextField();       //User Input field for Artist Name

    @FXML
    public void GotoRadio(ActionEvent event) throws IOException {   //Radio Button Pressed
        System.out.println("Radio button is pressed");
        radioLabel.setText("Radio Generator  ");
        plName.setText("Playlist Name : ");
        arName.setText("Artist Name : ");
        playlistNameInsert.setVisible(true);
        artistNameInsert.setVisible(true);
        buttonGenerate.setVisible(true);
//        main.IsConnected();     //prints whole db on terminal from main(testing)
    }

    @FXML
    public void SaveRadio(ActionEvent event) throws IOException{    //Save Button Pressed
        SearchArtCreate();
    }

    public void SearchArtCreate() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:TrackPlay.sqlite");
            if (connect == null) {
                try {
                    System.out.println("Cannot connect to database");
                    connect.isClosed();
                    System.exit(1);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
/******************* Radio LOGIC ************************/
            else {  // ON SUCCESSFUL RUN
                    try {
                        System.out.println("Connection stat--> OK");
                        System.out.println("====" + connect.isClosed());
                        Statement statement = connect.createStatement();

                        System.out.println("11");   //tracing the run
                        ResultSet resultset = statement.executeQuery("SELECT * FROM Track WHERE Artist = '"+ artistNameInsert.getText() + "'");
                        System.out.println("22");   //tracing resultset execution

                        String selectedGenre = resultset.getString("Genre");
                        System.out.println("33");   //tracing usrInputArtGenre
                        System.out.println("Genre obtained--> " + selectedGenre);

                        String PlaylistName = playlistNameInsert.getText();
                        System.out.println("Playlist Name Obtained--> "+ PlaylistName);

                        /*********** Retrieving last record id in PlaylistId ************/
                        ResultSet rspointer = statement.executeQuery("SELECT * FROM Playlist WHERE PlaylistId=(SELECT MAX(PlaylistId) FROM Playlist)");
                        ResultSetMetaData rsmd = rspointer.getMetaData();
                        int PlayId = rsmd.getColumnCount();
                        System.out.println("The Last slot--> " + (PlayId+1));       //getting the the last PlaylistId count

                        System.out.println("\n*Generating 10 Random Tracks Below: ");

                        //Preparing statement to insert the Playlist and return the new generated number using the auto-increment feature of the database.
                        PreparedStatement prest = connect.prepareStatement("INSERT INTO Playlist (PlaylistName)"+ "VALUES('" + PlaylistName + "');", Statement.RETURN_GENERATED_KEYS);
                        prest.executeUpdate();
                        ResultSet rs = prest.getGeneratedKeys();

                        int PlaylistId = 0;
                        if(rs.next()) {
                            PlaylistId = rs.getInt(1);
                        }
                        ResultSet resultsetRandom = statement.executeQuery("SELECT * FROM Track WHERE Genre = '" + selectedGenre +"' ORDER BY RANDOM() LIMIT 10");

                        while (resultsetRandom.next()){
                            int TrackId = resultsetRandom.getInt("TrackId");
                            String TrackTitle = resultsetRandom.getString("TrackTitle");
                            String Artist = resultsetRandom.getString("Artist");
                            String Genre = resultsetRandom.getString("Genre");
                            String Album = resultsetRandom.getString("Album");

                            System.out.println("PlaylistId: " +PlaylistId);
                            System.out.println("TrackId: " +TrackId);
                            System.out.println("TrackTitle: " +TrackTitle);
                            System.out.println("Artist: " +Artist);
                            System.out.println("Genre: " +Genre);
                            System.out.println("Album: " +Album);
                            System.out.println("\n");

                            //Creating a new statement or close/open for every update, otherwise the database throws a multi_user exception
                            connect.createStatement().executeUpdate("INSERT INTO PlaylistTrack (PlaylistId,TrackId)"+ "VALUES(" + PlaylistId+ ",'" + TrackId + "');");
                        }
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
            }
            connect.close();
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
    }

    public boolean IsSubscribed(boolean subscription){      //checking subscription

        String subsQuery = "SELECT * FROM Account WHERE subscription = ?";
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:TrackPlay.sqlite");

            PreparedStatement pst = conn.prepareStatement(subsQuery);
            pst.setBoolean(1, subscription);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                System.out.println("Customer subscribed");
                return true;
            }else{
                return false;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return true;
    }
}