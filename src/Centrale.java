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
    public void consommerRessources( Case c ,Partie p){
        // On vérifie que la centrale est bien sur une Plaine pour avoir accès à la richesse du sol
        if (c instanceof Plaine) {
            Plaine maPlaine = (Plaine) c;
            float qualite = maPlaine.getQualite();
            float richesseSol = maPlaine.getRichesseSol();
            float res_csm = qualite * richesseSol * this.charbon_dispo * this.getNiveau() * this.getRendement();
            p.setRessources(p.getRessources() - Math.round(res_csm));
        }
    }
    @Override
    public void produireEnergie( Case c, Partie p) {
        // On vérifie que la centrale est bien sur une Plaine pour avoir accès à la richesse du sol
        if (c instanceof Plaine) {
            Plaine maPlaine = (Plaine) c;
            float qualite = maPlaine.getQualite();
            float richesseSol = maPlaine.getRichesseSol();
            float enrg_prod = qualite * richesseSol * this.charbon_dispo * this.getNiveau() * this.getRendement();
            p.setProduction_energie(p.getProduction_energie() + Math.round(enrg_prod));
        }
    }
    @Override
    public void BilanTour(Case c, Partie p) {
        consommerRessources(c,p);
        produireEnergie(c,p);
    }

}
