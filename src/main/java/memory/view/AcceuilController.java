package memory.view;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import memory.MemoryApp;

public class AcceuilController implements Initializable {

    private MemoryApp memoryApp;
    private Stage primaryStage;
    private int tailleDifficulte;
    private int tempsDifficulte;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    public void actionAPropos() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("A propos");
        alert.setHeaderText("Memory by Enzo Fournet in JavaFX");
        alert.setHeight(700);

        URL imageUrl = getClass().getResource("image/PP.jpeg");
        Image image = new Image(imageUrl.toString());
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(64);
        imageView.setFitHeight(64);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        Rectangle clip = new Rectangle(0, 0, 64, 64);
        clip.setArcWidth(20);
        clip.setArcHeight(20);

        imageView.setClip(clip);
        
        alert.getDialogPane().setGraphic(imageView);

        alert.setContentText("Memory est un jeu de mémoire développé par enzo Fournet dans le cadre d'un projet scolaire.\n A l'IUT de Blagnac, en 1ème année de BUT Informatique.\n\n");
        alert.showAndWait();
    }

    @FXML
	private void actionQuitterMenu(){
		Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
		confirm.setTitle("Fermerture de l'application");
		confirm.setHeaderText("Voulez-vous vraiment quitter ?");
		confirm.initOwner(primaryStage);
		confirm.showAndWait();

		ButtonType reponse = confirm.getResult();
		if (reponse == ButtonType.OK) {
			this.primaryStage.close();
		}
	}

    @FXML
    private void actionNouvellePartie() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        HBox hboxNbJoueur = new HBox();
        Label nbJoueurText = new Label("Nombre de joueurs");
        RadioButton btnNbJoueurSolo = new RadioButton("Solo");
        RadioButton btnNbJoueurDuo = new RadioButton("Duo");
        javafx.scene.control.ToggleGroup nbJoueurGrp = new javafx.scene.control.ToggleGroup();
        nbJoueurGrp.getToggles().add(btnNbJoueurSolo);
        nbJoueurGrp.getToggles().add(btnNbJoueurDuo);

        HBox hboxNomJoueur1 = new HBox();
        Label nomJoueurText1 = new Label("Nom du joueur");
        TextField nomJoueur1 = new TextField();

        HBox hboxNomJoueur2 = new HBox();
        Label nomJoueurText2 = new Label("Nom du joueur 2");
        TextField nomJoueur2 = new TextField();
        
        HBox hboxTriche = new HBox();
        ToggleButton tricheButton = new ToggleButton("Désactivé");
        Label tricheText = new Label("Triche");

        HBox hboxTheme = new HBox();
        Label themeText = new Label("Choisir un thème");
        MenuButton themeMenu = new MenuButton();
        themeMenu.setText("Thème");
        RadioMenuItem chiffre = new RadioMenuItem("Chiffre");
        RadioMenuItem legume = new RadioMenuItem("Couleur");
        RadioMenuItem Lettre = new RadioMenuItem("Lettre");
        javafx.scene.control.ToggleGroup themeMenuGrp = new javafx.scene.control.ToggleGroup();
        chiffre.setToggleGroup(themeMenuGrp);
        legume.setToggleGroup(themeMenuGrp);
        Lettre.setToggleGroup(themeMenuGrp);


        themeMenu.getItems().addAll(chiffre, legume, Lettre);
        chiffre.setOnAction(e -> themeMenu.setText("Chiffre"));
        legume.setOnAction(e -> themeMenu.setText("Couleur"));
        Lettre.setOnAction(e -> themeMenu.setText("Lettre"));
        
        HBox hboxDifficulteTaille = new HBox();
        Label difficulteTailleText = new Label("Choisir une difficulté de taille");
        MenuButton difficulteTailleMenu = new MenuButton();
        difficulteTailleMenu.setText("Difficulté");
        RadioMenuItem tailleFacile = new RadioMenuItem("2x2");
        RadioMenuItem tailleMoyen = new RadioMenuItem("4x4");
        RadioMenuItem tailleDifficile = new RadioMenuItem("6x6");
        RadioMenuItem tailleExpert = new RadioMenuItem("8x8");
        
        javafx.scene.control.ToggleGroup difficulteTailleMenuGrp = new javafx.scene.control.ToggleGroup();
        tailleFacile.setToggleGroup(difficulteTailleMenuGrp);
        tailleMoyen.setToggleGroup(difficulteTailleMenuGrp);
        tailleDifficile.setToggleGroup(difficulteTailleMenuGrp);
        tailleExpert.setToggleGroup(difficulteTailleMenuGrp);

        difficulteTailleMenu.getItems().addAll(tailleFacile, tailleMoyen, tailleDifficile, tailleExpert);
        tailleFacile.setOnAction(e -> difficulteTailleMenu.setText("2x2"));
        tailleMoyen.setOnAction(e -> difficulteTailleMenu.setText("4x4"));
        tailleDifficile.setOnAction(e -> difficulteTailleMenu.setText("6x6"));
        tailleExpert.setOnAction(e -> difficulteTailleMenu.setText("8x8"));

        difficulteTailleMenuGrp.selectedToggleProperty().addListener(new ChangeListener<javafx.scene.control.Toggle>() {
            @Override
            public void changed(ObservableValue<? extends javafx.scene.control.Toggle> observable, javafx.scene.control.Toggle oldValue, javafx.scene.control.Toggle newValue) {
                RadioMenuItem selectedItem = (RadioMenuItem) newValue;
                if (selectedItem != null) {
                    switch (selectedItem.getText()) {
                        case "2x2":
                            tailleDifficulte = 2;
                            break;
                        case "4x4":
                            tailleDifficulte = 4;
                            break;
                        case "6x6":
                            tailleDifficulte = 6;
                            break;
                        case "8x8":
                            tailleDifficulte = 8;
                            break;
                    }
                }
            }
        });

        HBox hboxDifficulteTemps = new HBox();
        Label difficulteTempsText = new Label("Choisir une difficulté de temps");
        MenuButton difficulteTempsMenu = new MenuButton();
        difficulteTempsMenu.setText("Difficulté");
        RadioMenuItem tempsFacile = new RadioMenuItem("1 seconde");
        RadioMenuItem tempsMoyen = new RadioMenuItem("2 secondes");
        RadioMenuItem tempsDifficile = new RadioMenuItem("3 secondes");
        RadioMenuItem tempsExpert = new RadioMenuItem("4 secondes");

        javafx.scene.control.ToggleGroup difficulteTempsMenuGrp = new javafx.scene.control.ToggleGroup();
        tempsFacile.setToggleGroup(difficulteTempsMenuGrp);
        tempsMoyen.setToggleGroup(difficulteTempsMenuGrp);
        tempsDifficile.setToggleGroup(difficulteTempsMenuGrp);
        tempsExpert.setToggleGroup(difficulteTempsMenuGrp);

        difficulteTempsMenu.getItems().addAll(tempsFacile, tempsMoyen, tempsDifficile, tempsExpert);
        tempsFacile.setOnAction(e -> difficulteTempsMenu.setText("1 seconde"));
        tempsMoyen.setOnAction(e -> difficulteTempsMenu.setText("2 secondes"));
        tempsDifficile.setOnAction(e -> difficulteTempsMenu.setText("3 secondes"));
        tempsExpert.setOnAction(e -> difficulteTempsMenu.setText("4 secondes"));

        difficulteTempsMenuGrp.selectedToggleProperty().addListener(new ChangeListener<javafx.scene.control.Toggle>() {
            @Override
            public void changed(ObservableValue<? extends javafx.scene.control.Toggle> observable, javafx.scene.control.Toggle oldValue, javafx.scene.control.Toggle newValue) {
                RadioMenuItem selectedItem = (RadioMenuItem) newValue;
                if (selectedItem != null) {
                    switch (selectedItem.getText()) {
                        case "1 seconde":
                            tempsDifficulte = 1;
                            break;
                        case "2 secondes":
                            tempsDifficulte = 2;
                            break;
                        case "3 secondes":
                            tempsDifficulte = 3;
                            break;
                        case "4 secondes":
                            tempsDifficulte = 4;
                            break;
                    }
                }
            }
        });

        hboxNbJoueur.getChildren().add(nbJoueurText);
        hboxNbJoueur.getChildren().add(btnNbJoueurSolo);
        hboxNbJoueur.getChildren().add(btnNbJoueurDuo);
        hboxNbJoueur.setMinWidth(200);
        hboxNbJoueur.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hboxNbJoueur.setSpacing(10);

        hboxNomJoueur1.getChildren().add(nomJoueurText1);
        hboxNomJoueur1.getChildren().add(nomJoueur1);
        hboxNomJoueur1.setMinWidth(200);
        hboxNomJoueur1.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hboxNomJoueur1.setSpacing(10);

        hboxNomJoueur2.getChildren().add(nomJoueurText2);
        hboxNomJoueur2.getChildren().add(nomJoueur2);
        hboxNomJoueur2.setMinWidth(200);
        hboxNomJoueur2.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hboxNomJoueur2.setSpacing(10);

        hboxTriche.getChildren().add(tricheText);
        hboxTriche.getChildren().add(tricheButton);
        hboxTriche.setMinWidth(200);
        hboxTriche.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hboxTriche.setSpacing(10);

        hboxTheme.getChildren().add(themeText);
        hboxTheme.getChildren().add(themeMenu);
        hboxTheme.setMinWidth(200);
        hboxTheme.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hboxTheme.setSpacing(10);

        hboxDifficulteTaille.getChildren().add(difficulteTailleText);
        hboxDifficulteTaille.getChildren().add(difficulteTailleMenu);
        hboxDifficulteTaille.setMinWidth(200);
        hboxDifficulteTaille.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hboxDifficulteTaille.setSpacing(10);

        hboxDifficulteTemps.getChildren().add(difficulteTempsText);
        hboxDifficulteTemps.getChildren().add(difficulteTempsMenu);
        hboxDifficulteTemps.setMinWidth(200);
        hboxDifficulteTemps.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hboxDifficulteTemps.setSpacing(10);

        VBox vbox = new VBox();
        vbox.getChildren().add(hboxNbJoueur);
        vbox.getChildren().add(hboxNomJoueur1);
        btnNbJoueurDuo.setOnAction(e -> {
            if (btnNbJoueurDuo.isSelected()) {
                vbox.getChildren().add(2, hboxNomJoueur2);
                nomJoueurText1.setText("Nom du joueur 1");
            }
        });
        btnNbJoueurSolo.setOnAction(e -> {
            if (btnNbJoueurSolo.isSelected()) {
                vbox.getChildren().remove(hboxNomJoueur2);                
                nomJoueurText1.setText("Nom du joueur");
            }
        });
        vbox.getChildren().add(hboxTriche);
        vbox.getChildren().add(hboxTheme);
        vbox.getChildren().add(hboxDifficulteTaille);
        vbox.getChildren().add(hboxDifficulteTemps);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setSpacing(20);

        

        tricheButton.setOnAction(e -> {
            if (tricheButton.isSelected()) {
                tricheButton.setText("Activé");
            } else {
                tricheButton.setText("Désactivé");
            }
        });

        ButtonType LancerButton = new ButtonType("Lancer");
        ButtonType AnnulerButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        DialogPane customDialogPane = new DialogPane() {
            @Override
            protected Node createButton(ButtonType buttonType) {
                Button button = (Button) super.createButton(buttonType);

                if (buttonType == LancerButton) {
                    button.addEventFilter(ActionEvent.ACTION, event -> {
                        String erreur = "";
                        if (!btnNbJoueurDuo.isSelected() && !btnNbJoueurSolo.isSelected()) {
                            erreur += "Vous devez choisir le nombre de joueur\n";
                            btnNbJoueurDuo.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 3;");
                            btnNbJoueurSolo.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 3;");
                        }
                        if (nomJoueur1.getText().isEmpty()) {
                            if (!btnNbJoueurDuo.isSelected()) {
                                erreur += "Le nom du joueur doit être renseigné\n";
                            }else {
                                erreur += "Le nom du joueur 1 doit être renseigné\n";
                            }
                            
                            nomJoueur1.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 3;");
                        }
                        if(btnNbJoueurDuo.isSelected() && nomJoueur2.getText().isEmpty()) {
                            erreur += "Vous avez choisi Duo, le nom du joueur 2 doit donc être renseigné\n";
                            nomJoueur2.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 3;");
                        }
                        if ( !nomJoueur2.getText().isEmpty() && !nomJoueur1.getText().isEmpty() && nomJoueur1.getText().equals(nomJoueur2.getText()) && btnNbJoueurDuo.isSelected()) {
                            erreur += "Vous avez choisi Duo, les noms des joueurs doivent être différents\n";
                            nomJoueur1.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 3;");
                            nomJoueur2.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 3;");
                        }
                        if (themeMenu.getText() == "Thème") {
                            erreur += "Vous devez choisir un thème\n";
                            themeMenu.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 3;");
                        }
                        if (difficulteTailleMenu.getText() == "Difficulté") {
                            erreur += "Vous devez choisir une taille de difficulté\n";
                            difficulteTailleMenu.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 3;");
                        }
                        if (difficulteTempsMenu.getText() == "Difficulté") {
                            erreur += "Vous devez choisir un temps de difficulté\n";
                            difficulteTempsMenu.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-style: solid; -fx-border-radius: 3;");
                        }
                        if (!erreur.isEmpty()) {
                            Alert alertErreur = new Alert(Alert.AlertType.ERROR);
                            alertErreur.initModality(Modality.NONE);
                            alertErreur.setTitle("Erreur");
                            alertErreur.setHeaderText("Erreur de configuration");
                            alertErreur.setContentText(erreur);
                            alertErreur.showAndWait();

                            Timeline timeline = new Timeline(
                                new KeyFrame(Duration.seconds(2), setVisibleNullError -> {      
                                    btnNbJoueurDuo.setStyle("-fx-text-fill: black; -fx-text-fill : black; -fx-border-color: none;");
                                    btnNbJoueurSolo.setStyle("-fx-text-fill: black; -fx-text-fill : black; -fx-border-color: none;");
                                    nomJoueur1.setStyle("-fx-text-fill: black; -fx-text-fill : black; -fx-border-color: none;");
                                    nomJoueur2.setStyle("-fx-text-fill: black; -fx-text-fill : black; -fx-border-color: none;");
                                    themeMenu.setStyle("-fx-text-fill: black; -fx-text-fill : black; -fx-border-color: none;");
                                    difficulteTailleMenu.setStyle("-fx-text-fill: black; -fx-text-fill : black; -fx-border-color: none;");
                                    difficulteTempsMenu.setStyle("-fx-text-fill: black; -fx-text-fill : black; -fx-border-color: none;");
                                })
                            );
                            timeline.play();
                            event.consume();
                        }else{
                            String modeJeux;
                            if(btnNbJoueurDuo.isSelected()) {
                                modeJeux = "Duo";
                            }else {
                                modeJeux = "Solo";
                            }

                            String pfNomJoeur2;
                            if (btnNbJoueurDuo.isSelected()) {
                                pfNomJoeur2 = nomJoueur2.getText();
                            }else {
                                pfNomJoeur2 = null;
                            }
                            memoryApp.loadGame(modeJeux,  nomJoueur1.getText(), pfNomJoeur2, tricheButton.isSelected(), themeMenu.getText(),tempsDifficulte, tailleDifficulte);
                        }
                    });
                }

                return button;
            }
        };
        customDialogPane.getButtonTypes().setAll(AnnulerButton, LancerButton);
        
        alert.setDialogPane(customDialogPane);
        alert.getDialogPane().setContent(vbox);
        alert.getDialogPane().setMinSize(400, 400);
        alert.setTitle("Configuration de partie");
        alert.setHeaderText("Choix des paramètres de la partie");

        Optional<ButtonType> result = alert.showAndWait();
    }

    @FXML
    private void actionNouvellePartieSoloDefault() {
        String[] mode = {"Chiffre", "Couleur", "Lettre"};
        Random random = new Random();
        int randomMode = random.nextInt(mode.length);
        String randomOption = mode[randomMode];        
        int randomTaille = random.nextInt(4);
        randomTaille = randomTaille * 2 + 2;

        memoryApp.loadGame("Solo",  "Joueur1Default", null, false, randomOption ,1, randomTaille);
    }

    @FXML
    private void actionNouvellePartieDuoDefault() {
        String[] mode = {"Chiffre", "Couleur", "Lettre"};
        Random random = new Random();
        int randomMode = random.nextInt(mode.length);
        String randomOption = mode[randomMode];        
        int randomTaille = random.nextInt(4);
        randomTaille = randomTaille * 2 + 2;

        memoryApp.loadGame("Duo",  "Joueur1Default", "Joueur2Default", false, randomOption ,1, randomTaille);
    }

    @FXML
    private void actionHistorique() {
        memoryApp.loadHistorique();
    }
}