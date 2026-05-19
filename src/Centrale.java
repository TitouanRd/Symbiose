public class Centrale extends ProdEnergie{
    private String ressourcesUtiliser;
    private float charbon_dispo;
    public Centrale(float charbon_dispo,String ressourcesUtiliser,String type,int niveau,int cout,int productionEnergie, int productionRess,int entretienEnergie, int entretienRess,float impactQualite,float impactPollution,float impactVie, float rendement){
        super(type, niveau, cout, productionEnergie,  productionRess, entretienEnergie,  entretienRess, impactQualite, impactPollution,impactVie,rendement);
        this.ressourcesUtiliser = ressourcesUtiliser;
        this.charbon_dispo = charbon_dispo;
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

}
