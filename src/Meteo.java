public class Meteo {
    private String nom;
    private float tauxVent;
    private float tauxCourant;
    private float tauxEnsoleillement;
    private float tauxVie;
    private float tauxdestruction;

    public String getNom() {
        return nom;
    }

    public Meteo(String nom, float tauxVent, float tauxCourant, float tauxEnsoleillement, float tauxVie,
            float tauxdestruction) {
        this.nom = nom;
        this.tauxVent = tauxVent;
        this.tauxCourant = tauxCourant;
        this.tauxEnsoleillement = tauxEnsoleillement;
        this.tauxVie = tauxVie;
        this.tauxdestruction = tauxdestruction;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getTauxVent() {
        return tauxVent;
    }

    public void setTauxVent(float tauxVent) {
        this.tauxVent = tauxVent;
    }

    public float getTauxCourant() {
        return tauxCourant;
    }

    public void setTauxCourant(float tauxCourant) {
        this.tauxCourant = tauxCourant;
    }

    public float getTauxEnsoleillement() {
        return tauxEnsoleillement;
    }

    public void setTauxEnsoleillement(float tauxEnsoleillement) {
        this.tauxEnsoleillement = tauxEnsoleillement;
    }

    public float getTauxVie() {
        return tauxVie;
    }

    public void setTauxVie(float tauxVie) {
        this.tauxVie = tauxVie;
    }

    public float getTauxdestruction() {
        return tauxdestruction;
    }

    public void setTauxdestruction(float tauxdestruction) {
        this.tauxdestruction = tauxdestruction;
    }

    public void modificationMeteo() {
        System.err.println("Modif Météo");
    }
}
