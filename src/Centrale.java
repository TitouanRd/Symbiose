public class Centrale extends ProdEnergie{
    private String ressourcesUtiliser;
    public Centrale(String ressourcesUtiliser,String type,int niveau,int cout,int productionEnergie, int productionRess,int entretienEnergie, int entretienRess,float impactQualite,float impactPollution,float impactVie, float rendement){
        super(type, niveau, cout, productionEnergie,  productionRess, entretienEnergie,  entretienRess, impactQualite, impactPollution,impactVie,rendement);
        this.ressourcesUtiliser = ressourcesUtiliser;
    }
    public String getRessourcesUtiliser() {
        return ressourcesUtiliser;
    }
    public void setRessourcesUtiliser(String ressourcesUtiliser) {
        this.ressourcesUtiliser = ressourcesUtiliser;
    }

    public void consommerRessources() {
        //A COMPLETER
    }
    public void produireEnergie() {
        //A COMPLETER
    }
    public void BilanTour(){
        //A COMPLETER
    }

}
