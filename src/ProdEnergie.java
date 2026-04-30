public abstract class ProdEnergie extends Construction {
    private float impactVie;
    private float rendement;

    public ProdEnergie(String type,int niveau,int cout,int productionEnergie, int productionRess,int entretienEnergie, int entretienRess,float impactQualite,float impactPollution,float impactVie, float rendement) {
        super(type, niveau, cout, productionEnergie,  productionRess, entretienEnergie,  entretienRess, impactQualite, impactPollution);
        this.impactVie = impactVie;
        this.rendement = rendement;
    }
    public float getImpactVie() {
        return impactVie;
    }
    public float getRendement() {
        return rendement;
    }
    public void setImpactVie(float impactVie){
        this.impactVie = impactVie;
    }
    public void setRendement(float rendement){
        this.rendement = rendement;
    }
    public abstract void consommerRessources( Case c,Partie p);
    public abstract void produireEnergie( Case c,Partie p);
}
