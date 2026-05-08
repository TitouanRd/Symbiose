import javax.swing.*;

public class Foret extends TypeTerrain {
    private float recouvrementArbre;
    private Case parent;

    public float getrecouvrementArbre() {
        return recouvrementArbre;
    }
    public void setRecouvrementArbre(float recouvrementArbre) {
        this.recouvrementArbre = recouvrementArbre;
    }

    public float getRecouvrementArbre() {
        return recouvrementArbre;
    }

    public Case getParent() {
        return parent;
    }

    public void setParent(Case parent) {
        this.parent = parent;
    }

    public Foret(float recouvrementArbre, Case parent) {
        this.recouvrementArbre = recouvrementArbre;
        this.parent = parent;

    }
    public void raser() {
        Plaine plaine = new Plaine(10,50f,30f,80f,this.getParent());
        this.getParent().setTypeTerrain(plaine);
        this.getParent().setQualite(this.getParent().getQualite()-20);
        this.getParent().getCarte().getPartie().notifyMapChanged();// rafraichie la grille
    }
    public void exploiter() {
        System.err.println("Foret exploiter");
        this.getParent().getCarte().getPartie().notifyMapChanged();// rafraichie la grille
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
    public void fin_tour(){
    }

    @Override
    // Gère le nombre d'actions pour exploiter ou raser la forêt, retourne true si l'action peut être effectuée, false sinon
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

    @Override
    // Affiche les informations de la forêt et les actions possibles (exploiter ou raser)
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

            "- Pollution: " + parent.getPollution() + "\n" +
            "- Qualité: " + parent.getQualite() + "\n" +
            "- Santé environnementale: " + parent.getSante_environnemental() + "\n" +
            "- Meteo: " + parent.getMeteo() + "\n" +
            "- Construction: " + parent.getConstruction() + "\n" +
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
            // Vérifie si l'action peut être effectuée (nombre d'actions restantes), puis exploite la forêt
            if (nb_tour()) {
                exploiter();
            }
            frame.dispose();
        });
        buttonPanel.add(exploiter);

        JButton raser = new JButton("Raser");
        raser.addActionListener(e -> {
            if (nb_tour()) {
                raser();
            }
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
