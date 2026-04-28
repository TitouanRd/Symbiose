import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class App {
    public static void main(String[] args) throws Exception {
        // Lancer la grille hexagonale avec la carte
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


//ici je veut que je clique sur une des tailles de carte et que ça lance la grille hexagonale avec la carte correspondante, mais je n'arrive pas à faire le lien entre les boutons et la création de la carte, est ce que tu peux m'aider ?

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void lancerGrille(String taille) {
        try {
            Partie partie = new Partie(null, false, 0, 0, 0, 0, 0, taille);
            JFrame grilleFrame = new JFrame("Hex Grid - " + taille);
            grilleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            grilleFrame.add(new HexGridApp(partie.getCarte()));
            grilleFrame.pack();
            grilleFrame.setLocationRelativeTo(null);
            grilleFrame.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
