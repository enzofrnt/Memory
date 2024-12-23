package memory.om;

import java.io.Serializable;

public class Partie implements Serializable{
    private String modeJeux;
    private String nom1;
    private String nom2;
    private boolean triche;
    private String theme;
    private int difficulteTemps;
    private int taille;
    private int nbCoups1;
    private int nbCoups2;
    private int gagnant1;
    private int gagnant2;
    private int temps;
    private int joueurActuel;
    
    public Partie(String pfModeJeux ,String pfNom1, String pfNom2, boolean pfTriche, String pfTheme,int pfDifficulteTemps, int pfTaille, int pfnNbCoups1, int pfnNbCoups2, int pfGagnant1, int pfGagnant2, int temps) {
        this.modeJeux = pfModeJeux;
        this.nom1 = pfNom1;
        this.nom2 = pfNom2;
        this.triche = pfTriche;
        this.theme = pfTheme;
        this.difficulteTemps = pfDifficulteTemps;
        this.taille = pfTaille;
        this.nbCoups1 = pfnNbCoups1;
        if(pfModeJeux.equals("Duo")) {
            this.nbCoups2 = pfnNbCoups2;
            this.gagnant1 = pfGagnant1;
            this.gagnant2 = pfGagnant2;
            this.joueurActuel = 1;
        }
        this.temps = temps;


    }

    public String getModeJeux() {   return modeJeux;    }
    public String getNom1() {    return nom1;  }
    public String getNom2() {    return nom2;  }
    public boolean getTriche() {    return triche;  }
    public String getTheme() {    return theme;  }
    public int getDifficulteTemps() {    return difficulteTemps;  }
    public int getTaille() {    return taille;  }
    public int getNbCoups1() {    return nbCoups1;  }
    public int getNbCoups2() {    return nbCoups2;  }
    public int getGagnant1() {    return gagnant1;  }
    public int getGagnant2() {    return gagnant2;  }
    public int getJoueurActuel() {    return joueurActuel;  }
    public int getTemps() {    return temps;  }

    public void setModeJeux(String modeJeux) {    this.modeJeux = modeJeux;  }
    public void setNom1(String pfNom1) {    this.nom1 = pfNom1;  }
    public void setNom2(String pfNom2) {    this.nom2 = pfNom2;  }
    public void setGagnant1(int pfGagnant1) {    this.gagnant1 = pfGagnant1;  }
    public void setGagnant2(int pfGagnant2) {    this.gagnant2 = pfGagnant2;  }
    public void setTriche(boolean pfTriche) {    this.triche = pfTriche;  }
    public void setTheme(String pfTheme) {    this.theme = pfTheme;  }
    public void setDifficulteTemps(int pfDifficulteTemps) {    difficulteTemps = pfDifficulteTemps;  }
    public void setTaille(int pfTaille) {    this.taille = pfTaille;  }
    public void setNbCoups1(int pfNbCoups) {    this.nbCoups1 = pfNbCoups;  }
    public void setNbCoups2(int pfNbCoups) {    this.nbCoups2 = pfNbCoups;  }
    public void setJoueurActuel(int pfJouerActuel) {    this.joueurActuel = pfJouerActuel;  }
    public void setTemps(int pfTemps) {    this.temps = pfTemps;  }

    @Override
    public String toString() {
        if(modeJeux.equals("Solo")) {
            return 
            "------------------------------------------------------------\n"
            + "Partie Solo de " + nom1 + " :\n" 
            + "     | Triche utilisé : " + (triche ? "oui" : "non") + "\n"
            + "     | Theme utilisé : " + theme + "\n"
            + "     | Temps d'afficage des cartes : " + difficulteTemps  + "\n"
            + "     | Taille de la grille utilisé : " + taille  + "\n"
            + "     | Nombre de coups réalisé lors de la partie : " + nbCoups1 + "\n" 
            + "     | Temps de jeu de la partie " + temps + "\n"
            + "------------------------------------------------------------\n";
        }else if(modeJeux.equals("Duo")) { 
            return 
            "------------------------------------------------------------\n"
            + " Partie Duo de " + nom1 + " et " + nom2 + " :\n"
            + "     | Triche utilisé : " + (triche ? "oui" : "non") + "\n"
            + "     | Theme utilisé : " + theme + "\n"
            + "     | Temps d'afficage des cartes : " + difficulteTemps  + "\n"
            + "     | Taille de la grille utilisé : " + taille  + "\n"
            + "     | Nombre de coups réalisé par " + nom1 + " : " + nbCoups1 + "\n"
            + "     | Nombre de coups réalisé par " + nom2 + " : " + nbCoups2 + "\n"
            + "     | Nombre de coups gagnant réalisé par " + nom1 + " : " + gagnant1 + "\n"
            + "     | Nombre de coups gagnant réalisé par " + nom2 + " : " + gagnant2 + "\n"
            + "     | Gagnant de la partie : " + (gagnant1 > gagnant2 ? nom1 : nom2) + "\n"
            + "     | Temps de jeu de la partie " + temps + "\n"
            + "------------------------------------------------------------\n";
        }
        return "pas de parti pour le moment";
    }
}
    
