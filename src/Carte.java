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


    public Carte(String tailleCarte,Partie partie) {
        // 1. Dépendance externe
        // Comme la signature ne prend plus "Partie", il faudra la lier via un setter
        // dans ta classe Partie juste après l'instanciation : carte.setPartie(this);
        this.partie = partie;

        // 2. Valeurs écologiques initiales (Une carte saine au tour 0)
        this.pollution = 0f;           // Aucune pollution au départ
        this.vie_sauvage = 100f;       // Qualité de l'environnement maximale
        this.foret = 70f;              // Couverture forestière généreuse par défaut
        this.temp = 15f;               // Température tempérée standard (15°C)
        this.presVile = false;         // Pas de ville posée (le tutoriel s'en chargera)

        // 3. Limites planétaires (Conditions de Game Over)
        // Fixées empiriquement pour offrir un bon défi sans être frustrantes
        this.limite_pollution = 80f;   // DÉFAITE si la pollution moyenne dépasse 80
        this.limite_vie_sauvage = 20f; // DÉFAITE si la qualité moyenne chute sous 20
        this.limite_foret = 15f;       // DÉFAITE si moins de 15% de forêts restantes
        this.limite_temp = 25f;        // DÉFAITE si la température moyenne s'envole (+10°C)

        // 4. Dimensions de la grille
        int lignes;
        int colones;

        switch (tailleCarte.toLowerCase()) {
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

        // 5. Initialisation des structures de données
        this.grille = new Case[colones][lignes];

        // Remplissage de la matrice et génération des biomes
        this.initialiserGrilleAleatoire(colones, lignes);

        // Détection des voisines pour le système de diffusion (Automate cellulaire)
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
                int poidsForet = 45;
                int poidsLac = 20;

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
    public Number[] fin_Tour() {
        System.err.println("Carte fin_Tour");
        Number[] retoursTotal = {0f, 0f, 0f, 0f};

        float p = 0, q = 0, f = 0, t = 0;
        int totalCases = this.grille.length * this.grille[0].length;

        for (Case[] ligne : this.grille) {
            for (Case c : ligne) {
                Number[] retours = c.fin_Tour();

                retoursTotal[0] = retoursTotal[0].floatValue() + retours[0].floatValue();
                retoursTotal[1] = retoursTotal[1].floatValue() + retours[1].floatValue();

                p += retours[2].floatValue();
                q += retours[3].floatValue();
                f += retours[4].floatValue();
                t += retours[5].floatValue();
            }
        }

        // Vérification directe des limites planétaires via les moyennes
        if ((p / totalCases) > this.limite_pollution ||
                (q / totalCases) < this.limite_vie_sauvage ||
                (f / totalCases) < this.limite_foret ||
                (t / totalCases) > this.limite_temp) {

            retoursTotal[2] = 1f;  // Signal de Game Over
        }

        // Calcul des moyennes
        float moyP = p / totalCases;
        float moyQ = q / totalCases;
        float moyF = f / totalCases;
        float moyT = t / totalCases;

        // AFFICHE LE DIAGNOSTIC DANS LA CONSOLE
        System.out.println("\n--- DIAGNOSTIC DES LIMITES PLANÉTAIRES ---");
        System.out.println("Pollution Moyenne : " + moyP + " / Limite max : " + this.limite_pollution);
        System.out.println("Qualité Moyenne   : " + moyQ + " / Limite min : " + this.limite_vie_sauvage);
        System.out.println("Taux de Forêt     : " + moyF + " / Limite min : " + this.limite_foret);
        System.out.println("Température       : " + moyT + " / Limite max : " + this.limite_temp);
        System.out.println("------------------------------------------\n");

        // Vérification directe des limites planétaires via les moyennes calculées
        if (moyP > this.limite_pollution || moyQ < this.limite_vie_sauvage || moyF < this.limite_foret || moyT > this.limite_temp) {
            retoursTotal[2] = 1f;  // Signal de Game Over
        }

        return retoursTotal;
    }

    public void show(Partie partie){
        for (Case[] x : this.grille) {
            for (Case c : x) {
                c.show(partie);
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
