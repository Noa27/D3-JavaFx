package sio.d3;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class SecondaryController {

    @FXML
    private Label liste;
    @FXML
    private void switchToPrimary() throws IOException {
        GestionDesClimatiseurs.setRoot("primary");
    }
}