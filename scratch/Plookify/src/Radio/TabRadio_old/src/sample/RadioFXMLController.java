package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

//    buttonGenerate.setOnAction(new MyButtonHandler());

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
//        main.IsConnected();     //prints whole db on terminal from main
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
/******************* RADIO LOGIC************************/
            else {  // ON SUCCESSFUL RUN
                    try {
                        System.out.println("Connection stat-> OK");
                        Statement statement = connect.createStatement();
                        ResultSet resultset = statement.executeQuery("SELECT * FROM Track WHERE Artist = '"+ artistNameInsert.getText() + "'");

                        String selectedGenre = resultset.getString("Genre");
                        System.out.println("Genre obtained -> " + selectedGenre);

                        String PlaylistName = playlistNameInsert.getText();

                        ResultSet resultsetRandom = statement.executeQuery("SELECT * FROM Track WHERE Genre = '" + selectedGenre +"' ORDER BY RANDOM() LIMIT 10");
                        System.out.println("\nGenerating 10 Random Tracks Below: ");

                        int PlaylistId = 1;


                        while (resultsetRandom.next()){
                            String title = resultsetRandom.getString("TrackTitle");
                            System.out.println(title);
                            statement.executeUpdate("INSERT INTO Playlist (PlaylistId,PlaylistName)"+ "VALUES(" + PlaylistId+ ",'" + PlaylistName + "');");

                            PlaylistId++;
                        }
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
            }
/********************************************************/
            connect.close();
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
    }

    public boolean IsSubscribed(boolean subscription){

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