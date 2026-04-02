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
    public void consommerRessources(){
        //A COMPLETER
    }
    public void produireEnergie(){
        //A COMPLETER
    }

    public void BilanTour() {
        //A COMPLETER
    }
}
