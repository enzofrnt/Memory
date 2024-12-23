package memory.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.util.Duration;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import memory.MemoryApp;
import memory.Theme;
import memory.om.Jeu;
import memory.om.Partie;
import memory.om.Reponse;

public class PartieController implements Initializable{
    private MemoryApp memoryApp;
    private Stage primaryStage;
    private Jeu jeuActuel;
    private Partie partiActuel;
    private Button boutonEnCours;
    private ArrayList<String> arrayListTheme;
    private Thread tache;
    private final IntegerProperty time = new SimpleIntegerProperty(0);


    @FXML
    private GridPane grille;

    @FXML
    private Label timeLabel;

    @FXML
    private Text joueurJoue;
    @FXML
    private Text Joueur1Stats;
    @FXML
    private Text nbCoupsJ1;
    @FXML
    private Text nbCoupsGagnantJ1;
    @FXML
    private Text Joueur2Stats;
    @FXML
    private Text nbCoupsJ2;
    @FXML
    private Text nbCoupsGagnantJ2;

    @FXML
    private VBox histoVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeLabel.textProperty().bind(time.asString("Durée de la partie : %s"));     
        
        time.addListener(new ChangeListener<Number> () {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                partiActuel.setTemps(newValue.intValue());
            }
        });

        tache = new Thread( () -> {
			try {
				while (true) {
					Platform.runLater(() -> {
						time.set(time.get() + 1);
					});
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
			}
		});
		tache.start();
    }

    public void setMemoryApp(MemoryApp memoryApp) {
        this.memoryApp = memoryApp;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setOnCloseRequest(event -> {
            event.consume();
			actionQuitterMenu();
		});
    }

    @FXML
	private void actionQuitterMenu(){
		Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
		confirm.setTitle("On quitte la partie");
		confirm.setHeaderText("Voulez-vous vraiment quitter la partie ou en relancer une ?");
		confirm.initOwner(primaryStage);

        ButtonType btnQuitter = new ButtonType("Quitter");
        ButtonType btnRelancer = new ButtonType("Relancer");
        ButtonType btnAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirm.getButtonTypes().setAll(btnQuitter, btnRelancer, btnAnnuler);

        Optional<ButtonType> result = confirm.showAndWait();

		if (result.isPresent()) {
            if (result.get() == btnQuitter) {
                tache.interrupt();
                this.memoryApp.loadAccueil();
            } else if (result.get() == btnRelancer) {
                tache.interrupt();
                timeLabel.textProperty().unbind();
                timeLabel.setText("");
                this.memoryApp.loadGame(this.partiActuel.getModeJeux(), this.partiActuel.getNom1(), this.partiActuel.getNom2(), this.partiActuel.getTriche(), this.partiActuel.getTheme(), this.partiActuel.getDifficulteTemps(), this.partiActuel.getTaille());
            }
        }
	}

    public void setGridPaneTaille(String pfModeJeux, int pfTaille, int pfTemps, String pfTheme) {
        if (pfModeJeux.equals("Solo")) {
            this.boutonEnCours = null;
            this.grille.setGridLinesVisible(true);
            this.grille.setHgap(10);
            this.grille.setVgap(10);
            Theme theme = new Theme(pfTheme, pfTaille);
            arrayListTheme = theme.getTheme();
            for (int i = 0; i < pfTaille; i++) {
                for (int j = 0; j < pfTaille; j++) {
                    Button btn = new Button();
                    btn.setPrefSize(50, 50);
                    btn.onActionProperty().set(e -> {
                        partiActuel.setNbCoups1(partiActuel.getNbCoups1()+1);
                        Reponse reponse = this.jeuActuel.jouer(pfTaille*grille.getRowIndex(btn)+grille.getColumnIndex(btn));
                        if (reponse == Reponse.PREMIERE) {
                            Theme.actionTheme(pfTheme, arrayListTheme, btn, this.jeuActuel, pfTaille);
                            this.boutonEnCours = btn;
                            this.boutonEnCours.setDisable(true);
                        }else if (reponse == Reponse.GAGNE) {
                            Theme.actionTheme(pfTheme, arrayListTheme, btn, this.jeuActuel, pfTaille);
                            btn.setDisable(true);
                        }else if (reponse == Reponse.PERDU) {
                            Theme.actionTheme(pfTheme, arrayListTheme, btn, this.jeuActuel, pfTaille);
                            btn.setDisable(true);
                            disableAllButtons(true, pfTaille);
                            PauseTransition pause = new PauseTransition(Duration.millis(pfTemps*1000));
                            pause.setOnFinished(et -> {
                                this.boutonEnCours.setText("");
                                this.boutonEnCours.setStyle("");
                                btn.setText("");
                                btn.setStyle("");
                                this.boutonEnCours.setDisable(false);
                                btn.setDisable(false);
                                disableAllButtons(false, pfTaille);
                            });
                            pause.play();
                        }
                        
                        if (this.jeuActuel.isPartieTerminee()) {
                            tache.interrupt();
                            memoryApp.savePartie();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Partie terminée");
                            alert.setHeaderText("La partie est terminée");
                            alert.setContentText("Vous avez gagné en " + this.partiActuel.getNbCoups1() + " coups");
                            alert.initOwner(primaryStage);

                            ButtonType relancerBtn = new ButtonType("Relancer", ButtonBar.ButtonData.OK_DONE);
                            ButtonType quitterBtn = new ButtonType("Quitter", ButtonBar.ButtonData.CANCEL_CLOSE);

                            alert.getButtonTypes().setAll(relancerBtn, quitterBtn);

                            Button relancerButton = (Button) alert.getDialogPane().lookupButton(relancerBtn);

                            relancerButton.setOnAction(event -> {
                                timeLabel.textProperty().unbind();
                                timeLabel.setText("");
                                this.memoryApp.loadGame(this.partiActuel.getModeJeux(), this.partiActuel.getNom1(), this.partiActuel.getNom2(), this.partiActuel.getTriche(), this.partiActuel.getTheme(), this.partiActuel.getDifficulteTemps(), this.partiActuel.getTaille());
                            });

                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.isPresent() && result.get() == quitterBtn) {
                                this.memoryApp.loadAccueil();
                            }
                        }
                        
                    });
                    this.grille.add(btn, i, j);
                }
            }
        }else if (pfModeJeux.equals("Duo")) {
            this.histoVBox.setVisible(true);
            this.Joueur1Stats.setText("Stat de " + this.partiActuel.getNom1());
            this.Joueur2Stats.setText("Stat de " + this.partiActuel.getNom2());
            this.nbCoupsJ1.setText("nb coups : " + Integer.toString(this.partiActuel.getNbCoups1()));
            this.nbCoupsJ2.setText("nb coups : " + Integer.toString(this.partiActuel.getNbCoups2()));
            this.nbCoupsGagnantJ1.setText("nb coups gagnant : " + Integer.toString(this.partiActuel.getGagnant1()));
            this.nbCoupsGagnantJ2.setText("nb coups gagnant" + Integer.toString(this.partiActuel.getGagnant2()));
            this.grille.setGridLinesVisible(true);
            this.grille.setHgap(10);
            this.grille.setVgap(10);
            Theme theme = new Theme(pfTheme, pfTaille);
            arrayListTheme = theme.getTheme();
            this.joueurJoue.setText(this.partiActuel.getNom1() + " doit joué");
            for (int i = 0; i < pfTaille; i++) {
                for (int j = 0; j < pfTaille; j++) {
                    Button btn = new Button();
                    btn.setPrefSize(50, 50);
                    btn.onActionProperty().set(e -> {
                        
                        if (this.partiActuel.getJoueurActuel() == 1) {
                            partiActuel.setNbCoups1(partiActuel.getNbCoups1()+1);
                            this.nbCoupsJ1.setText("nb coups : " + Integer.toString(this.partiActuel.getNbCoups1()));
                        }else if (this.partiActuel.getJoueurActuel() == 2) {
                            partiActuel.setNbCoups2(partiActuel.getNbCoups2()+1);
                            this.nbCoupsJ2.setText("nb coups : " + Integer.toString(this.partiActuel.getNbCoups2()));
                        }
                        Reponse reponse = this.jeuActuel.jouer(pfTaille*grille.getRowIndex(btn)+grille.getColumnIndex(btn));
                        if (reponse == Reponse.PREMIERE) {
                            Theme.actionTheme(pfTheme, arrayListTheme, btn, this.jeuActuel, pfTaille);
                            this.boutonEnCours = btn;
                            this.boutonEnCours.setDisable(true);
                        }else if (reponse == Reponse.GAGNE) {
                            Theme.actionTheme(pfTheme, arrayListTheme, btn, this.jeuActuel, pfTaille);
                            btn.setDisable(true);
                            
                            if (this.partiActuel.getJoueurActuel() == 1) {
                                this.partiActuel.setGagnant1(partiActuel.getGagnant1()+1);
                                this.nbCoupsGagnantJ1.setText("nb coups gagnant : " + Integer.toString(this.partiActuel.getGagnant1()));
                            }else if (this.partiActuel.getJoueurActuel() == 2) {
                                this.partiActuel.setGagnant2(partiActuel.getGagnant2()+1);
                                this.nbCoupsGagnantJ2.setText("nb coups gagnant : " + Integer.toString(this.partiActuel.getGagnant2()));
                            }
                            
                        }else if (reponse == Reponse.PERDU) {
                            Theme.actionTheme(pfTheme, arrayListTheme, btn, this.jeuActuel, pfTaille);
                            disableAllButtons(true, pfTaille);
                            PauseTransition pause = new PauseTransition(Duration.millis(pfTemps*1000));
                            pause.setOnFinished(et -> {
                                this.boutonEnCours.setText("");
                                this.boutonEnCours.setStyle("");
                                btn.setText("");
                                btn.setStyle("");
                                this.boutonEnCours.setDisable(false);
                                btn.setDisable(false);
                                disableAllButtons(false, pfTaille);
                            });
                            pause.play();
                            if (this.partiActuel.getJoueurActuel() == 1) {
                                this.partiActuel.setJoueurActuel(2);
                                this.joueurJoue.setText(this.partiActuel.getNom2() + " doit joué");
                            }else if (this.partiActuel.getJoueurActuel() == 2) {
                                this.partiActuel.setJoueurActuel(1);
                                this.joueurJoue.setText(this.partiActuel.getNom1() + " doit joué");
                            }
                        }
                        if (this.jeuActuel.isPartieTerminee()) {
                            memoryApp.savePartie();
                            tache.interrupt();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Partie terminée");
                            alert.setHeaderText("Partie terminée");
                            if(this.partiActuel.getGagnant1() > this.partiActuel.getGagnant2()) {
                                alert.setContentText("La partie est terminée, " + this.partiActuel.getNom1() + " a gagné !");
                            }else if(this.partiActuel.getGagnant1() < this.partiActuel.getGagnant2()) {
                                alert.setContentText("La partie est terminée, " + this.partiActuel.getNom2() + " a gagné !");
                            }else {
                                alert.setContentText("La partie est terminée, égalité !");
                            }
                            ButtonType quitterBtn = new ButtonType("Quitter");
                            ButtonType relancerBtn = new ButtonType("Relancer");
                            alert.getButtonTypes().setAll(quitterBtn, relancerBtn);
                            Button relancerButton = (Button) alert.getDialogPane().lookupButton(relancerBtn);
                            relancerButton.setOnAction(event -> {
                                timeLabel.textProperty().unbind();
                                timeLabel.setText("");
                                this.memoryApp.loadGame(this.partiActuel.getModeJeux(), this.partiActuel.getNom1(), this.partiActuel.getNom2(), this.partiActuel.getTriche(), this.partiActuel.getTheme(), this.partiActuel.getDifficulteTemps(), this.partiActuel.getTaille());
                            });
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == quitterBtn) {
                                this.memoryApp.loadAccueil();
                            }
                        }
                    });
                    this.grille.add(btn, i, j);
                }
            }
        }
    }

    private void disableAllButtons(boolean disable, int pfTaille) {
        for (int row = 0; row < pfTaille; row++) {
            for (int col = 0; col < pfTaille; col++) {
                Node node = getNodeByRowColumnIndex(row, col, this.grille);
    
                if (node instanceof Button) {
                    Button btn = (Button) node;
    
                    if (disable || this.jeuActuel.isCarteTrouvee(pfTaille * row + col)) {
                        btn.setDisable(true);
                    } else {
                        btn.setDisable(false);
                    }
                }
            }
        }
    }

    private Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
    
        return null;
    }

    public void setJeu(Jeu jeuActuel) {
        this.jeuActuel = jeuActuel;
    }

    public void setPartiActuel(Partie partiActuel) {
        this.partiActuel = partiActuel;
    }
}
