package sio.d3;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.util.List;
import java.util.ArrayList;

public class AjoutClimController {
    @FXML
    private TextArea valMarque;
    @FXML
    private TextArea valPuissance;
    @FXML
    private TextArea valModele;
    @FXML
    private TextArea valSalle;
    @FXML
    private TextArea valBatiment;
/*  @FXML
    private Label marque; */
    /*@FXML
    private void createClimatiseur() throws IOException {
        Climatiseur clim = new Climatiseur(valMarque.getText(), valModele.getText(), Integer.valueOf(valPuissance.getText()));
        Climatiseurs.add(clim);
    }*/
@FXML
private void handleButtonAction(ActionEvent event) throws IOException
{
        int puiss = Integer.valueOf(valPuissance.getText()).intValue();
        String marque = valMarque.getText();
        String model = valModele.getText();
        String salle = valSalle.getText();
        String batiment = valBatiment.getText();
    //création d'un nouvel objetva.)
        Climatiseur c = new Climatiseur(salle, batiment, marque,model,puiss);
    //ajout du climatiseur dans la liste du climatiseur du modèle
        Model.insertClimatiseur(c);
    //on revient à la fenêtre d'affichage de la liste de climatiseurs.
        GestionDesClimatiseurs.setRoot("accueil_liste");
}
    @FXML
    public void initialize()
    {

    }
   /* public static void AfficherClimatiseurs() {
        int a = 1;
        for(Climatiseur compteur: Climatiseurs){
            System.out.println ("Climatiseur " + a + " " + compteur.getMarque() + " : " + compteur.getPuissance() + " BTU, de " + compteur.getSurfaceCouverte());
            a++;
        }
    }*/
}
