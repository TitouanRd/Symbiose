import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {


        // Lancer choix taille grille
        JFrame frame = new JFrame("Hex Grid");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        

        JLabel label = new JLabel("Bienvenue sur Symbiose !");
        panel1.add(label);

        JLabel label2 = new JLabel("Choisissez une taille de carte :");
        panel2.add(label2);


        JButton petit = new JButton("Petite");
        petit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancerGrille("petite");
                frame.dispose();
            }
        });
        panel3.add(petit);

        JButton moyen = new JButton("Moyenne");
        moyen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancerGrille("moyenne");
                frame.dispose();
            }
        });
        panel3.add(moyen);
    
        JButton grand = new JButton("Grande");
        grand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancerGrille("grande");
                frame.dispose();
            }
        });
        panel3.add(grand);




        JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout());

        panel4.add(panel2, BorderLayout.NORTH);
        panel4.add(panel3, BorderLayout.CENTER);

        panel.add(panel1, BorderLayout.NORTH);
        panel.add(panel4, BorderLayout.CENTER);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void lancerGrille(String taille) {
        try {
            Partie partie = new Partie("facile", taille); // Ex: 100 ressources pour commencer
            Ville.resetNbVille();
            JFrame grilleFrame = new JFrame("Hex Grid - " + taille);
            grilleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel(new BorderLayout());
            JPanel topPanel = new JPanel(new BorderLayout());

            final JTextArea infoArea = new JTextArea(2, 60); // Passé à 2 lignes pour afficher les instructions
            infoArea.setEditable(false);

            final HexGridApp gridApp = new HexGridApp(partie);
            JButton fin_tour = new JButton("Fin de tour");

            // Définition de la routine de mise à jour des informations
            Runnable updateInfo = () -> {
                infoArea.setText(formatInfo(partie));

                // --- LOGIQUE TOUR 0 : BLOCAGE DU BOUTON FIN DE TOUR ---
                if (partie.getNb_tour() == 0) {
                    int nbVilles = Ville.getNbVille(); // Utilise le compteur statique de ta classe Ville
                    if (nbVilles == 0) {
                        infoArea.append("\n OBJECTIF OBLIGATOIRE : Vous devez poser votre première Ville sur une Plaine pour commencer !");
                        fin_tour.setEnabled(false); // Désactive le bouton tant qu'aucune ville n'est construite
                    } else {
                        infoArea.append("\n Objectif atteint ! Vous pouvez maintenant terminer votre tour.");
                        fin_tour.setEnabled(true);
                    }
                } else {
                    fin_tour.setEnabled(true); // Toujours actif pour les tours suivants
                }
            };

            partie.setUpdateListener(updateInfo);
            updateInfo.run();

            topPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

            // Listener pour rafraîchir la grille quand la carte change
            Runnable mapChangeListener = () -> gridApp.refresh();
            partie.setMapChangeListener(mapChangeListener);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            fin_tour.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    partie.fin_Tour();
                    gridApp.refresh();
                    partie.notifyUpdateListener();
                }
            });

            buttonPanel.add(fin_tour);
            topPanel.add(buttonPanel, BorderLayout.EAST);

            mainPanel.add(topPanel, BorderLayout.NORTH);
            mainPanel.add(gridApp, BorderLayout.CENTER);

            grilleFrame.add(mainPanel);
            grilleFrame.pack();
            grilleFrame.setLocationRelativeTo(null);
            grilleFrame.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Format les informations de la partie pour les afficher dans le JTextArea
    private static String formatInfo(Partie partie) {
        return "- Ressources: " + partie.getRessources() +
               "    - Production d'énergie: " + partie.getProduction_energie() +
               "    - Nombre de tours: " + partie.getNb_tour() +
               "    - Limite de tours: " + partie.getLimite_tour() +
               "    - Nombre d'actions: " + partie.getNb_actions();
    }
}

