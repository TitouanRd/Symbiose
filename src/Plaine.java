
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class Plaine extends TypeTerrain {
    private float vitesseVent;
    private float enseileillement;
    private float temperatureSol;
    private float richesseSol;
    private boolean protege = false;
    private Case parent;

    public float getVitesseVent() {
        return vitesseVent;
    }
    public void setVitesseVent(float vitesseVent) {
        this.vitesseVent = vitesseVent;
    }
    public float getEnseileillement() {
        return enseileillement;
    }
    public void setEnseileillement(float enseileillement) {
        this.enseileillement = enseileillement;
    }
    public float getTemperatureSol() {
        return temperatureSol;
    }
    public void setTemperatureSol(float temperatureSol) {
        this.temperatureSol = temperatureSol;
    }
    public float getRichesseSol() {
        return richesseSol;
    }
    public void setRichesseSol(float richesseSol) {
        this.richesseSol = richesseSol;
    }

    
    public Plaine(float vitesseVent, float enseileillement, float temperatureSol, float richesseSol, Case parent) {
        this.vitesseVent = vitesseVent;
        this.enseileillement = enseileillement;
        this.temperatureSol = temperatureSol;
        this.richesseSol = richesseSol;
        this.parent = parent;
    }

    public Case getParent() {
        return parent;
    }

    public void setParent(Case parent) {
        this.parent = parent;
    }

    public void creuser(){
        Lac lac = new Lac(15f,this.getVitesseVent(),this.getParent());
        this.getParent().setTypeTerrain(lac);
        this.getParent().getCarte().getPartie().notifyMapChanged();// rafraichie la grille pour afficher le lac
    }
    public void planterForet(){
        Foret foret = new Foret(50f,this.getParent());
        this.getParent().setTypeTerrain(foret);
        this.getParent().getCarte().getPartie().notifyMapChanged();// rafraichie la grille pour afficher la foret
    }
    public void proteger(){
        this.protege = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" | Plaine | ");
        //sb.append("vitesseVent=").append(vitesseVent);
        //sb.append(", enseileillement=").append(enseileillement);
        //sb.append(", temperatureSol=").append(temperatureSol);
        //sb.append(", richesseSol=").append(richesseSol);
        //sb.append('}');
        return sb.toString();
    }

    @Override
    public Number[] fin_tour() {
        Number[] retour = new Number[1];
        retour[0] = 0f;
        return retour;
    }

    public void show(Partie partie) { // affiche les info de la plaine et les actions possibles dans une nouvelle fenêtre
        JFrame frame = new JFrame("Plaine");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Afficher les informations de la plaine
        JLabel infoLabel = new JLabel("Informations de la plaine :");
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
                        "- Ensoleillement: " + enseileillement + "\n" +
                        "- Temperature du sol: " + temperatureSol + "\n" +
                        "- Richesse du sol: " + richesseSol + "\n" +
                        "- Vitesse du vent: " + vitesseVent
        );
        panel.add(new JScrollPane(infoArea));

        // Actions
        JLabel actionLabel = new JLabel("Actions sur une plaine :");
        actionLabel.setAlignmentX(panel.CENTER_ALIGNMENT);
        panel.add(actionLabel);

        JPanel buttonPanel = new JPanel();

        // --- LOGIQUE DE VÉRIFICATION DU TUTORIEL (DEPART) ---
        // Si le compteur statique de Ville est à 0, le joueur n'a pas encore posé sa base
        boolean modeTuto = (Ville.getNbVille() == 0);

        JButton construireVille = new JButton("Construire une Ville");
        construireVille.addActionListener(e -> {
            // On vérifie s'il reste des actions disponibles au joueur
            if (partie.getNb_actions() > 0) {
                // 1. Modification du modèle
                parent.setConstruction(new Ville("Ville", 1, 0, 0, 0, 0, 0, 0f, 0f));
                parent.setOccupation(true);

                // 2. Consommation de la ressource d'action
                partie.setNb_actions(partie.getNb_actions() - 1);

                // 3. Notification pour mettre à jour le texte du haut (Bandeau de l'App)
                partie.notifyUpdateListener();

                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Vous n'avez plus d'actions disponibles pour ce tour !");
            }
        });

        // Filtrage des boutons
        if (modeTuto) {
            buttonPanel.add(construireVille);
        } else {
            // Boutons standards (pense à leur passer aussi la vérification via 'partie' si nécessaire)
            JButton creuser = new JButton("Creuser");
            creuser.addActionListener(ev -> { if(nb_tour()) creuser(); frame.dispose(); });

            JButton planterForet = new JButton("Planter une forêt");
            planterForet.addActionListener(ev -> { if(nb_tour()) planterForet(); frame.dispose(); });

            JButton proteger = new JButton("Proteger");
            proteger.addActionListener(ev -> { if(nb_tour()) proteger(); frame.dispose(); });

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

                JButton cen = new JButton("Centrale");
                cen.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Centrale centrale = new Centrale(0,"charbon","centrale",0,0,0,0,0,0,0,0,0,0);
                        getParent().construire(centrale);
                        frame1.dispose();
                    }
                });
                panel2.add(cen);

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

                JButton pano = new JButton("PanneauSolaire");
                pano.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PanneauSollaire panneausolaire = new PanneauSollaire(0,"panneausolaire",0,0,0,0,0,0,0,0,0,0);
                        getParent().construire(panneausolaire);
                        frame1.dispose();
                    }
                });
                panel2.add(pano);

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

// Configuration et affichage de la PREMIÈRE fenêtre
        buttonPanel.add(construire);
            buttonPanel.add(creuser);
            buttonPanel.add(planterForet);
            buttonPanel.add(proteger);


        }

        panel.add(buttonPanel);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    // vérifie si le joueur a des actions restantes pour ce tour, si oui décrémente le nombre d'actions et retourne true, sinon retourne false
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

}
