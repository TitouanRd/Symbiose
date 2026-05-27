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
<<<<<<< Updated upstream
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
=======
        System.out.println("--- FIN DU TOUR " + this.nb_tour + " ---");
        this.nb_tour++;

        // 1. Récupération du bilan global de l'écosystème
        Number[] retours = this.getCarte().fin_Tour();

        // 2. Mise à jour des stocks
        this.ressources += retours[0].intValue();
        this.production_energie += retours[1].intValue();

        // --- VÉRIFICATIONS DES CONDITIONS DE DÉFAITE (Priorité absolue) ---

        // A. Effondrement écologique (Limites planétaires)
        if (retours[2].intValue() == 1) {
            JOptionPane.showMessageDialog(null,
                    "DÉFAITE : La nature n'a pas survécu à votre expansion. Les limites planétaires ont été franchies.",
                    "Game Over", JOptionPane.ERROR_MESSAGE);
            notifyUpdateListener();
            System.exit(0);
            return; // Stoppe net l'exécution
            
        }

        // B. Banqueroute matérielle (Plus de bois/métal pour l'entretien)
        if (this.ressources < 0) {
            JOptionPane.showMessageDialog(null,
                    "DÉFAITE : Vous êtes ruiné ! Vos infrastructures s'effondrent par manque d'entretien.",
                    "Banqueroute", JOptionPane.ERROR_MESSAGE);
            notifyUpdateListener();
            System.exit(0);
            return;
        }

        // C. Blackout énergétique sévère
        if (this.production_energie < -200) {
            JOptionPane.showMessageDialog(null,
                    "DÉFAITE : Blackout total ! Votre ville est paralysée par le manque d'énergie.",
                    "Blackout", JOptionPane.ERROR_MESSAGE);
            notifyUpdateListener();
            System.exit(0);
            return;
        }

        // D. Limite de temps écoulée
        if (this.nb_tour >= this.limite_tour) {
            JOptionPane.showMessageDialog(null,
                    "DÉFAITE : Le temps imparti est écoulé. Vous n'avez pas atteint l'indépendance énergétique.",
                    "Fin du temps", JOptionPane.WARNING_MESSAGE);
            notifyUpdateListener();
            System.exit(0);
            return;
        }

        // --- VÉRIFICATIONS DES CONDITIONS DE VICTOIRE ET D'ÉVOLUTION ---

        // Victoire finale (10 000 est un très bon cap avec les valeurs actuelles)
        if (this.production_energie >= 1000) {
            JOptionPane.showMessageDialog(null,
                    "VICTOIRE ! Vous avez atteint l'objectif énergétique tout en maintenant l'équilibre du système !",
                    "Félicitations", JOptionPane.INFORMATION_MESSAGE);
            notifyUpdateListener();
            System.exit(0);
            return;
        }

        // Évolution de la ville
        // SÉCURITÉ : On vérifie que la référence "ville" n'est pas nulle avant d'appeler ses méthodes
        if (this.production_energie >= 500 && this.ville != null && this.ville.getNiveau() != 2) {
            this.ville.monterNiveau();
            JOptionPane.showMessageDialog(null,
                    "Développement : Votre ville passe au Niveau 2 !",
                    "Évolution", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        // --- PRÉPARATION DU TOUR SUIVANT ---
        this.setNb_actions(3); // On réarme les actions
        notifyUpdateListener(); // On rafraîchit le bandeau UI
>>>>>>> Stashed changes
    }

}}}
