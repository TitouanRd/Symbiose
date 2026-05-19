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
            Partie partie = new Partie(null, false, 0, 3, 0, 0, 0, taille);
            JFrame grilleFrame = new JFrame("Hex Grid - " + taille);
            grilleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            // Créer un panel principal avec BorderLayout
            JPanel mainPanel = new JPanel(new BorderLayout());


            // Récupérer les valeurs des attributs de l'objet partie
            int ressources=partie.getRessources();
            float production_energie=partie.getProduction_energie();
            int nb_tour=partie.getNb_tour();
            int limite_tour=partie.getLimite_tour();
            int nb_actions=partie.getNb_actions();


            // Panel du haut pour afficher du texte et bouton
            JPanel topPanel = new JPanel(new BorderLayout());

            JTextArea infoArea = new JTextArea(1, 60);
            infoArea.setEditable(false);
            infoArea.setText(
            "- Ressources: " + ressources +
            "    - Production d'énergie: " + production_energie +
            "    - Nombre de tours: " + nb_tour +
            "    - Limite de tours: " + limite_tour +
            "    - Nombre d'actions: " + nb_actions
             );
            topPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton fin_tour = new JButton("Fin de tour");
              fin_tour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ici lance actions de fin de tour
                partie.fin_Tour();
            }
        });



            buttonPanel.add(fin_tour);
            topPanel.add(buttonPanel, BorderLayout.EAST);

            mainPanel.add(topPanel, BorderLayout.NORTH);

            // Panel du milieu pour la grille
            HexGridApp gridApp = new HexGridApp(partie.getCarte());
            mainPanel.add(gridApp, BorderLayout.CENTER);
            
            grilleFrame.add(mainPanel); 
            grilleFrame.pack();
            grilleFrame.setLocationRelativeTo(null);
            grilleFrame.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
