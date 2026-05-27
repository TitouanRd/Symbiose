public class PanneauSolaire extends ProdEnergie{
    private float exposition;
    public PanneauSolaire(int niveau) {
        // Appel obligatoire à super() EN TOUT PREMIER avec des valeurs par défaut
        // Signature : niveau, cout, prodEnergie, prodRess, entretEnergie, entretRess, impQualite, impPollution, impVie, rendement
        super(niveau, 0, 0, 0, 0, 0, 0f, 0f, 0f, 0f);

        // Affectation des valeurs spécifiques selon le niveau
        switch (niveau) {
            case 1 -> {
                this.setCout(50);
                this.setProductionEnergie(15);
                this.setProductionRess(0);

                this.setEntretienEnergie(1);  // Onduleurs
                this.setEntretienRess(2);     // Nettoyage régulier

                this.setImpactQualite(3.0f);  // Emprise au sol
                this.setImpactPollution(0.1f);// Zéro émission
                this.setImpactVie(1.0f);      // Faible dérangement

                this.setRendement(15f);       // Rendement photovoltaïque standard
                this.exposition = 10f;
            }
            case 2 -> {
                this.setCout(120);
                this.setProductionEnergie(20);
                this.setProductionRess(0);

                this.setEntretienEnergie(2);
                this.setEntretienRess(5);

                this.setImpactQualite(5.0f);
                this.setImpactPollution(0.3f);
                this.setImpactVie(2.0f);

                this.setRendement(45f);       // Cellules haute efficacité
                this.exposition = 20f;
            }
            default -> {
                this.setCout(0);
                this.setProductionEnergie(0);
                this.setProductionRess(0);

                this.setEntretienEnergie(0);
                this.setEntretienRess(0);

                this.setImpactQualite(0f);
                this.setImpactPollution(0f);
                this.setImpactVie(0f);

                this.setRendement(0f);
                this.exposition = 0f;
            }
        }
    }
    public  float getExposition() {
        return exposition;
    }
    public void setExposition(float exposition) {
        this.exposition = exposition;
    }
    @Override
    public float consommerRessources(Case c) {
        // 1. Coût d'entretien nominal
        float res_csm = this.getEntretienRess();

        // 2. Facteur Qualité (Environnement hostile = usure des onduleurs et câbles)
        // Sécurité Math.max pour éviter la division par zéro
        float facteurQualite = 100f / Math.max(1f, c.getQualite());
        res_csm *= facteurQualite;

        // 3. Contraintes météorologiques
        if (c.getTypeTerrain() instanceof Plaine) {
            Plaine plaine = (Plaine) c.getTypeTerrain();

            // S'il fait très chaud, les onduleurs souffrent et demandent des pièces de rechange
            if (plaine.getTemperatureSol() > 40f) {
                res_csm *= 1.3f; // +30% de coût d'entretien
            }
        }

        return res_csm;
    }
    @Override
    public float produireEnergie(Case c) {
        float enrg_prod = 0;

        if (c.getTypeTerrain() instanceof Plaine) {
            Plaine plaine = (Plaine) c.getTypeTerrain();
            float ensoleillement = plaine.getEnseileillement();

            // 1. Facteur de Qualité (La poussière/pollution bloque la lumière)
            // Si qualite = 100, le facteur est 1.0. Si qualite = 50, rendement divisé par 2.
            float facteurQualite = Math.max(0f, c.getQualite()) / 100f;

            // 2. Impact Thermique (Réalité physique : les panneaux n'aiment pas la canicule)
            float facteurChaleur = 1.0f;
            if (plaine.getTemperatureSol() > 35f) {
                // Perte d'efficacité si la température du sol dépasse 35°C
                facteurChaleur = 0.8f;
            }

            // 3. Calcul de la puissance brute (Formule du MVP)
            // On croise l'ensoleillement de la carte avec l'exposition de l'installation et son rendement.
            // On divise par 100 pour garder des valeurs à échelle humaine (ex: 50 à 300 par tour)
            float productionBrute = (ensoleillement * this.exposition * this.getRendement()) / 100f;

            // 4. Résultat final
            enrg_prod = this.getProductionEnergie()+ productionBrute * facteurQualite * facteurChaleur;
        }
        return enrg_prod;
    }
    @Override
    public Number[] BilanTour(Case c) {
        // 1. Application des impacts environnementaux sur la case
        // On augmente la pollution et on diminue la qualité en fonction des stats du bâtiment
        float nouvellePollution = c.getPollution() + this.getImpactPollution();
        float nouvelleQualite = c.getQualite() - this.getImpactQualite();

        // Sécurité : on "clamp" (bloque) les valeurs entre 0 et 100 pour éviter les bugs
        c.setPollution(Math.min(100f, Math.max(0f, nouvellePollution)));
        c.setQualite(Math.min(100f, Math.max(0f, nouvelleQualite)));

        // 2. Bilan économique (Ressources et Énergie)
        Number[] retours = new Number[2];
        retours[0] = - consommerRessources(c); // Dépense (Négatif)
        retours[1] = produireEnergie(c);       // Gain (Positif)

        return retours;
    }

    @Override
    public String toString() {
        return "Panneau Sollaire de niveau "+this.getNiveau();
    }
}
