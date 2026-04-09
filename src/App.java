import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class App {
    public static void main(String[] args) throws Exception {
        Partie partie = new Partie(null, false, 0, 0, 0, 0, 0, "petite");
        System.out.println(partie.getCarte());

        // Lancer la grille hexagonale avec la carte
        JFrame frame = new JFrame("Hex Grid");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel panel1 = new JPanel();
        JPanel panel3 = new JPanel();
        JLabel label = new JLabel("Bienvenue sur Symbiose !");
        panel1.add(label);


    

        JTextField textField = new JTextField(20);
        panel3.add(textField, BorderLayout.SOUTH);

        
        panel.add(panel1, BorderLayout.NORTH);
        panel.add(panel3, BorderLayout.CENTER);
        frame.add(panel);






        
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new HexGridApp(partie.getCarte()));
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
