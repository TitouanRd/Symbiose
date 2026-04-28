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

    public void show() {
        JFrame frame = new JFrame("Hex Grid");

        JPanel panel = new JPanel();

        JLabel label = new JLabel("Action sur une foret :");
        panel.add(label);



        JButton exploiter = new JButton("Exploiter");
        exploiter.addActionListener(e -> {
            exploiter();
            frame.dispose();
        });
        panel.add(exploiter);

        JButton raser = new JButton("Raser");
        raser.addActionListener(e -> {        
            raser();
            frame.dispose();
        });     
        panel.add(raser);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
