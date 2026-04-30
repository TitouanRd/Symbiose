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
    public void consommerRessources( Case c ,Partie p){
        // On vérifie que l'hydrolienne est bien sur un Lac pour avoir accès à la vitesse
        if (c instanceof Lac) {
            Lac monLac = (Lac) c;
            float qualite = monLac.getQualite();
            float vitesse = monLac.getVitesseCourant();
            float res_csm = qualite * vitesse * this.profondeur * this.getNiveau() * this.getRendement();
            p.setRessources(p.getRessources() - Math.round(res_csm));
        }
    }
    @Override
    public void produireEnergie( Case c, Partie p) {
        // On vérifie que l'hydrolienne est bien sur un Lac pour avoir accès à la vitesse
        if (c instanceof Lac) {
            Lac monLac = (Lac) c;
            float qualite = monLac.getQualite();
            float vitesse = monLac.getVitesseCourant();
            float enrg_prod = qualite * vitesse * this.profondeur * this.getNiveau() * this.getRendement();
            p.setProduction_energie(p.getProduction_energie() + 3*enrg_prod);
        }
    }
    @Override
    public void BilanTour(Case c, Partie p) {
        consommerRessources(c,p);
        produireEnergie(c,p);
    }

}
