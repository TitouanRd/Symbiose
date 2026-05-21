public class PanneauSollaire extends ProdEnergie{
    private float exposition;
    public PanneauSollaire(int niveau) {
        // 1. Appel obligatoire à super() en TOUT PREMIER avec des valeurs par défaut
        // Signature de ProdEnergie : niveau, cout, prodEnergie, prodRess, entretEnergie, entretRess, impQualite, impPollution, impVie, rendement
        super(niveau, 0, 0, 0, 0, 0, 0f, 0f, 0f, 0f);

        // 2. Affectation des valeurs spécifiques selon le niveau
        switch (niveau) {
            case 1 -> {
                this.setCout(10);
                this.setProductionEnergie(0);
                this.setProductionRess(0);
                this.setEntretienEnergie(10);
                this.setEntretienRess(5);
                this.setImpactQualite(10f);
                this.setImpactPollution(10f);
                this.setImpactVie(5f);
                this.setRendement(20f);
                this.exposition = 10f; // Attribut propre au panneau
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
    public float consommerRessources( Case c){
        // On vérifie que le panneau sollaire est bien sur une Plaine pour avoir accès à l'ensoleillement
        float res_csm =0;
        if (c.getTypeTerrain() instanceof Plaine) {
            float qualite = c.getQualite();
            float enseileillement = ((Plaine)c.getTypeTerrain()).getEnseileillement();
            res_csm = qualite * enseileillement * this.exposition * this.getNiveau() * this.getRendement();
        }
        return res_csm;
    }
    @Override
    public float produireEnergie( Case c) {
        // On vérifie que le panneau solaire est bien sur une Plaine pour avoir accès à l'ensoleillement'
        float enrg_prod =0;
        if (c.getTypeTerrain() instanceof Plaine) {
            float qualite = c.getQualite();
            float enseileillement = ((Plaine)c.getTypeTerrain()).getEnseileillement();
            enrg_prod = qualite * enseileillement * this.exposition * this.getNiveau() * this.getRendement();
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
        return "Panneau Sollaire de niveau "+this.getNiveau();
    }
}
