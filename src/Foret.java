import javax.swing.*;

public class Foret extends Case {
    private float recouvrementArbre;
    public float getrecouvrementArbre() {
        return recouvrementArbre;
    }
    public void setRecouvrementArbre(float recouvrementArbre) {
        this.recouvrementArbre = recouvrementArbre;
    }
    
    public Foret(float pollution, float qualite, String sante_environnemental, float recouvrementArbre) {
        super(pollution, qualite, sante_environnemental);
        this.recouvrementArbre = recouvrementArbre;
    }
    public void raser() {
        System.err.println("Foret rasze");
    }
    public void exploiter() {
        System.err.println("Foret exploiter");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" | Foret  | ");
        //sb.append("recouvrementArbre=").append(recouvrementArbre);
        //sb.append('}');
        return sb.toString();
    }

    @Override
    public void show() {
        JFrame frame = new JFrame("Foret");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Afficher les informations de la forêt
        JLabel infoLabel = new JLabel("Informations de la forêt :");
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
            "- Recouvrement d'arbres: " + recouvrementArbre
        );
        panel.add(new JScrollPane(infoArea));

        // Actions
        JLabel actionLabel = new JLabel("Actions sur une forêt :");
        actionLabel.setAlignmentX(panel.CENTER_ALIGNMENT);
        panel.add(actionLabel);

        JPanel buttonPanel = new JPanel();
        JButton exploiter = new JButton("Exploiter");
        exploiter.addActionListener(e -> {
            exploiter();
            frame.dispose();
        });
        buttonPanel.add(exploiter);

        JButton raser = new JButton("Raser");
        raser.addActionListener(e -> {
            raser();
            frame.dispose();
        });
        buttonPanel.add(raser);

        panel.add(buttonPanel);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
