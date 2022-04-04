package sio.d3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Model{
    private static ObservableList<Climatiseur> climatiseurs =
            FXCollections.observableArrayList();
    public static ObservableList<Climatiseur> getClimatiseurs() {
        return climatiseurs;
    }

    private static Connection connexion;
    private static Statement stmt;

    public static void connect_to_database()
    {
       try
        {
//configuration de la base de données
            String url1 = "jdbc:mysql://localhost:3307/" + "GestionClims?&useJDBCCompliantTimezoneShift=true" +
            "GuseLegacyDatetimeCode=false&serverTimezone=UTC";
//utilisateur pour connexion à la BDD
            String user = "user";
//mot de passe pour connexion à la BDD
            String password = "mdpclient";
//établissement de la connexion à la.base de données
            connexion = DriverManager.getConnection(url1, user, password);
            if (connexion != null)
            {
                System.out.println("La connexion est effective. Il faut faire des requêtes !");
                selectClimatiseurs();
            }
        }
        catch (SQLException ex)
        {

//s'il y a une erreur le message suivant s'affiche en Ligne de commande
            System.out.println("Erreur de connexion à la bdd. Identifiant ou mot de passe invalide,");
            ex.printStackTrace();
        }
    }
    public static void insertClimatiseur(Climatiseur clim)
    {
        try
        {
            System.out.println("Ajout d'un climatiseur");
            stmt = connexion.createStatement();
//définition de la requête
            String sql = "INSERT INTO climatiseurs (marque, modele, puissance, surfaceMin, surfaceMax) VALUES (\""+clim.getMarque()+"\", \""+ clim.getModele() + "\", " + clim.getPuissance()
                    + ", " + clim.getSurfaceMinCouverte()+ ", " + clim.getSurfaceMaxCouverte()+ ")";
            System.out.println("requête :"+sql); // On log la requête précédente dans le terminal
//exécution de la requête
            Boolean req = stmt.execute(sql);
//parcours des enregistrements résultats,
//création de nouveaux objets "climatiseurs" et
//ajout de cet objet dans la liste
        }
        catch(SQLException se) // Au cas où il y a une erreur, c'est pour avoir le message qu'il y en a une.
        {
//exécuté si la requête ne s'est pas bien exécutée
            se.printStackTrace();
            System.err.println("La requête s'est mal déroulée.");
        }
    }
    public static void deleteClimatiseur(Climatiseur clim)
    {
        try
        {
            System.out.println("Suppression d'un climatiseur");
            stmt = connexion.createStatement();
//définition de la requête
            PreparedStatement sql = connexion.prepareStatement("DELETE FROM climatiseurs WHERE id = ?");
            sql.setInt(1, clim.getID());
            sql.executeUpdate();
            System.out.println("requête :"+sql); // On log la requête précédente dans le terminal
            getClimatiseurs().remove(clim);
        }
        catch(SQLException se) // Au cas où il y a une erreur, c'est pour avoir le message qu'il y en a une.
        {
//exécuté si la requête ne s'est pas bien exécutée
            se.printStackTrace();
            System.err.println("La requête s'est mal déroulée.");
        }
    }
    public static void updateClimatiseur(int id, String val, String attribut)
    {
        try
        {
            stmt = connexion.createStatement();
            PreparedStatement sql = connexion.prepareStatement("UPDATE climatiseurs SET " + attribut + " = ? WHERE id = ?");
            sql.setString(1, val);
            sql.setInt(2, id);
            sql.executeUpdate();
            System.out.println("requête :"+sql); // On log la requête précédente dans le terminal
        }
        catch(SQLException se) // Au cas où il y a une erreur, c'est pour avoir le message qu'il y en a une.
        {
//exécuté si la requête ne s'est pas bien exécutée
            se.printStackTrace();
            System.err.println("La requête s'est mal déroulée.");
        }
    }
    public static void selectClimatiseurs()
    {
        try
        {
            climatiseurs.clear();
            System.out.println("Chargement des climatiseurs..."); // On "log" dans le terminal que l'application est entrain de charger l'application.
//création d'un objet "Statement" qui permettra d'exécuter la requête
            stmt = connexion.createStatement(); // Création d'un objet Statement
//définition de la requête
            String sql = "SELECT * from climatiseurs"; // On sélectionne toutes les clims dans la BDD
            System.out.println("requête :"+sql); // On log la requête précédente dans le terminal
//exécution de la requête
            ResultSet rs = stmt.executeQuery(sql); // On exécute la requête SQL précédente
//parcours des enregistrements résultats,
//création de nouveaux objets "climatiseurs" et
//ajout de cet objet dans la liste
            while(rs.next()) // Cette boucle permet de créer des objets Climatiseur à partir des enregistrements de la BDD pour pouvoir ensuite les afficher dans l'app.
            {
                String mar = rs.getString("marque");
                String mod = rs.getString("modele");
                int pui = rs.getInt("puissance");
                Climatiseur c = new Climatiseur(mar,mod,pui);
                int id = rs.getInt("id");
                c.setID(id);
                climatiseurs.add(c);
            }
            rs.close();
        }
        catch(SQLException se) // Au cas où il y a une erreur, c'est pour avoir le message qu'il y en a une.
        {
//exécuté si la requête ne s'est pas bien exécutée
            se.printStackTrace();
            System.err.println("La requête c'est mal déroulée.");
        }
        finally
        {
            if(stmt!=null)
            {
                try
                {
                    stmt.close(); // A la fin des requêtes, on referme la recherche d'informations dans la BDD
                } catch (SQLException ex)
                {
                }
            }
        }
    }
}
