package sample;

import com.sun.javafx.geom.Rectangle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.shape.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main extends Application {
    private Stage primaryStage;
    private BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws IOException{
        Main main = new Main();
        main.IsConnected();         //called here to keep the main method simple

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Plookify");
        MainView();
    }

    // FXML loader
    private void MainView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("RadioFXML.fxml"));
        mainLayout = loader.load();

        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    //Make Connection
    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:track.sqlite");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    //Verify Database Connection
    public void IsConnected(){
        Connection connect = Connector();
        if (connect == null){
            try {
                System.out.println("Cannot connect to database");
                connect.isClosed();
                System.exit(1);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //Main method
    public static void main(String[] args) {
        launch(args);
    }


}
