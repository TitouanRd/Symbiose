import javax.swing.SwingUtilities;

public class Partie {
    private int ressources;
    private float production_energie;
    private int nb_tour;
    private int limite_tour;
    private int nb_actions;
    private String difficulter;
    private boolean limite_depassee;
    private Runnable updateListener;
    private Runnable mapChangeListener;
    private final Carte carte;

    public Partie(String difficulter, boolean limite_depassee, int limite_tour, int nb_actions, int nb_tour, float production_energie, int ressources, String tailleCarte) {
        this.difficulter = difficulter;
        this.limite_depassee = limite_depassee;
        this.limite_tour = limite_tour;
        this.nb_actions = nb_actions;
        this.nb_tour = nb_tour;
        this.production_energie = production_energie;
        this.ressources = ressources;
        this.carte = new Carte(tailleCarte, 0f, 0f, 0f, 0f, 0f, 0f, false, 0f, 0f, this);
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
        notifyUpdateListener();
    }

    public void setNb_tour(int nb_tour) {
        this.nb_tour = nb_tour;
        notifyUpdateListener();
    }

    public void setLimite_tour(int limite_tour) {
        this.limite_tour = limite_tour;
        notifyUpdateListener();
    }

    public void setNb_actions(int nb_actions) {
        this.nb_actions = nb_actions;
        notifyUpdateListener();
    }

    public void setDifficulter(String difficulter) {
        this.difficulter = difficulter;
    }

    // met à jour les info de la partie et rafraichie la grille
    public void setUpdateListener(Runnable updateListener) {
        this.updateListener = updateListener;
    }

    // met à jour la grille quand la carte change
    public void setMapChangeListener(Runnable mapChangeListener) {
        this.mapChangeListener = mapChangeListener;
    }

    // notifie les listeners de mise à jour pour rafraichir les info et la grille
    private void notifyUpdateListener() {
        if (updateListener != null) {
            SwingUtilities.invokeLater(updateListener);
        }
    }
    // notifie les listeners de changement de carte pour rafraichir la grille
    public void notifyMapChanged() {
        if (mapChangeListener != null) {
            SwingUtilities.invokeLater(mapChangeListener);
        }
    }

    public void setLimite_depassee(boolean limite_depassee) {
        this.limite_depassee = limite_depassee;
    }

    public void fin_Tour() {
        System.out.println("partie fin_Tour");
        this.getCarte().fin_Tour();
        this.nb_tour +=1;
        if (this.nb_tour==this.limite_tour) {
            System.out.println("Partie fini, nombre de tours dépassé");
        } else {
            Number[] retours = this.getCarte().fin_Tour();
            this.ressources += (int)retours[0];
            this.production_energie += (int)retours[1];
            if (production_energie>10000) {
                System.out.println("Partie fini, objecif de production atteint");
            } else if ((int)retours[2] == 1) {
                System.out.println("Partie fini, Une des limite à été dépassée");
            } else {
                //nouveau tour
                this.nb_actions = 3;
                notifyUpdateListener();// rafraichie les info de la partie
        System.out.println("partie show");
        this.carte.show();
    }

}}}
