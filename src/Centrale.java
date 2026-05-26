public class Centrale extends ProdEnergie{
    private String ressourcesUtiliser;
    private float charbon_dispo;
    public Centrale(int niveau) {
        // 1. Appel obligatoire à super() en TOUT PREMIER avec des valeurs par défaut
        // Signature ProdEnergie : niveau, cout, prodEnergie, prodRess, entretEnergie, entretRess, impQualite, impPollution, impVie, rendement
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
                this.setRessourcesUtiliser("Charbon");

                // Décommente si tu as bien créé le setter dans ta classe :
                // this.setCharbonDispo(100f);
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
                this.setRessourcesUtiliser("Uranium"); // J'ai mis une majuscule pour être cohérent avec "Charbon"

                // this.setCharbonDispo(0f);
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

                // this.setCharbonDispo(0f);
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
    public float consommerRessources( Case c){
        // On vérifie que la centrale est bien sur une Plaine pour avoir accès à la richesse du sol
        float res_csm = 0;
        if (c.getTypeTerrain() instanceof Plaine) {
            float qualite = c.getQualite();
            float richesseSol = ((Plaine)c.getTypeTerrain()).getRichesseSol();
            res_csm = qualite * richesseSol * this.charbon_dispo * this.getNiveau() * this.getRendement();
        }
        return res_csm;
    }
    @Override
    public float produireEnergie( Case c) {
        // On vérifie que la centrale est bien sur une Plaine pour avoir accès à la richesse du sol
        float enrg_prod = 0;
        if (c.getTypeTerrain() instanceof Plaine) {
            float qualite = c.getQualite();
            float richesseSol = ((Plaine)c.getTypeTerrain()).getRichesseSol();
            enrg_prod = qualite * richesseSol * this.charbon_dispo * this.getNiveau() * this.getRendement();
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
        return "central de niveau "+this.getNiveau();
    }

}
