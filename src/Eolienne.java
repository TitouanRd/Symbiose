public class Eolienne extends ProdEnergie{
    private float hauteur;
    public Eolienne(int niveau) {
        // 1. Appel obligatoire à super() en TOUT PREMIER avec des valeurs par défaut
        // Signature de ProdEnergie : niveau, cout, prodEnergie, prodRess, entretEnergie, entretRess, impQualite, impPollution, impVie, rendement
        super(niveau, 0, 0, 0, 0, 0, 0f, 0f, 0f, 0f);

        // 2. Affectation des valeurs spécifiques selon le niveau
        switch (niveau) {
            case 1 -> {
                this.setCout(10);
                this.setProductionEnergie(0); // À modifier plus tard quand l'éolienne produira effectivement
                this.setProductionRess(0);
                this.setEntretienEnergie(10);
                this.setEntretienRess(5);
                this.setImpactQualite(10f);
                this.setImpactPollution(10f);
                this.setImpactVie(5f);
                this.setRendement(20f);
                this.setHauteur(10f); // Attribut propre à l'Éolienne
            }
            case 2 -> {
                this.setCout(20);
                this.setProductionEnergie(0);
                this.setProductionRess(0);
                this.setEntretienEnergie(40);
                this.setEntretienRess(20);
                this.setImpactQualite(15f);
                this.setImpactPollution(20f);
                this.setImpactVie(10f);
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
    public float consommerRessources( Case c){
        // On vérifie que l'eolienne est bien sur une Plaine pour avoir accès à la vitesse du vent
        float res_csm = 0;
        if (c.getTypeTerrain() instanceof Plaine) {
            float qualite = c.getQualite();
            float vitesseVent = ((Plaine)c.getTypeTerrain()).getVitesseVent();
            res_csm = qualite * vitesseVent * this.hauteur * this.getNiveau() * this.getRendement();
        }
        if (c.getTypeTerrain() instanceof Lac) {
            float qualite = c.getQualite();
            float vitesseVent = ((Lac)c.getTypeTerrain()).getVitesseVent();
            res_csm = qualite * vitesseVent * this.hauteur * this.getNiveau() * this.getRendement();
        }
        return res_csm;
    }
    @Override
    public float produireEnergie( Case c) {
        // On vérifie que l'eolienne est bien sur une Plaine pour avoir accès à la vitesse du vent
        float enrg_prod = 0;
        if (c.getTypeTerrain() instanceof Plaine) {
            float qualite = c.getQualite();
            float vitesseVent = ((Plaine)c.getTypeTerrain()).getVitesseVent();
            enrg_prod = qualite * vitesseVent * this.hauteur * this.getNiveau() * this.getRendement();

        }
        if (c.getTypeTerrain() instanceof Lac) {
            float qualite = c.getQualite();
            float vitesseVent = ((Plaine)c.getTypeTerrain()).getVitesseVent();
            enrg_prod = qualite * vitesseVent * this.hauteur * this.getNiveau() * this.getRendement();

        }
        return enrg_prod;
    }
    @Override
    public Number[] BilanTour(Case c) {
        Number[] retours = new Number[2];
        retours[0] = - consommerRessources(c);
        retours[1] =  produireEnergie(c);
        return retours;
    }

    @Override
    public String toString() {
        return "éolienne de niveau "+this.getNiveau();
    }
}
