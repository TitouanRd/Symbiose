public abstract class Construction{
    private int niveau;
    private int cout;
    private int productionEnergie;
    private int productionRess;
    private int entretienEnergie;
    private int entretienRess;
    private float impactQualite;
    private float impactPollution;

    public Construction(int niveau,int cout,int productionEnergie, int productionRess,int entretienEnergie, int entretienRess,float impactQualite,float impactPollution) {
        this.niveau = niveau;
        this.cout = cout;;
        this.productionEnergie = productionEnergie;
        this.productionRess = productionRess;
        this.entretienEnergie = entretienEnergie;
        this.entretienRess = entretienRess;
        this.impactQualite = impactQualite;
        this.impactPollution = impactPollution;

    }
    public int getNiveau() {
        return niveau;
    }
    public int getCout() {
        return cout;
    }
    public int getProductionEnergie() {
        return productionEnergie;
    }
    public int getProductionRess() {
        return productionRess;
    }
    public int getEntretienEnergie() {
        return entretienEnergie;
    }
    public int getEntretienRess() {
    return entretienRess;}
    public float getImpactQualite() {
        return impactQualite;
    }
    public float getImpactPollution() {
        return impactPollution;
    }
    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }
    public void setCout(int cout) {
        this.cout = cout;
    }
    public void setProductionEnergie(int productionEnergie) {
        this.productionEnergie = productionEnergie;
    }
    public void setProductionRess(int productionRess) {
        this.productionRess = productionRess;
    }
    public void setEntretienEnergie(int entretienEnergie) {
        this.entretienEnergie = entretienEnergie;
    }
    public void setEntretienRess(int entretienRess) {
        this.entretienRess = entretienRess;
    }
    public void setImpactQualite(float impactQualite) {
        this.impactQualite = impactQualite;
    }
    public void setImpactPollution(float impactPollution) {
        this.impactPollution = impactPollution;
    }
    public abstract Number[] BilanTour(Case c);
}
