public class Ville extends Construction {
    private static int nbVille = 0;

    public Ville(int niveau) {

        super(niveau, 0, 0, 0, 0, 0, 0f, 0f);

        // 2. On modifie les attributs en fonction du niveau grâce aux setters
        switch(niveau) {
            case 1:
                this.setCout(10);
                this.setProductionEnergie(0);
                this.setProductionRess(0);
                this.setEntretienEnergie(10);
                this.setEntretienRess(5);
                this.setImpactQualite(10f);
                this.setImpactPollution(10f);
                break;

            case 2:
                this.setCout(20);
                this.setProductionEnergie(0);
                this.setProductionRess(0);
                this.setEntretienEnergie(40);
                this.setEntretienRess(20);
                this.setImpactQualite(15f);
                this.setImpactPollution(20f);
                break; // Attention : tu avais oublié ce break dans ton code d'origine !

            default:
                this.setCout(0);
                this.setProductionEnergie(0);
                this.setProductionRess(0);
                this.setEntretienEnergie(0);
                this.setEntretienRess(0);
                this.setImpactQualite(0f);
                this.setImpactPollution(0f);
                break;
        }
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