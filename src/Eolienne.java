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
    public void consommerRessources(){
        //A COMPLETER
    }
    public void produireEnergie() {
        //A COMPLETER
    }
    public void BilanTour(){
        //A COMPLETER
        }
}
