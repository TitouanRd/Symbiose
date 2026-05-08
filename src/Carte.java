import java.util.ArrayList;
import java.util.Random;

public class Carte {
    private float temp;
    private float limite_temp;
    private float vie_sauvage;
    private float limite_vie_sauvage;
    private float foret;
    private float limite_foret;
    private float pollution;
    private float limite_pollution;
    private boolean presVile;
    private Case[][] grille;
    private Partie partie;


    public Carte(String tailleCarte, float foret, float limite_foret, float limite_pollution, float limite_temp, float limite_vie_sauvage, float pollution, boolean presVile, float temp, float vie_sauvage, Partie partie) {
        this.partie = partie;
        this.foret = foret;
        this.limite_foret = limite_foret;
        this.limite_pollution = limite_pollution;
        this.limite_temp = limite_temp;
        this.limite_vie_sauvage = limite_vie_sauvage;
        this.pollution = pollution;
        this.presVile = presVile;
        this.temp = temp;
        this.vie_sauvage = vie_sauvage;
        int lignes;
        int colones;
        switch (tailleCarte) {// ici les taille c pour mon ordi 
            case "petite" -> {
                lignes = 15;
                colones = 10;
            }
            case "moyenne" -> {
                lignes = 25;
                colones = 12;
            }
            case "grande" -> {
                lignes = 40;
                colones = 18;
            }
            default -> {
                lignes = 20;
                colones = 20;
            }
        }

        // Initialisation de la grille
        this.grille = new Case[colones][lignes];
        this.initialiserGrilleAleatoire(colones, lignes);
        this.detectionCasesVoisines();
    }
    

    private void initialiserGrilleAleatoire(int largeur, int hauteur) {
        Random random = new Random();
        this.grille = new Case[largeur][hauteur];
        
        // Une météo par défaut pour commencer
        Meteo meteoParDefaut = new Meteo("Clair", 10f, 5f, 50f, 100f, 0f);

        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {

                // 1. Poids de base (Probabilités de départ)
                int poidsPlaine = 100; // Très dominant par défaut
                int poidsForet = 15;
                int poidsLac = 10;

                // 2. Bonus de voisinage (Haut et Gauche)
                // On regarde la case à gauche
                if (x > 0) {
                    if (grille[x-1][y].getTypeTerrain() instanceof Foret) poidsForet += 80;
                    if (grille[x-1][y].getTypeTerrain() instanceof Lac) poidsLac += 80;
                }
                // On regarde la case en haut
                if (y > 0) {
                    if (grille[x][y-1].getTypeTerrain() instanceof Foret) poidsForet += 80;
                    if (grille[x][y-1].getTypeTerrain() instanceof Lac) poidsLac += 80;
                }

                // 3. Tirage aléatoire pondéré
                int totalPoids = poidsPlaine + poidsForet + poidsLac;
                int tirage = random.nextInt(totalPoids);
                grille[x][y] = new Case(0f,100f,"Saine",this,null);
                TypeTerrain typeTerrain;
                if (tirage < poidsPlaine) {
                    // Création d'une Plaine
                    typeTerrain = new Plaine(10f, 50f, 15f, 80f,grille[x][y] );
                } else if (tirage < poidsPlaine + poidsForet) {
                    // Création d'une Forêt
                    typeTerrain = new Foret(70f,grille[x][y]);
                } else {
                    // Création d'un Lac
                    typeTerrain = new Lac( 15f, 10f,grille[x][y]);
                }

                grille[x][y].setTypeTerrain(typeTerrain);

                // On assigne la météo
                grille[x][y].setMeteo(meteoParDefaut);
            }
        }
    }   

    public  void detectionCasesVoisines() {
        for (int i = 0; i < this.grille.length; i++){
            for (int j = 0; j < this.grille[i].length; j++){
                ArrayList<Case> listeTemporaire = new ArrayList<>();

                int[][] casVois =  {  
                {i-1,j}, {i,j+1}, {i+1,j+1},{i+1,j}, {i+1,j-1}, {i,j-1}
                };
                for (int[] c : casVois) {
                    int vx = c[0];
                    int vy = c[1];
                    if (vx >= 0 && vx < grille.length && vy >= 0 && vy < grille[0].length) {
                        listeTemporaire.add(this.grille[vx][vy]);
                    }
                }
                this.grille[i][j].setVoisines(listeTemporaire.toArray(new Case[6]));
            }
        }
    }

    public float getTemp() {
        return temp;
    }

    public float getLimite_temp() {
        return limite_temp;
    }

    public float getVie_sauvage() {
        return vie_sauvage;
    }

    public float getLimite_vie_sauvage() {
        return limite_vie_sauvage;
    }

    public float getForet() {
        return foret;
    }

    public float getLimite_foret() {
        return limite_foret;
    }

    public float getPollution() {
        return pollution;
    }

    public float getLimite_pollution() {
        return limite_pollution;
    }

    public boolean isPresVile() {
        return presVile;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setLimite_temp(float limite_temp) {
        this.limite_temp = limite_temp;
    }

    public void setVie_sauvage(float vie_sauvage) {
        this.vie_sauvage = vie_sauvage;
    }

    public void setLimite_vie_sauvage(float limite_vie_sauvage) {
        this.limite_vie_sauvage = limite_vie_sauvage;
    }

    public void setForet(float foret) {
        this.foret = foret;
    }

    public void setLimite_foret(float limite_foret) {
        this.limite_foret = limite_foret;
    }

    public void setPollution(float pollution) {
        this.pollution = pollution;
    }

    public void setLimite_pollution(float limite_pollution) {
        this.limite_pollution = limite_pollution;
    }

    public void setPresVile() {
        this.presVile = true;
    }
    public void fin_Tour() {
        System.err.println("Carte fin_Tour");
        for (Case[] x : this.grille) {
            for (Case c : x) {
                c.fin_Tour();
                c.show();
            }
        }
    }

    public void show(){
        for (Case[] x : this.grille) {
            for (Case c : x) {
                c.show();
            }
        }
    }
    public Case[][] getGrille() {
        return grille;
    }

    public void setGrille(Case[][] grille) {
        this.grille = grille;
    }

    public Partie getPartie() {
        return partie;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Carte{");
        sb.append("grille=");
        for (Case[] x : this.grille) {
            sb.append("\n[");
            for (Case c : x) {
                sb.append(c);
            }
            sb.append("]");
        }
        sb.append('}');
        return sb.toString();
    }
}
