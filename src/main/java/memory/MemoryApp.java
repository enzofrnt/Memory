package memory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import memory.om.Jeu;
import memory.om.Partie;
import memory.view.AcceuilController;
import memory.view.ListePartieController;
import memory.view.PartieController;


import java.awt.Taskbar;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


public class MemoryApp extends Application {

	private AnchorPane root;
	private Stage primaryStage;
	private Thread tache;
	private final IntegerProperty tempsJeu = new SimpleIntegerProperty(0);
	private Jeu jeuActuel;
	private Partie partiActuel;
	private ObservableList<Partie> listePartie = FXCollections.observableArrayList();


	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.root = new AnchorPane();
		
		primaryStage.getIcons().add(new Image(MemoryApp.class.getResourceAsStream("icon.png")));
		//si MACOS est utilisé chagement d'icon pour la barre des taches
		Taskbar taskbar = Taskbar.getTaskbar();
        try {
            java.awt.Image dockIcon = SwingFXUtils.fromFXImage(new Image(getClass().getResourceAsStream("icon.png")), null);
            taskbar.setIconImage(dockIcon);
        } catch (IllegalArgumentException | UnsupportedOperationException e) {//le catch ne retourne rien pour en pas perturber l'execution du programme sur les autres OS}
		}

		Scene scene = new Scene(root);
		scene.getStylesheets().add(MemoryApp.class.getResource("style.css").toExternalForm());
		primaryStage.setTitle("Memory Game");
		primaryStage.setResizable(false);
		primaryStage.setMinWidth(750);
		primaryStage.setMinHeight(650);
		primaryStage.setScene(scene);

		loadAccueil();
		primaryStage.show();
	}
	
	public void loadAccueil() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MemoryApp.class.getResource("view/Acceuil.fxml"));

			BorderPane accueil = (BorderPane) loader.load();

			AcceuilController ctrl= loader.getController();
			
			ctrl.setMemoryApp(this);
			ctrl.setPrimaryStage(primaryStage);

			this.root.getChildren().add(accueil);

			AnchorPane.setTopAnchor(accueil, 0.0);
			AnchorPane.setRightAnchor(accueil, 0.0);
			AnchorPane.setBottomAnchor(accueil, 0.0);
			AnchorPane.setLeftAnchor(accueil, 0.0);
		} catch (IOException e) {
			System.exit(1);
		}
	}

	

	public static void main2(String[] args) {
		Application.launch(args);
	}

	public void loadGame(String pfModeJeux, String pfNomJoueur1, String pfNomJoeur2, boolean pfTriche, String pfTheme, int pfTemps, int pfTaille ) {
		jeuActuel = new Jeu(pfTaille*pfTaille/2, pfTriche);
		partiActuel = new Partie(pfModeJeux, pfNomJoueur1, pfNomJoeur2, pfTriche, pfTheme, pfTemps, pfTaille, 0, 0, 0, 0, 0);
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MemoryApp.class.getResource("view/Partie.fxml"));

			BorderPane jeu = loader.load();

			PartieController ctrl = loader.getController();

			ctrl.setMemoryApp(this);
			ctrl.setPrimaryStage(primaryStage);
			ctrl.setJeu(jeuActuel);
			ctrl.setPartiActuel(partiActuel);
			ctrl.setGridPaneTaille(pfModeJeux, pfTaille, pfTemps, pfTheme);

			this.root.getChildren().add(jeu);

			AnchorPane.setTopAnchor(jeu, 0.0);
			AnchorPane.setRightAnchor(jeu, 0.0);
			AnchorPane.setBottomAnchor(jeu, 0.0);
			AnchorPane.setLeftAnchor(jeu, 0.0);
		} catch (Exception e) {
			System.exit(1);
		}
		
	}

	public void loadHistorique() {
		try {
			this.listePartie.clear();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MemoryApp.class.getResource("view/ListePartie.fxml"));

			BorderPane ListePartie = (BorderPane) loader.load();

			ListePartieController ctrl= loader.getController();
			
			ctrl.setMemoryApp(this);
			ctrl.setPrimaryStage(primaryStage);
			ctrl.setListePartie(listePartie);

			loadParties();

			this.root.getChildren().add(ListePartie);

			AnchorPane.setTopAnchor(ListePartie, 0.0);
			AnchorPane.setRightAnchor(ListePartie, 0.0);
			AnchorPane.setBottomAnchor(ListePartie, 0.0);
			AnchorPane.setLeftAnchor(ListePartie, 0.0);
		} catch (IOException e) {
			System.exit(1);
		}
	}

	public void savePartie() {
		listePartie.add(partiActuel);
		saveParties();
	}

	public void saveParties() {
        try {
			FileOutputStream fos = new FileOutputStream("main.sav");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeInt(listePartie.size());
			for (Partie partie : listePartie) {
                oos.writeObject(partie);
            }
			oos.close();
			fos.close();
		} catch (IOException e) {
		}
    }

    public void loadParties() {
        try {
            ObjectInputStream flux = new ObjectInputStream(new FileInputStream("main.sav"));
            if (!(flux == null)) {
                int nbPartie = flux.readInt();
                for (int i = 0; i < nbPartie; i++) {
                    Partie membre = (Partie) flux.readObject();
				    listePartie.add(membre);
                }
                flux.close();
            }
        } catch (IOException | ClassNotFoundException e) {
        }
    }
}
