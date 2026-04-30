import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Lac extends Case {
    private float vitesseCourant;
    private float vitesseVent;
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
    
    public Lac(float pollution, float qualite, String sante_environnemental, float vitesseCourant, float vitesseVent) {
        super(pollution, qualite, sante_environnemental);
        this.vitesseCourant = vitesseCourant;
        this.vitesseVent = vitesseVent;
    }
    public void remplir() {
        System.err.println("Lac remplire");
    }
    public void exploiter() {
        System.err.println("Lac exploiter");
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

    public void show() {
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
            "- Pollution: " + getPollution() + "\n" +
            "- Qualité: " + getQualite() + "\n" +
            "- Santé environnementale: " + getSante_environnemental() + "\n" +
            "- Meteo: " + getMeteo() + "\n" +
            "- Construction: " + getConstruction() + "\n" +
            "- Vitesse du courant: " + vitesseCourant + "\n" +
            "- Vitesse du vent: " + vitesseVent
        );
        panel.add(new JScrollPane(infoArea));

        // Actions
        JLabel actionLabel = new JLabel("Actions sur un lac :");
        actionLabel.setAlignmentX(panel.CENTER_ALIGNMENT);
        panel.add(actionLabel);

        JPanel buttonPanel = new JPanel();
        JButton exploiter = new JButton("Exploiter");
        exploiter.addActionListener(e -> {
            exploiter();
            frame.dispose();
        });
        buttonPanel.add(exploiter);

        JButton remplire = new JButton("Remplir");
        remplire.addActionListener(e -> {
            remplir();
            frame.dispose();
        });
        buttonPanel.add(remplire);

        panel.add(buttonPanel);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
