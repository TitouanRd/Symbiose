public class Ville extends Construction {
    private static int nbVille = 0;

    public Ville(String type, int niveau, int cout, int productionEnergie,
                 int productionRess, int entretienEnergie, int entretienRess,
                 float impactQualite, float impactPollution) {

        super(type, niveau, cout, productionEnergie, productionRess,
                entretienEnergie, entretienRess, impactQualite, impactPollution);
        nbVille++;
    }

    // SOLUTION ERREUR 1 : Implémenter la méthode manquante de Construction
    @Override
    public void BilanTour(Case c,  Partie p) {
        System.out.println("Bilan du tour pour la ville...");
        // Logique du bilan
        consommerEnergie(c,p);
        consommerRessources(c,p);
    }

    public void consommerEnergie(Case c, Partie p) {
        // Logique
        if (c instanceof Plaine) {
            Plaine maPlaine = (Plaine) c;
            float qualite = maPlaine.getQualite();
            float enrg_csm = qualite  * this.getNiveau() * maPlaine.getRichesseSol();
            p.setProduction_energie(p.getProduction_energie() - enrg_csm);
        }
    }

    public void consommerRessources(Case c, Partie p) {
        // Logique
        if (c instanceof Plaine) {
            Plaine maPlaine = (Plaine) c;
            float qualite = maPlaine.getQualite();
            float richesseSol = maPlaine.getRichesseSol();
            float res_csm = qualite * richesseSol  * this.getNiveau() ;
            p.setRessources(p.getRessources() - Math.round(res_csm));
        }
    }

    public static int getNbVille() {
        return nbVille;
    }
}