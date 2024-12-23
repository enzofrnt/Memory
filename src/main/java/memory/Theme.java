package memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import memory.om.Jeu;

public class Theme {

    private ArrayList<String> theme;

    public Theme(String pfNomTheme, int pfTaille){
        Random random = new Random();
        int i = pfTaille*pfTaille/2;

        if (pfNomTheme.equals("Chiffre")) {
            this.theme = new ArrayList<String>();
            for(int j = 0; j < i; j++){
                this.theme.add(""+j);
            }
        } else if (pfNomTheme.equals("Couleur")) {
            this.theme = new ArrayList<String>();
            int j = 0;
            while (j < i) {
                Color randomColor = Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
                String colorString = String.format("#%02X%02X%02X",
                        (int) (randomColor.getRed() * 255),
                        (int) (randomColor.getGreen() * 255),
                        (int) (randomColor.getBlue() * 255));

                if (!this.theme.contains(colorString) && couleurDifferente(randomColor, this.theme)) {
                    this.theme.add(colorString);
                    j++;
                }
            }
        } else if (pfNomTheme.equals("Lettre")) {
            String alphabet = "AÂÀÄBCDEÉÈÊFGHIJKLMNOPQRSTUVWXYZ";

            List<Character> caracteres = new ArrayList<>();
            for (char c : alphabet.toCharArray()) {
                caracteres.add(c);
            }
            Collections.shuffle(caracteres);
            StringBuilder resultat = new StringBuilder(caracteres.size());
            for (char c : caracteres) {
                resultat.append(c);
            }
            alphabet = resultat.toString();
    
            
            this.theme = new ArrayList<String>();
            for(int j = 0; j < i; j++){
                this.theme.add(""+alphabet.charAt(j));
            }
        }
    }

    public String getTheme(int pfIndex){    return this.theme.get(pfIndex); }

    public ArrayList<String> getTheme(){    return this.theme; }

    public static void actionTheme(String pfNomTheme, ArrayList<String> pfTheme, Button pfButton, Jeu pfJeuActuel, int pfTaille) {
        if (pfNomTheme.equals("Chiffre")) {
            pfButton.setText(pfTheme.get(pfJeuActuel.getCarteValeur(pfTaille*GridPane.getRowIndex(pfButton)+GridPane.getColumnIndex(pfButton))));
        } else if (pfNomTheme.equals("Couleur")) {
            pfButton.setStyle("-fx-background-color: " + pfTheme.get(pfJeuActuel.getCarteValeur(pfTaille*GridPane.getRowIndex(pfButton)+GridPane.getColumnIndex(pfButton))) + ";");
        } else if (pfNomTheme.equals("Lettre")) {
            pfButton.setText(pfTheme.get(pfJeuActuel.getCarteValeur(pfTaille*GridPane.getRowIndex(pfButton)+GridPane.getColumnIndex(pfButton))));
        }
    }

    private boolean couleurDifferente(Color color, List<String> theme) {
        double minDistanceSquared = 7000; // La distance minimale entre les couleurs
        for (String colorString : theme) {
            Color existingColor = Color.web(colorString);
            double rDiff = color.getRed() - existingColor.getRed();
            double gDiff = color.getGreen() - existingColor.getGreen();
            double bDiff = color.getBlue() - existingColor.getBlue();

            double distanceSquared = (rDiff * rDiff + gDiff * gDiff + bDiff * bDiff) * 255 * 255;

            if (distanceSquared < minDistanceSquared) {
                return false;
            }
        }
        return true;
    }
}
