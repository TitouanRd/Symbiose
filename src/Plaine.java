import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Plaine extends Case {
    private float vitesseVent;
    private float enseileillement;
    private float temperatureSol;

    private float richesseSol;

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

    
    public Plaine(float pollution, float qualite, String sante_environnemental, float vitesseVent,
            float enseileillement, float temperatureSol, float richesseSol) {
        super(pollution, qualite, sante_environnemental);
        this.vitesseVent = vitesseVent;
        this.enseileillement = enseileillement;
        this.temperatureSol = temperatureSol;
        this.richesseSol = richesseSol;
    }

    

    public void creuser(){
        System.err.println("Plaine creuser");
    }
    public void planterForet(){
        System.err.println("Plaine planterForet");
    }
    public void proteger(){
        System.err.println("Plaine proteger");
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

     public void show() {
        JFrame frame = new JFrame("Hex Grid");

        JPanel panel = new JPanel();

        JLabel label = new JLabel("Action sur une foret :");
        panel.add(label);



        JButton creuser = new JButton("Creuser");
        creuser.addActionListener(e -> {
            creuser ();
            frame.dispose();
        });
        panel.add(creuser);

        JButton planterForet = new JButton("PlanterForet");
        planterForet.addActionListener(e -> {        
            planterForet();
            frame.dispose();
        });     
        panel.add(planterForet);

        JButton proteger = new JButton("Proteger");
        proteger.addActionListener(e -> {        
            proteger();
            frame.dispose();// ferme la fenetre quand je clique
        });     
        panel.add(proteger);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
