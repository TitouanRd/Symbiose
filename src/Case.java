public class Case {
    private float pollution;
    private float qualite;
    private boolean occupation;
    private float temperature;
    private String sante_environnemental;
    private Meteo meteo;
    private Case[] voisines;
    private Construction construction = null;
    private final Carte carte;
    private int x,y;
    private TypeTerrain typeTerrain;

    public Case[] getVoisines() {
        return voisines;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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

    public Number[] fin_Tour() {
        System.err.println("Case fin_Tour");
        this.meteo.modificationMeteo();
        Number [] retoursCase = new Number[6];
        Number[] retoursTerrain = this.typeTerrain.fin_tour();
        if (construction != null) {
            Number[] retourConstruction = construction.BilanTour(this);
            retoursCase[0] = retourConstruction[0]; // ressources
            retoursCase[1] = retourConstruction[1]; // prod energie
        } else {
            retoursCase[0] = 0; // ressources
            retoursCase[1] = 0; // prod energie
        }
        retoursCase[2] = this.pollution;
        retoursCase[3] = this.qualite;
        retoursCase[4] = retoursTerrain[0]; //taux_foret
        retoursCase[5] = this.temperature;
        return retoursCase;
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

    public void construire(Construction construction) {
        if (this.construction == null) {
            this.construction = construction;
        }
    }

    public void detruire() {
        if (this.construction != null) {
            this.construction = null;
        }
    }
}
