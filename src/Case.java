public class Case {
    private float pollution;
    private float qualite;
    private boolean occupation;
    private String sante_environnemental;
    private Meteo meteo;
    private Case[] voisines;
    private Construction construction;
    private final Carte carte;
    private int x,y;
    private TypeTerrain typeTerrain;

    public Case[] getVoisines() {
        return voisines;
    }

    public void setVoisines(Case[] voisines) {
        this.voisines = voisines;
    }

    public Construction getConstruction() {
        return construction;
    }

    public void setConstruction(Construction construction) {
        this.construction = construction;
    }

    public Meteo getMeteo() {
        return meteo;
    }

    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }

    public void fin_Tour() {
        System.err.println("Case fin_Tour");
        this.meteo.modificationMeteo();
        this.typeTerrain.fin_tour();
    }

    public void show() {
        this.typeTerrain.show();
    }

    public void afficherInformations(){
        System.err.println("Case informations");
    }
    public void construir(){
        System.err.println("Case construir");
    }

    public float getPollution() {
        return pollution;
    }

    public void setPollution(float pollution) {
        this.pollution = pollution;
    }

    public float getQualite() {
        return qualite;
    }

    public void setQualite(float qualite) {
        this.qualite = qualite;
    }

    public boolean isOccupation() {
        return occupation;
    }

    public void setOccupation(boolean occupation) {
        this.occupation = occupation;
    }

    public String getSante_environnemental() {
        return sante_environnemental;
    }

    public void setSante_environnemental(String sante_environnemental) {
        this.sante_environnemental = sante_environnemental;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TypeTerrain getTypeTerrain() {
        return typeTerrain;
    }

    public void setTypeTerrain(TypeTerrain typeTerrain) {
        this.typeTerrain = typeTerrain;
    }

    public Case(float pollution, float qualite, String sante_environnemental, Carte carte, TypeTerrain typeTerrain) {
        this.pollution = pollution;
        this.qualite = qualite;
        this.occupation = false;
        this.sante_environnemental = sante_environnemental;
        this.carte = carte;
        this.typeTerrain = typeTerrain;
    }

    public Carte getCarte() {
        return carte;
    }
}
