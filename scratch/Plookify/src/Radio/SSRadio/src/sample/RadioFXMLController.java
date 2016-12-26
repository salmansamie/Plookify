package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class RadioFXMLController {
    private Main maine;

    @FXML
    protected void GotoRadio(ActionEvent event) throws IOException{
        System.out.println("This works");
    }
}