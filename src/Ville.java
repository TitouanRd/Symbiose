public class Ville extends Construction {
    private static int nbVille = 0;

    public Ville(String type, int niveau, int cout, int productionEnergie,
                 int productionRess, int entretienEnergie, int entretienRess,
                 float impactQualite, float impactPollution) {

        super(type, niveau, cout, productionEnergie, productionRess,
                entretienEnergie, entretienRess, impactQualite, impactPollution);
        nbVille++;
    }

    public static void resetNbVille() {
        nbVille = 0;
    }

    // SOLUTION ERREUR 1 : Implémenter la méthode manquante de Construction
    @Override
    public Number[] BilanTour(Case c) {
        System.out.println("Bilan du tour pour la ville...");
        Number[] retours = new Number[2];
        retours[0] = - consommerRessources(c);
        retours[1] =  - consommerEnergie(c);
        return retours;
    }

    public float consommerEnergie(Case c) {
        // Logique
        float enrg_csm =0;
        if (c.getTypeTerrain() instanceof Plaine) {
            float qualite = c.getQualite();
            enrg_csm = qualite  * this.getNiveau() * ((Plaine)c.getTypeTerrain()).getRichesseSol();
        }
        return enrg_csm;
    }

    public float consommerRessources(Case c) {
        // Logique
        float res_csm =0;
        if (c.getTypeTerrain() instanceof Plaine) {
            float qualite = c.getQualite();
            float richesseSol = ((Plaine)c.getTypeTerrain()).getRichesseSol();
            res_csm = qualite * richesseSol  * this.getNiveau() ;
        }
        return res_csm;
    }

    public static int getNbVille() {
        return nbVille;
    }

    @Override
    public String toString() {
        return "ville de niveau "+this.getNiveau();
    }
}