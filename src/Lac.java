import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


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
    public Number[] fin_tour() {
        Number[] retour = new Number[1];
        retour[0] = 0f;
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


        if (modeTuto) {
            // En mode tuto, on remplace les boutons par un message explicatif explicite
            JLabel labelAvertissement = new JLabel("⚠️ Action impossible. Construisez d'abord votre Ville sur une Plaine !");
            labelAvertissement.setForeground(Color.RED);
            buttonPanel.add(labelAvertissement);
        } else {
            // Le jeu standard reprend si la ville est construite
            JButton exploiter = new JButton("Exploiter");
            exploiter.addActionListener(e -> {
                if (nb_tour()) { // vérifie si le joueur a des actions restantes pour exploiter le lac
                    exploiter();
                }
                frame.dispose();
            });
            buttonPanel.add(exploiter);

            JButton remplire = new JButton("Remplir");
            remplire.addActionListener(e -> {
                if (nb_tour()) {
                    remplir();
                }
                frame.dispose();
            });
            buttonPanel.add(remplire);

            JButton construire = new JButton("Construire");
            construire.addActionListener(e -> {
                if (nb_tour()) {
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
                        public void actionPerformed(ActionEvent e) {
                            Exploitation exploitation = new Exploitation(0,"exploitation",0,0,0,0,0,0,0,0);
                            getParent().construire(exploitation);
                            frame1.dispose();
                        }
                    });

                    panel2.add(ex);
                    JButton hydro = new JButton("Hydrolienne");
                    hydro.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Hydrolienne hydro = new Hydrolienne(0,"hydrolienne",0,0,0,0,0,0,0,0,0,0);
                            getParent().construire(hydro);
                            frame1.dispose();
                        }
                    });
                    panel2.add(hydro);

                    JButton eol = new JButton("Eolienne");
                    eol.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Eolienne eolienne = new Eolienne(0,"Eolienne",0,0,0,0,0,0,0,0,0,0);
                            getParent().construire(eolienne);
                            frame1.dispose();
                        }
                    });
                    panel2.add(eol);

                    JButton del = new JButton("Détruire");
                    del.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            getParent().detruire();
                            frame1.dispose();
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
                }
            });
            buttonPanel.add(construire);
        }

        panel.add(buttonPanel);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
