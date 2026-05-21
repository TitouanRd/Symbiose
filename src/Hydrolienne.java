public class Hydrolienne extends ProdEnergie{
    private float profondeur;
    public Hydrolienne(float profondeur,String type,int niveau,int cout,int productionEnergie, int productionRess,int entretienEnergie, int entretienRess,float impactQualite,float impactPollution,float impactVie, float rendement){
        super(type, niveau, cout, productionEnergie,  productionRess, entretienEnergie,  entretienRess, impactQualite, impactPollution,impactVie,rendement);
        this.profondeur = profondeur;
    }
    public float getProfondeur() {
        return profondeur;
    }
    public void getP(float profondeur) {
        this.profondeur = profondeur;
    }

    @Override
    public float consommerRessources( Case c){
        // On vérifie que l'hydrolienne est bien sur un Lac pour avoir accès à la vitesse
        float res_csm =0;
        if (c.getTypeTerrain() instanceof Lac) {
            float qualite = c.getQualite();
            float vitesse = ((Lac)c.getTypeTerrain()).getVitesseCourant();
            res_csm = qualite * vitesse * this.profondeur * this.getNiveau() * this.getRendement();
        }
        return res_csm;
    }
    @Override
    public float produireEnergie( Case c) {
        // On vérifie que l'hydrolienne est bien sur un Lac pour avoir accès à la vitesse
        float enrg_prod =0;
        if (c.getTypeTerrain() instanceof Lac) {
            float qualite = c.getQualite();
            float vitesse = ((Lac)c.getTypeTerrain()).getVitesseCourant();
            enrg_prod = qualite * vitesse * this.profondeur * this.getNiveau() * this.getRendement();
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
        return "Hydrolienne de niveau "+this.getNiveau();
    }

}
