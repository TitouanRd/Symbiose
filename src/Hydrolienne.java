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
