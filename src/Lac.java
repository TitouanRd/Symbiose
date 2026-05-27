import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Lac extends TypeTerrain {
    private float vitesseCourant;
    private float vitesseVent;
    private Case parent;

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

    public Case getParent() {
        return parent;
    }

    public void setParent(Case parent) {
        this.parent = parent;
    }

    public Lac(float vitesseCourant, float vitesseVent, Case parent) {
        this.vitesseCourant = vitesseCourant;
        this.vitesseVent = vitesseVent;
        this.parent = parent;

    }
    public void remplir() {
        Plaine plaine = new Plaine(this.getVitesseVent(),50f,15f,50f,this.getParent());
        this.getParent().setTypeTerrain(plaine);
        this.getParent().getCarte().getPartie().notifyMapChanged();// rafraichie la grille
    }
    public void exploiter() {
        System.err.println("Lac exploiter");
        this.getParent().getCarte().getPartie().notifyMapChanged();// rafraichie la grille
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" |   Lac  | ");
        //sb.append("vitesseCourant=").append(vitesseCourant);
        //sb.append(", vitesseVent=").append(vitesseVent);
        //sb.append('}');
        return sb.toString();
    }

    @Override
    public Number[] fin_tour(Case c) {
        if (c.getPollution() < 40f) {
            // Auto-épuration naturelle de l'eau si la pollution reste modérée
            c.setPollution(Math.max(0f, c.getPollution() - 1.5f));
        } else {
            // Si le lac est saturé de produits toxiques, sa qualité s'effondre d'elle-même
            c.setQualite(Math.max(0f, c.getQualite() - 2f));
        }

        Number[] retour = new Number[1];
        retour[0] = 0f; // Pas de forêt sur un lac
        return retour;
    }
    @Override
    // gère le nombre d'actions restantes pour les actions sur le lac
    public boolean  nb_tour() {
        Partie partie = parent.getCarte().getPartie();
        int actionsRestantes = partie.getNb_actions();
        System.out.println("Actions restantes avant action: " + actionsRestantes);
        if (actionsRestantes > 0) {
            partie.setNb_actions(actionsRestantes - 1);
            return true;
        }
        return false;
    }

// Affiche les informations du lac et les actions possibles
    public void show(Partie partie) {
        JFrame frame = new JFrame("Lac");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Afficher les informations du lac
        JLabel infoLabel = new JLabel("Informations du lac :");
        infoLabel.setAlignmentX(panel.CENTER_ALIGNMENT);
        panel.add(infoLabel);

        JTextArea infoArea = new JTextArea(10, 20);
        infoArea.setEditable(false);
        infoArea.setText(
            "- Pollution: " + parent.getPollution() + "\n" +
            "- Qualité: " + parent.getQualite() + "\n" +
            "- Santé environnementale: " + parent.getSante_environnemental() + "\n" +
            "- Meteo: " + parent.getMeteo() + "\n" +
            "- Construction: " + parent.getConstruction() + "\n" +
            "- Vitesse du courant: " + vitesseCourant + "\n" +
            "- Vitesse du vent: " + vitesseVent
        );
        panel.add(new JScrollPane(infoArea));

        // Actions
        JLabel actionLabel = new JLabel("Actions sur un lac :");
        actionLabel.setAlignmentX(panel.CENTER_ALIGNMENT);
        panel.add(actionLabel);

        JPanel buttonPanel = new JPanel();
        // --- LOGIQUE DE VÉRIFICATION DU TUTORIEL ---
        boolean modeTuto = (Ville.getNbVille() == 0);

<<<<<<< HEAD
=======
<<<<<<< Updated upstream
        JButton remplire = new JButton("Remplir");
        remplire.addActionListener(e -> {
            if (nb_tour()) {
                remplir();
            }
            frame.dispose();
        });
        buttonPanel.add(remplire);
=======
>>>>>>> IHM

        if (modeTuto) {
            // En mode tuto, on remplace les boutons par un message explicatif explicite
            JLabel labelAvertissement = new JLabel("⚠️ Action impossible. Construisez d'abord votre Ville sur une Plaine !");
            labelAvertissement.setForeground(Color.RED);
            buttonPanel.add(labelAvertissement);
        } else {
<<<<<<< HEAD
            // Le jeu standard reprend si la ville est construite
            JButton exploiter = new JButton("Exploiter");
            exploiter.addActionListener(e -> {
                if (nb_tour()) { // vérifie si le joueur a des actions restantes pour exploiter le lac
                    exploiter();
                }else {
                    JOptionPane.showMessageDialog(frame, "Vous n'avez plus d'actions disponibles pour ce tour !");
                }
                frame.dispose();


            });
            buttonPanel.add(exploiter);
=======
            
>>>>>>> IHM

            JButton remplire = new JButton("Remplir");
            remplire.addActionListener(e -> {
                if (nb_tour()) {
                    remplir();
                }else {
                    JOptionPane.showMessageDialog(frame, "Vous n'avez plus d'actions disponibles pour ce tour !");
                }
                frame.dispose();
            });
            buttonPanel.add(remplire);

            JButton construire = new JButton("Construire");
            construire.addActionListener(e -> {

                    // 1. Création de la DEUXIÈME fenêtre
                    JFrame frame1 = new JFrame("Construire");

                    JPanel panel1 = new JPanel();
                    panel1.setLayout(new BorderLayout()); // Correction ici : panel1 et non panel

                    JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JLabel label = new JLabel("Constructions possibles:");
                    panel1.add(label, BorderLayout.NORTH); // Optionnel : pour un meilleur rendu visuel

                    JButton ex = new JButton("Exploitation");
                    ex.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed (ActionEvent e){
                                if (nb_tour()) {
                            int niveau = 1;
                            Partie currentPartie = getParent().getCarte().getPartie();
                            if (currentPartie != null && currentPartie.getVille() != null) {
                                niveau = currentPartie.getVille().getNiveau();
                            }
                            Exploitation exploitation = new Exploitation(niveau);
                            if (currentPartie.getRessources() >= exploitation.getCout()) {

                                // 2. On DÉDUIT le coût de la construction du compte de la Partie
                                currentPartie.setRessources(currentPartie.getRessources() - exploitation.getCout());

                                // 3. On construit physiquement le bâtiment
                                getParent().construire(exploitation);
                                getParent().setOccupation(true);
                                frame1.dispose();

                                // 4. On rafraîchit l'interface
                                currentPartie.notifyMapChanged();
                                currentPartie.notifyUpdateListener();

                            } else {
                                // Si pas assez d'argent, on avertit le joueur sans fermer le menu
                                JOptionPane.showMessageDialog(frame1, "Ressources insuffisantes ! Coût : " + exploitation.getCout(), "Erreur", JOptionPane.WARNING_MESSAGE);
                            }
                        }else {
                                    JOptionPane.showMessageDialog(frame, "Vous n'avez plus d'actions disponibles pour ce tour !");
                                }
                        }
                    });
                    panel2.add(ex);

                    JButton hydro = new JButton("Hydrolienne");
                    hydro.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed (ActionEvent e){
                            if (nb_tour()) {
                            int niveau = 1;
                            Partie currentPartie = getParent().getCarte().getPartie();
                            if (currentPartie != null && currentPartie.getVille() != null) {
                                niveau = currentPartie.getVille().getNiveau();
                            }
                            Hydrolienne hydro = new Hydrolienne(niveau);
                            if (currentPartie.getRessources() >= hydro.getCout()) {

                                // 2. On DÉDUIT le coût de la construction du compte de la Partie
                                currentPartie.setRessources(currentPartie.getRessources() - hydro.getCout());

                                // 3. On construit physiquement le bâtiment
                                getParent().construire(hydro);
                                getParent().setOccupation(true);
                                frame1.dispose();

                                // 4. On rafraîchit l'interface
                                currentPartie.notifyMapChanged();
                                currentPartie.notifyUpdateListener();

                            } else {
                                // Si pas assez d'argent, on avertit le joueur sans fermer le menu
                                JOptionPane.showMessageDialog(frame1, "Ressources insuffisantes ! Coût : " + hydro.getCout(), "Erreur", JOptionPane.WARNING_MESSAGE);
                            }
                        }else {
                                JOptionPane.showMessageDialog(frame, "Vous n'avez plus d'actions disponibles pour ce tour !");
                            }
                        }
                    });
                    panel2.add(hydro);

                    JButton eol = new JButton("Eolienne");
                    eol.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if (nb_tour()) {
                            int niveau = 1;
                            Partie currentPartie = getParent().getCarte().getPartie();
                            if (currentPartie != null && currentPartie.getVille() != null) {
                                niveau = currentPartie.getVille().getNiveau();
                            }
                            Eolienne eolienne = new Eolienne(niveau);
                            if (currentPartie.getRessources() >= eolienne.getCout()) {

                                // 2. On DÉDUIT le coût de la construction du compte de la Partie
                                currentPartie.setRessources(currentPartie.getRessources() - eolienne.getCout());

                                // 3. On construit physiquement le bâtiment
                                getParent().construire(eolienne);
                                getParent().setOccupation(true);
                                frame1.dispose();

                                // 4. On rafraîchit l'interface
                                currentPartie.notifyMapChanged();
                                currentPartie.notifyUpdateListener();

                            } else {
                                // Si pas assez d'argent, on avertit le joueur sans fermer le menu
                                JOptionPane.showMessageDialog(frame1, "Ressources insuffisantes ! Coût : " + eolienne.getCout(), "Erreur", JOptionPane.WARNING_MESSAGE);
                            }
                        }else {
                                    JOptionPane.showMessageDialog(frame, "Vous n'avez plus d'actions disponibles pour ce tour !");
                                }
                        }
                    });
                    panel2.add(eol);

                    JButton del = new JButton("Détruire");
                    del.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (nb_tour()) {
                                int niveau = 1;
                                Partie currentPartie = getParent().getCarte().getPartie();
                                if (currentPartie != null && currentPartie.getVille() != null) {
                                    niveau = currentPartie.getVille().getNiveau();
                                }

                                getParent().detruire();
                                getParent().setOccupation(false);
                                frame1.dispose();
                                if (currentPartie != null) {
                                    currentPartie.notifyMapChanged();
                                    currentPartie.notifyUpdateListener();
                                }
                            }else {
                                JOptionPane.showMessageDialog(frame, "Vous n'avez plus d'actions disponibles pour ce tour !");
                            }
                        }
                    });
                    panel2.add(del);



                    panel1.add(panel2, BorderLayout.CENTER);

                    frame1.add(panel1);

                    // CORRECTION : On applique les configurations à frame1 et on utilise DISPOSE_ON_CLOSE
                    frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame1.pack();
                    frame1.setLocationRelativeTo(null);
                    frame1.setVisible(true);

                    // CORRECTION : On ne ferme la première fenêtre QUE si la condition nb_tour() est vraie
                    frame.dispose();

            });
            buttonPanel.add(construire);
        }
<<<<<<< HEAD
=======
>>>>>>> Stashed changes
>>>>>>> IHM

        panel.add(buttonPanel);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
