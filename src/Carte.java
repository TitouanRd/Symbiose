public class Carte {
    private float temp;
    private float limite_temp;
    private float vie_sauvage;
    private float limite_vie_sauvage;
    private float foret;
    private float limite_foret;
    private float pollution;
    private float limite_pollution;
    private boolean presVile;

    public Carte(float foret, float limite_foret, float limite_pollution, float limite_temp, float limite_vie_sauvage, float pollution, boolean presVile, float temp, float vie_sauvage) {
        this.foret = foret;
        this.limite_foret = limite_foret;
        this.limite_pollution = limite_pollution;
        this.limite_temp = limite_temp;
        this.limite_vie_sauvage = limite_vie_sauvage;
        this.pollution = pollution;
        this.presVile = presVile;
        this.temp = temp;
        this.vie_sauvage = vie_sauvage;
    }

    
    public void fin_Tour() {
        System.err.println("Carte fin_Tour");
        this.show();
    }
    public void show() {
        System.err.println("Carte show");
    }

    public float getTemp() {
        return temp;
    }

    public float getLimite_temp() {
        return limite_temp;
    }

    public float getVie_sauvage() {
        return vie_sauvage;
    }

    public float getLimite_vie_sauvage() {
        return limite_vie_sauvage;
    }

    public float getForet() {
        return foret;
    }

    public float getLimite_foret() {
        return limite_foret;
    }

    public float getPollution() {
        return pollution;
    }

    public float getLimite_pollution() {
        return limite_pollution;
    }

    public boolean isPresVile() {
        return presVile;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public void setLimite_temp(float limite_temp) {
        this.limite_temp = limite_temp;
    }

    public void setVie_sauvage(float vie_sauvage) {
        this.vie_sauvage = vie_sauvage;
    }

    public void setLimite_vie_sauvage(float limite_vie_sauvage) {
        this.limite_vie_sauvage = limite_vie_sauvage;
    }

    public void setForet(float foret) {
        this.foret = foret;
    }

    public void setLimite_foret(float limite_foret) {
        this.limite_foret = limite_foret;
    }

    public void setPollution(float pollution) {
        this.pollution = pollution;
    }

    public void setLimite_pollution(float limite_pollution) {
        this.limite_pollution = limite_pollution;
    }

    public void setPresVile() {
        this.presVile = true;
    }
}
