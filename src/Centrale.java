public class Centrale extends ProdEnergie{
    private String ressourcesUtiliser;
    private float charbon_dispo;
    public Centrale(int niveau) {
        // 1. Appel obligatoire à super() en TOUT PREMIER
        // Signature : niveau, cout, prodEnergie, prodRess, entretEnergie, entretRess, impQualite, impPollution, impVie, rendement
        super(niveau, 0, 0, 0, 0, 0, 0f, 0f, 0f, 0f);

        // 2. Affectation des valeurs spécifiques selon le niveau
        switch (niveau) {
            case 1 -> {
                this.setCout(150);
                this.setProductionEnergie(0);
                this.setProductionRess(0);

                this.setEntretienEnergie(5);
                this.setEntretienRess(15);

                this.setImpactQualite(5.0f);
                this.setImpactPollution(8.0f); // Très polluant !
                this.setImpactVie(4.0f);

                this.setRendement(40f);
                this.setRessourcesUtiliser("Charbon");

                // On remplit la soute de charbon (Durée de vie courte/moyenne)
                // this.setCarburantDispo(200f);
            }
            case 2 -> {
                this.setCout(400); // Investissement massif
                this.setProductionEnergie(0);
                this.setProductionRess(0);

                this.setEntretienEnergie(15);
                this.setEntretienRess(30); // Maintenance très coûteuse

                this.setImpactQualite(2.0f);
                this.setImpactPollution(0.5f); // Énergie décarbonée
                this.setImpactVie(8.0f);       // Impact thermique sur l'eau/faune

                this.setRendement(100f);       // Puissance monstrueuse
                this.setRessourcesUtiliser("Uranium");

                // Cœur nucléaire chargé (Durée de vie très longue)
                // this.setCarburantDispo(500f);
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
                this.setRessourcesUtiliser("Aucune");

                // this.setCarburantDispo(0f);
            }
        }
    }
    public String getRessourcesUtiliser() {
        return ressourcesUtiliser;
    }
    public void setRessourcesUtiliser(String ressourcesUtiliser) {
        this.ressourcesUtiliser = ressourcesUtiliser;
    }

    public float getCharbon_dispo() { return charbon_dispo; }

    public void setCharbon_dispo(float charbon_dispo) { this.charbon_dispo = charbon_dispo; }

    @Override
    public float consommerRessources(Case c) {
        // --- PARTIE 1 : Mécanique interne (Le Charbon) ---
        // La centrale brûle son stock à chaque tour (ex: 10 par tour pour niv 1, 20 pour niv 2)
        float charbonBrule = 10f * this.getNiveau();
        this.charbon_dispo -= charbonBrule;

        // On s'assure que le stock ne tombe pas dans les négatifs
        if (this.charbon_dispo < 0) {
            this.charbon_dispo = 0;
        }


        // --- PARTIE 2 : Coût d'entretien pour le joueur ---
        // On récupère le coût nominal en ressources (ex: 5 ou 20)
        float res_csm = this.getEntretienRess();

        // Facteur Qualité : l'usure mécanique à cause de l'environnement toxique/pollué
        float facteurQualite = 100f / Math.max(1f, c.getQualite());
        res_csm *= facteurQualite;

        // Retourne le coût à déduire du stock global du joueur
        return res_csm;
    }
    @Override
    public float produireEnergie(Case c) {
        float enrg_prod = 0;

        // 1. La centrale ne tourne que si elle a du carburant !
        if (this.charbon_dispo > 0) {

            // 2. Production de base forte (basée sur le rendement : ex 20 ou 60)
            // On multiplie par un facteur fixe (ex: 5) pour avoir de grosses valeurs (100 à 300)
            float productionBrute = this.getRendement() * 5f;

            // 3. Facteur Qualité (l'encrassement des turbines)
            // 100 de qualité = 100% de production. 50 de qualité = 50% de prod.
            float facteurQualite = Math.max(0f, c.getQualite()) / 100f;

            // 4. Bonus de richesse du sol (Spécifique à la Plaine)
            float facteurRichesse = 1.0f; // Facteur par défaut (100%)
            if (c.getTypeTerrain() instanceof Plaine) {
                Plaine plaine = (Plaine) c.getTypeTerrain();
                // Si la plaine a 50 de richesse, le facteur passe à 1.5 (+50% de bonus)
                facteurRichesse = 1.0f + (Math.max(0f, plaine.getRichesseSol()) / 100f);
            }

            // Calcul final
            enrg_prod = productionBrute * facteurQualite * facteurRichesse;
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
        return "central de niveau "+this.getNiveau();
    }

}
