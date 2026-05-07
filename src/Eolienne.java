public class Eolienne extends ProdEnergie{
    private float hauteur;
    public Eolienne(float hauteur,String type,int niveau,int cout,int productionEnergie, int productionRess,int entretienEnergie, int entretienRess,float impactQualite,float impactPollution,float impactVie, float rendement) {
        super(type, niveau, cout, productionEnergie,  productionRess, entretienEnergie,  entretienRess, impactQualite, impactPollution,impactVie,rendement);
        this.hauteur = hauteur;
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
}
