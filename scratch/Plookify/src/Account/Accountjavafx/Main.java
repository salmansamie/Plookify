package Accountjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;



public class Main extends Application {
    private Stage primaryStage;
    private BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Plookify");
        primaryStage.setResizable(false);

        MainView();
    }

    // FXML loader for RadioFXML.fxml
    public void MainView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("AccountButton.fxml"));
        mainLayout = loader.load();

        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Make Connection
    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/guxuanyu/Documents/untitled folder/NetBeansSE7/SE7/Plookify/src/Accountjavafx/PlaylistDatabase-2.sqlite");
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
        else {
            Statement stmt;
            try {
                connect.setAutoCommit(false);
                System.out.println("Connection opened");

                stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Users;");
                while(rs.next()){

                    int id = rs.getInt("user_id");
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    String password = rs.getString("password");

                    System.out.println("user_id = " + id);
                    System.out.println("username = " + username);
                    System.out.println("email = " + email);
                    System.out.println("password = " + password);
                }
                rs.close();
                stmt.close();
                connect.close();

            }
            catch (Exception e){
                System.err.println(e.getClass().getName() + ":" + e.getMessage());
                System.exit(0);
            }
        }
    }

    //Main method
    public static void main(String[] args) {
        launch(args);
    }


}

