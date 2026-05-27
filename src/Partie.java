import javax.swing.*;

public class Partie {
    private int ressources;
    private float production_energie;
    private int nb_tour;
    private int limite_tour;
    private int nb_actions;
    private String difficulte;
    private boolean limite_depassee;
    private Runnable updateListener;
    private Runnable mapChangeListener;
    private final Carte carte;
    private Ville ville;

    public Partie(String difficulte, String tailleCarte) {
        // 1. Enregistrement des paramètres de la session
        this.difficulte = difficulte;

        // 2. Valeurs fixes pour un début de partie (Tour 0)
        this.limite_depassee = false;
        this.nb_tour = 0;
        this.nb_actions = 3;
        this.production_energie = 0f;

        // 3. Ajustement de l'économie selon la difficulté
        switch (difficulte.toLowerCase()) {
            case "facile" -> {
                this.ressources = 200; // Grand filet de sécurité
                this.limite_tour = 50; // Plus de temps pour atteindre les 10 000
            }
            case "difficile" -> {
                this.ressources = 50;  // Démarrage très tendu, aucune erreur permise
                this.limite_tour = 30; // Course contre la montre écologique
            }
            default -> { // "moyen"
                this.ressources = 100;
                this.limite_tour = 40;
            }
        }

        // 4. Génération de la carte (qui reçoit bien l'instance de Partie)
        this.carte = new Carte(tailleCarte, this);
    }
    public int getRessources() {
        return ressources;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
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

    public String getDifficulte() {
        return difficulte;
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

    public void setDifficulte(String difficulte) {
        this.difficulte = difficulte;
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
    public void notifyUpdateListener() {
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
        }

        // --- PRÉPARATION DU TOUR SUIVANT ---
        this.setNb_actions(3); // On réarme les actions
        notifyUpdateListener(); // On rafraîchit le bandeau UI
    }
}
