public class Lac extends Case {
    private float vitesseCourant;
    private float vitesseVent;
    public float getVitesseCourant() {
        return vitesseCourant;
    }
    public void setVitesseCourant(float vitesseCourant) {
        this.vitesseCourant = vitesseCourant;
    }
    public float getVitesseVent() {
        return vitesseVent;
    }
    public void setVitesseVent(float vitesseVent) {
        this.vitesseVent = vitesseVent;
    }
    
    public Lac(float pollution, float qualite, String sante_environnemental, float vitesseCourant, float vitesseVent) {
        super(pollution, qualite, sante_environnemental);
        this.vitesseCourant = vitesseCourant;
        this.vitesseVent = vitesseVent;
    }
    public void remplir() {
        System.err.println("Lac remplire");
    }
    public void exploiter() {
        System.err.println("Lac exploiter");
    }
}
