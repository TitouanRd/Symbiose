public class Ville extends Construction {
    private static int nbVille = 0;

    public Ville(int niveau) {
        // Appel obligatoire à super() EN TOUT PREMIER avec des valeurs par défaut
        super(niveau, 0, 0, 0, 0, 0, 0f, 0f);

        // Affectation des valeurs équilibrées
        switch (niveau) {
            case 1 -> {
                this.setCout(100);
                this.setProductionEnergie(0);
                this.setProductionRess(0);
                this.setEntretienEnergie(20);
                this.setEntretienRess(10);
                this.setImpactQualite(1.0f);
                this.setImpactPollution(2.0f);
            }
            case 2 -> {
                this.setCout(300);
                this.setProductionEnergie(0);
                this.setProductionRess(0);
                this.setEntretienEnergie(50);
                this.setEntretienRess(25);
                this.setImpactQualite(3.0f);
                this.setImpactPollution(5.0f);
            }
            default -> {
                this.setCout(0);
                this.setProductionEnergie(0);
                this.setProductionRess(0);
                this.setEntretienEnergie(0);
                this.setEntretienRess(0);
                this.setImpactQualite(0f);
                this.setImpactPollution(0f);
            }
        }
        nbVille++;
    }

    public static void resetNbVille() {
        nbVille = 0;
    }

    // SOLUTION ERREUR 1 : Implémenter la méthode manquante de Construction
    @Override
    public Number[] BilanTour(Case c) {
        // 1. Impact environnemental (La ville génère des déchets et de la pollution)
        float nouvellePollution = c.getPollution() + this.getImpactPollution();
        float nouvelleQualite = c.getQualite() - this.getImpactQualite();

        c.setPollution(Math.min(100f, Math.max(0f, nouvellePollution)));
        c.setQualite(Math.min(100f, Math.max(0f, nouvelleQualite)));

        // 2. Bilan économique
        Number[] retours = new Number[2];
        retours[0] = - consommerRessources(c); // Dépense (Négatif)
        retours[1] = - consommerEnergie(c);    // Dépense (Négatif)

        return retours;
    }

    public float consommerEnergie(Case c) {
        // 1. On récupère le coût nominal d'énergie
        float enrg_csm = this.getEntretienEnergie();

        // 2. Facteur Qualité : un environnement pollué coûte plus cher à maintenir
        // Math.max(1f, ...) empêche la division par zéro en cas de catastrophe écologique
        float facteurQualite = 100f / Math.max(1f, c.getQualite());
        enrg_csm *= facteurQualite;

        // 3. Impact météo (si la ville est sur une plaine)
        if (c.getTypeTerrain() instanceof Plaine) {
            Plaine plaine = (Plaine) c.getTypeTerrain();
            // S'il fait très froid ou très chaud, la ville consomme plus (chauffage/clim)
            if (plaine.getTemperatureSol() < 5f || plaine.getTemperatureSol() > 30f) {
                enrg_csm *= 1.2f; // +20% de consommation électrique
            }
        }

        return enrg_csm;
    }

    public float consommerRessources(Case c) {
        // 1. On récupère le coût nominal en ressources
        float res_csm = this.getEntretienRess();

        // 2. Même logique : la population consomme plus de ressources médicales/importées
        // si l'environnement direct est dégradé.
        float facteurQualite = 100f / Math.max(1f, c.getQualite());

        return res_csm * facteurQualite;
    }

    public static int getNbVille() {
        return nbVille;
    }

    @Override
    public String toString() {
        return "ville de niveau "+this.getNiveau();
    }

    public void monterNiveau() {
        this.setNiveau(this.getNiveau()+1);
        int cout;
        int productionEnergie;
        int productionRess;
        int entretienEnergie;
        int entretienRess;
        float impactQualite;
        float impactPollution;
        switch(this.getNiveau()){
            case 1:
                cout = 10;
                productionEnergie = 0;
                productionRess = 0;
                entretienEnergie = 10;
                entretienRess = 5;
                impactQualite = 10;
                impactPollution = 10;
                break;
            case 2:
                cout = 20;
                productionEnergie = 0;
                productionRess = 0;
                entretienEnergie = 40;
                entretienRess = 20;
                impactQualite = 15;
                impactPollution = 20;
            default:
                cout = 0;
                productionEnergie = 0;
                productionRess = 0;
                entretienEnergie = 0;
                entretienRess = 0;
                impactQualite = 0;
                impactPollution = 0;
        }

        this.setCout(cout);
        this.setProductionEnergie(productionEnergie);
        this.setProductionRess(productionRess);
        this.setEntretienEnergie(entretienEnergie);
        this.setEntretienRess(entretienRess);
        this.setImpactQualite(impactQualite);
        this.setImpactPollution(impactPollution);
    }
}