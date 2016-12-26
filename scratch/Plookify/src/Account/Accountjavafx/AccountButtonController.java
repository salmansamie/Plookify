package Accountjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.*;



public class AccountButtonController {
    Main main = new Main();

    @FXML
    private Label userid;

    @FXML
    private Label passid;
    
    @FXML
    private Label username1;
    
    @FXML
    private Label password1;
    
    @FXML
    private Label fullname1;
    
    @FXML
    private Label address1;
    
    @FXML
    private Label no1;
    
    @FXML
    Button btn_login = new Button();
    
    @FXML
    Button btn_reset = new Button();
    
    @FXML
    Button btn_register = new Button();

    @FXML
    TextField txt_username  = new TextField();     //User Input field for Playlist Name

    @FXML
    PasswordField txt_password = new PasswordField(); 
    
    @FXML
    Button btn_submit = new Button();
    
    @FXML
    TextField usernameInsert = new TextField();
    
    @FXML
    PasswordField passwordInsert = new PasswordField();
    
    @FXML
    TextField addressInsert = new TextField();
    
    @FXML
    TextField noInsert = new TextField();
    
    @FXML
    TextField fullnameInsert = new TextField();


    @FXML
    public void AccountButtonPress(ActionEvent event) throws IOException {   //Radio Button Pressed
        System.out.println("Account button is pressed");
        txt_username.setVisible(true);
        txt_password.setVisible(true);
        userid.setVisible(true);
        passid.setVisible(true);
        btn_login.setVisible(true);
        btn_reset.setVisible(true);
        btn_register.setVisible(true);
        username1.setVisible(false);
        password1.setVisible(false);
        fullname1.setVisible(false);
        address1.setVisible(false);
        no1.setVisible(false);
        usernameInsert.setVisible(false);
        passwordInsert.setVisible(false);
        fullnameInsert.setVisible(false);
        addressInsert.setVisible(false);
        noInsert.setVisible(false);
        btn_submit.setVisible(false);
//        main.IsConnected();     //prints whole db on terminal from main
    }

    @FXML
    public void RegisterButtonPress(ActionEvent event) throws IOException {
        System.out.println("Register button is pressed");
        txt_username.setVisible(false);
        txt_password.setVisible(false);
        userid.setVisible(false);
        passid.setVisible(false);
        btn_login.setVisible(false);
        btn_reset.setVisible(false);
        btn_register.setVisible(false);
        username1.setVisible(true);
        password1.setVisible(true);
        fullname1.setVisible(true);
        address1.setVisible(true);
        no1.setVisible(true);
        usernameInsert.setVisible(true);
        passwordInsert.setVisible(true);
        fullnameInsert.setVisible(true);
        addressInsert.setVisible(true);
        noInsert.setVisible(true);
        btn_submit.setVisible(true);
        
    }
    
    @FXML
    public void SubmitButtonPress(ActionEvent event) throws IOException {
        
    }
    
    @FXML
    public void LoginButtonPress(ActionEvent event) throws IOException {
        
    }

}