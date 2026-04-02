public abstract class ProdEnergie {
    private float impactVie;
    private float rendement;

    public ProdEnergie(float impactVie, float rendement) {
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
    public abstract void consommerRessources();
    public abstract void produireEnergie();
}
