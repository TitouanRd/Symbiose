import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
    }
    public void planterForet(){
        Foret foret = new Foret(50f,this.getParent());
        this.getParent().setTypeTerrain(foret);
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

    public void show() {
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
        JButton creuser = new JButton("Creuser");
        creuser.addActionListener(e -> {
            creuser();
            frame.dispose();
        });
        buttonPanel.add(creuser);

        JButton planterForet = new JButton("Planter une forêt");
        planterForet.addActionListener(e -> {
            planterForet();
            frame.dispose();
        });
        buttonPanel.add(planterForet);

        JButton proteger = new JButton("Proteger");
        proteger.addActionListener(e -> {
            proteger();
            frame.dispose();
        });
        buttonPanel.add(proteger);

        panel.add(buttonPanel);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
