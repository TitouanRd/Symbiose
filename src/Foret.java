public class Foret extends Case {
    private float recouvrementArbre;
    public float getrecouvrementArbre() {
        return recouvrementArbre;
    }
    public void setRecouvrementArbre(float recouvrementArbre) {
        this.recouvrementArbre = recouvrementArbre;
    }
    
    public Foret(float pollution, float qualite, String sante_environnemental, float recouvrementArbre) {
        super(pollution, qualite, sante_environnemental);
        this.recouvrementArbre = recouvrementArbre;
    }
    public void raser() {
        System.err.println("Foret rasze");
    }
    public void exploiter() {
        System.err.println("Foret exploiter");
    }
}
