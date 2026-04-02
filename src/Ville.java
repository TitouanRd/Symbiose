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
    public void BilanTour() {
        System.out.println("Bilan du tour pour la ville...");
        // Logique du bilan
    }

    public void consommerEnergie() {
        // Logique
    }

    public void consommerRessources() {
        // Logique
    }

    public static int getNbVille() {
        return nbVille;
    }
}