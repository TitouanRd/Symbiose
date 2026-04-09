public class Partie {
    private int ressources;
    private float production_energie;
    private int nb_tour;
    private int limite_tour;
    private int nb_actions;
    private String difficulter;
    private boolean limite_depassee;
    private final Carte carte;

    public Partie(String difficulter, boolean limite_depassee, int limite_tour, int nb_actions, int nb_tour, float production_energie, int ressources, String tailleCarte) {
        this.difficulter = difficulter;
        this.limite_depassee = limite_depassee;
        this.limite_tour = limite_tour;
        this.nb_actions = nb_actions;
        this.nb_tour = nb_tour;
        this.production_energie = production_energie;
        this.ressources = ressources;
        this.carte = new Carte(tailleCarte,0f,0f,0f,0f,0f,0f,false,0f,0f);
    }

    public int getRessources() {
        return ressources;
    }

    public float getProduction_energie() {
        return production_energie;
    }

    public int getNb_tour() {
        return nb_tour;
    }

    public int getLimite_tour() {
        return limite_tour;
    }

    public int getNb_actions() {
        return nb_actions;
    }

    public String getDifficulter() {
        return difficulter;
    }

    public boolean isLimite_depassee() {
        return limite_depassee;
    }
    public Carte getCarte() {
        return carte;
    }

    public void setRessources(int ressources) {
        this.ressources = ressources;
    }

    public void setProduction_energie(float production_energie) {
        this.production_energie = production_energie;
    }

    public void setNb_tour(int nb_tour) {
        this.nb_tour = nb_tour;
    }

    public void setLimite_tour(int limite_tour) {
        this.limite_tour = limite_tour;
    }

    public void setNb_actions(int nb_actions) {
        this.nb_actions = nb_actions;
    }

    public void setDifficulter(String difficulter) {
        this.difficulter = difficulter;
    }

    public void setLimite_depassee(boolean limite_depassee) {
        this.limite_depassee = limite_depassee;
    }

    public void fin_Tour() {
        System.out.println("partie fin_Tour");
        this.show();
    }

    public void show() {
        System.out.println("partie show");
        this.carte.show();
    }

    
    

}
