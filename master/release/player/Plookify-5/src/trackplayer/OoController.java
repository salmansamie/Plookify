/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trackplayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Zilan Su
 */
public class OoController implements Initializable {
    private BorderPane tets1;
    private Tab radio;
    @FXML
    private BorderPane pPnae;
    @FXML
    private Tab rPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                    Parent root1,root2,root3;
                    
        //Integrating other group members work
        try {
            root1 = FXMLLoader.load(getClass().getResource("player.fxml"));
            pPnae.setBottom(root1);
            root2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            pPnae.setTop(root2);
//            root3 = FXMLLoader.load(getClass().getResource("RadioFXML.fxml"));
//            rPane.setContent(root3);

            
        } catch (IOException ex) {
            Logger.getLogger(OoController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }    

    
}
