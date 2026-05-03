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
    public void consommerRessources( Case c ,Partie p){
        // On vérifie que l'eolienne est bien sur une Plaine pour avoir accès à la vitesse du vent
        if (c.getTypeTerrain() instanceof Plaine) {
            float qualite = c.getQualite();
            float vitesseVent = ((Plaine)c.getTypeTerrain()).getVitesseVent();
            float res_csm = qualite * vitesseVent * this.hauteur * this.getNiveau() * this.getRendement();
            p.setRessources(p.getRessources() - Math.round(res_csm));
        }
        if (c.getTypeTerrain() instanceof Lac) {
            float qualite = c.getQualite();
            float vitesseVent = ((Lac)c.getTypeTerrain()).getVitesseVent();
            float res_csm = qualite * vitesseVent * this.hauteur * this.getNiveau() * this.getRendement();
            p.setRessources(p.getRessources() - Math.round(res_csm));
        }
    }
    @Override
    public void produireEnergie( Case c, Partie p) {
        // On vérifie que l'eolienne est bien sur une Plaine pour avoir accès à la vitesse du vent
        if (c.getTypeTerrain() instanceof Plaine) {
            float qualite = c.getQualite();
            float vitesseVent = ((Plaine)c.getTypeTerrain()).getVitesseVent();
            float enrg_prod = qualite * vitesseVent * this.hauteur * this.getNiveau() * this.getRendement();
            p.setProduction_energie(p.getProduction_energie() + enrg_prod);
        }
        if (c.getTypeTerrain() instanceof Lac) {
            float qualite = c.getQualite();
            float vitesseVent = ((Plaine)c.getTypeTerrain()).getVitesseVent();
            float enrg_prod = qualite * vitesseVent * this.hauteur * this.getNiveau() * this.getRendement();
            p.setProduction_energie(p.getProduction_energie() + 3*enrg_prod);
        }
    }
    @Override
    public void BilanTour(Case c, Partie p) {
        consommerRessources(c,p);
        produireEnergie(c,p);
    }
}
