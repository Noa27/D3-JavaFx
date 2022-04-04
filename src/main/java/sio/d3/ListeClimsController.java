package sio.d3;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class ListeClimsController implements Initializable {
    @FXML
    private TableView listeClims;
    @FXML
    private TableColumn marque;
    @FXML
    private TableColumn modele;
    @FXML
    private TableColumn puissance;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listeClims.setEditable(true);
        marque.setCellFactory(TextFieldTableCell.forTableColumn());
        modele.setCellFactory(TextFieldTableCell.forTableColumn());
        puissance.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        Model.connect_to_database();
        listeClims.setItems(Model.getClimatiseurs());

    }
    @FXML
    public void ajoutClimatiseur(MouseEvent mouseEvent) throws IOException
    {
        GestionDesClimatiseurs.setRoot("ajout_climatiseur");
    }
    @FXML
    public void modificationClimatiseur (TableColumn.CellEditEvent cellEditEvent) {
        System.out.println("La valeur est modifiée pour"
                + cellEditEvent.getTableColumn().getText()
                + "=" + cellEditEvent.getNewValue().toString());

            String val = cellEditEvent.getNewValue().toString();
//récupération du climatiseur selectionné
            Climatiseur selectedItem = (Climatiseur) listeClims.getSelectionModel().getSelectedItem();
            selectedItem.setMarque(val);
            int id = selectedItem.getID();
            String attribut = cellEditEvent.getTableColumn().getText();
            Model.updateClimatiseur(id, val, attribut);
    }
    @FXML
    public void actionDeleteClim(MouseEvent mouseEvent) throws IOException
    {
        Climatiseur selectedItem ;
        selectedItem = (Climatiseur)
                listeClims.getSelectionModel().getSelectedItem();
        System.out.println("Suppression de l'item: " +selectedItem.marqueProperty().get());
        Model.getClimatiseurs().remove(selectedItem);
        Model.deleteClimatiseur(selectedItem);
    }
}
