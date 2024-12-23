package memory.view;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import memory.MemoryApp;
import memory.om.Partie;

public class ListePartieController implements Initializable {
    private MemoryApp memoryApp;
    private Stage primaryStage;
    private ObservableList<Partie> listObservPartie;

    @FXML
    private ListView<Partie> listViewPartie;

    @FXML
    private Button btnRecherche;

    @FXML
    private TextField textFieldRecherche;

    @FXML
    private Text textRecherche;

    @FXML
    private MenuButton mode;
    @FXML
    private MenuButton triche;
    @FXML
    private MenuButton taille;
    @FXML
    private MenuButton theme;
    @FXML
    private MenuButton temps;

    @FXML
    private RadioMenuItem modeSolo;
    @FXML
    private RadioMenuItem modeDuo; 
    @FXML
    private RadioMenuItem modeAucun;
    @FXML
    private RadioMenuItem tricheActive;
    @FXML
    private RadioMenuItem tricheDesactive;
    @FXML
    private RadioMenuItem tricheDesactiveActive;
    @FXML
    private RadioMenuItem taille2;
    @FXML
    private RadioMenuItem taille4;
    @FXML
    private RadioMenuItem taille6;
    @FXML
    private RadioMenuItem taille8;
    @FXML
    private RadioMenuItem taille0;
    @FXML
    private RadioMenuItem couleur;
    @FXML
    private RadioMenuItem chiffre;
    @FXML
    private RadioMenuItem lettre;
    @FXML
    private RadioMenuItem pasTheme;
    @FXML
    private RadioMenuItem temps1;
    @FXML
    private RadioMenuItem temps2;
    @FXML
    private RadioMenuItem temps3;
    @FXML
    private RadioMenuItem temps4;
    @FXML
    private RadioMenuItem temps0;


    @FXML
    private void actionQuitter() {
        memoryApp.loadAccueil();
    }

    @FXML
    private void actionSupprimerParties() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression des parties");
        alert.setContentText("Voulez-vous vraiment supprimer toutes les parties ?");
        alert.showAndWait();

        if (alert.getResult().getText().equals("OK")) {
            // this.listObservPartie.clear();
            // memoryApp.saveParties();
            // memoryApp.loadHistorique();
        }
        
    }

    @FXML
    private void actionRecherche() {
        triage();
    }

    @FXML
    private void modeSolo() {
        this.mode.setText("Solo");
        triage();
    }

    @FXML
    private void modeDuo() {
        this.mode.setText("Duo");
        triage();
    }

    @FXML
    private void modeAucun() {
        this.mode.setText("Aucun mode");
        triage();
    }

    @FXML
    private void tricheActive() {
        this.triche.setText("Triche activé");
        triage();
    }

    @FXML
    private void tricheDesactive() {
        this.triche.setText("Triche désactivé");
        triage();
    }

    @FXML
    private void tricheDesactiveActive() {
        this.triche.setText("Triche désactivé et activé");
        triage();
    }

    @FXML
    private void taille2() {
        this.taille.setText("Taille 2");
        triage();
    }

    @FXML
    private void taille4() {
        this.taille.setText("Taille 4");
        triage();
    }

    @FXML
    private void taille6() {
        this.taille.setText("Taille 6");
        triage();
    }

    @FXML
    private void taille8() {
        this.taille.setText("Taille 8");
        triage();
    }

    @FXML
    private void taille0() {
        this.taille.setText("Pas de taille");
        triage();
    }

    @FXML
    private void couleur() {
        this.theme.setText("Couleur");
        triage();
    }

    @FXML
    private void chiffre() {
        this.theme.setText("Chiffre");
        triage();
    }

    @FXML
    private void lettre() {
        this.theme.setText("Lettre");
        triage();
    }

    @FXML
    private void pasTheme() {
        this.theme.setText("Pas de thème");
        triage();
    }

    @FXML
    private void temps1() {
        this.temps.setText("Temps : 1s");
        triage();
    }

    @FXML
    private void temps2() {
        this.temps.setText("Temps : 2s");
        triage();
    }

    @FXML
    private void temps3() {
        this.temps.setText("Temps : 3s");
        triage();
    }

    @FXML
    private void temps4() {
        this.temps.setText("Temps : 4s");
        triage();
    }

    @FXML
    private void temps0() {
        this.temps.setText("Pas de temps");
        triage();
    }

    private void triage() {
        Comparator<Partie> comparateurTemps = Comparator.comparingInt(Partie::getTemps);
        Predicate<Partie> nouveauPredicat = partie -> true;

        if (this.modeSolo.isSelected()) {
            this.btnRecherche.setDisable(false);
            this.textFieldRecherche.setDisable(false);
            if (this.textFieldRecherche.getText().equals("Disponible en mode Solo")) {
                this.textFieldRecherche.setText("");     
            }
            this.textRecherche.setStyle("-fx-text-inner-color: Black;");
            if (!this.textFieldRecherche.getText().isEmpty()) {
                nouveauPredicat = nouveauPredicat.and(partie -> partie.getNom1().equalsIgnoreCase(this.textFieldRecherche.getText()));
            }
        } else {
            this.btnRecherche.setDisable(true);
            this.textFieldRecherche.setDisable(true);
            this.textRecherche.setStyle("-fx-text-inner-color: #9a9a9a;");
            this.textFieldRecherche.setText("Disponible en mode Solo");            
        }
    
        if (this.modeSolo.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getModeJeux().equalsIgnoreCase("Solo"));
        } else if (this.modeDuo.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getModeJeux().equalsIgnoreCase("Duo"));
        }
    
        if (this.tricheActive.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getTriche());
        } else if (this.tricheDesactive.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> !partie.getTriche());
        }
    
        if (this.taille2.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getTaille() == 2);
        } else if (this.taille4.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getTaille() == 4);
        } else if (this.taille6.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getTaille() == 6);
        } else if (this.taille8.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getTaille() == 8);
        }
    
        if (this.couleur.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getTheme().equalsIgnoreCase("Couleur"));
        } else if (this.chiffre.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getTheme().equalsIgnoreCase("Chiffre"));
        } else if (this.lettre.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getTheme().equalsIgnoreCase("Lettre"));
        }

        if (this.temps1.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getDifficulteTemps() == 1);
        } else if (this.temps2.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getDifficulteTemps() == 2);
        } else if (this.temps3.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getDifficulteTemps() == 3);
        } else if (this.temps4.isSelected()) {
            nouveauPredicat = nouveauPredicat.and(partie -> partie.getDifficulteTemps() == 4);
        }
        
        FilteredList<Partie> partiesFiltred = new FilteredList<>(listObservPartie, nouveauPredicat);
    
        SortedList<Partie> partiesSorted = new SortedList<>(partiesFiltred, comparateurTemps);
    
        listViewPartie.setItems(partiesSorted);
    }

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.btnRecherche.setDisable(true);
        this.textFieldRecherche.setDisable(true);
        this.textFieldRecherche.setText("Disponible en mode Solo");
        this.textRecherche.setStyle("-fx-text-inner-color: #9a9a9a;");
    }

    public void setMemoryApp(MemoryApp memoryApp) {
        this.memoryApp = memoryApp;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setListePartie(ObservableList<Partie> listePartie) {
        this.listViewPartie.setItems(listePartie);
        this.listObservPartie = listePartie;

        
    }
}
