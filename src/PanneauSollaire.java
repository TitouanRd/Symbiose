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
    public void consommerRessources( Case c ,Partie p){
        // On vérifie que le panneau sollaire est bien sur une Plaine pour avoir accès à l'ensoleillement
        if (c instanceof Plaine) {
            Plaine maPlaine = (Plaine) c;
            float qualite = maPlaine.getQualite();
            float enseileillement = maPlaine.getEnseileillement();
            float res_csm = qualite * enseileillement * this.exposition * this.getNiveau() * this.getRendement();
            p.setRessources(p.getRessources() - Math.round(res_csm));
        }
    }
    @Override
    public void produireEnergie( Case c, Partie p) {
        // On vérifie que le panneau solaire est bien sur une Plaine pour avoir accès à l'ensoleillement'
        if (c instanceof Plaine) {
            Plaine maPlaine = (Plaine) c;
            float qualite = maPlaine.getQualite();
            float enseileillement = maPlaine.getEnseileillement();
            float enrg_prod = qualite * enseileillement * this.exposition * this.getNiveau() * this.getRendement();
            p.setProduction_energie(p.getProduction_energie() + Math.round(enrg_prod));
        }
    }
    @Override
    public void BilanTour(Case c, Partie p) {
        consommerRessources(c,p);
        produireEnergie(c,p);
    }
}
