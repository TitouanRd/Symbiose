public class Eolienne extends ProdEnergie{
    private float hauteur;
    public Eolienne(int niveau) {
        // Appel obligatoire à super() en TOUT PREMIER avec des valeurs par défaut
        super(niveau, 0, 0, 0, 0, 0, 0f, 0f, 0f, 0f);

        // Affectation des valeurs spécifiques équilibrées
        switch (niveau) {
            case 1 -> {
                this.setCout(60);
                this.setProductionEnergie(0); // Calculé dynamiquement via le vent
                this.setProductionRess(0);

                this.setEntretienEnergie(1);  // Électronique de contrôle
                this.setEntretienRess(3);     // Graissage, maintenance mécanique

                this.setImpactQualite(2.0f);  // Nuisance visuelle/sonore modérée
                this.setImpactPollution(0.2f);// Énergie propre
                this.setImpactVie(3.0f);      // Risque aviaire

                this.setRendement(20f);
                this.setHauteur(10f);
            }
            case 2 -> {
                this.setCout(150);
                this.setProductionEnergie(0);
                this.setProductionRess(0);

                this.setEntretienEnergie(3);
                this.setEntretienRess(8);

                this.setImpactQualite(4.0f);
                this.setImpactPollution(0.5f);
                this.setImpactVie(6.0f);

                this.setRendement(60f);
                this.setHauteur(20f);
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
                this.setHauteur(0f);
            }
        }
    }
    public float getHauteur() {
        return hauteur;
    }
    public void setHauteur(float hauteur) {
        this.hauteur = hauteur;
    }
    @Override
    public float consommerRessources(Case c) {
        float res_csm = this.getEntretienRess();

        // Encrassement général
        float facteurQualite = 100f / Math.max(1f, c.getQualite());
        res_csm *= facteurQualite;

        // Usure mécanique
        float vitesseVent = 0;
        if (c.getTypeTerrain() instanceof Plaine) vitesseVent = ((Plaine) c.getTypeTerrain()).getVitesseVent();
        else if (c.getTypeTerrain() instanceof Lac) vitesseVent = ((Lac) c.getTypeTerrain()).getVitesseVent();

        if (vitesseVent > 90f) {
            res_csm *= 1.5f; // Tempête = forte usure mécanique (+50%)
        } else if (vitesseVent < 15f) {
            res_csm *= 0.8f; // Pas de rotation = usure minimale
        }

        return res_csm;
    }
    @Override
    public float produireEnergie(Case c) {
        float enrg_prod = 0;
        float vitesseVent = 0;

        // Récupération du vent
        if (c.getTypeTerrain() instanceof Plaine) {
            vitesseVent = ((Plaine) c.getTypeTerrain()).getVitesseVent();
        } else if (c.getTypeTerrain() instanceof Lac) {
            vitesseVent = ((Lac) c.getTypeTerrain()).getVitesseVent();
        } else {
            return 0; // Pas de vent exploitable en Forêt
        }

        // Le rotor ne tourne qu'entre 15 et 90 de vent
        if (vitesseVent >= 15f && vitesseVent <= 90f) {
            float facteurQualite = Math.max(0f, c.getQualite()) / 100f;

            // On multiplie le vent par la hauteur du mât et le rendement de la génératrice
            float productionBrute = (vitesseVent * this.getHauteur() * this.getRendement()) / 100f;

            enrg_prod = productionBrute * facteurQualite;
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
        return "éolienne de niveau "+this.getNiveau();
    }
}
