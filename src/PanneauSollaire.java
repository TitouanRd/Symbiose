public class PanneauSollaire extends ProdEnergie{
    private float exposition;
    public PanneauSollaire(float exposition,String type,int niveau,int cout,int productionEnergie, int productionRess,int entretienEnergie, int entretienRess,float impactQualite,float impactPollution,float impactVie, float rendement) {
        super(type, niveau, cout, productionEnergie,  productionRess, entretienEnergie,  entretienRess, impactQualite, impactPollution,impactVie,rendement);
        this.exposition = exposition;
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
}
