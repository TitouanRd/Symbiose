public class Hydrolienne extends ProdEnergie{
    private float profondeur;
    public Hydrolienne(int niveau) {
        // Appel obligatoire à super() EN TOUT PREMIER avec des valeurs par défaut
        // Signature : niveau, cout, prodEnergie, prodRess, entretEnergie, entretRess, impQualite, impPollution, impVie, rendement
        super(niveau, 0, 0, 0, 0, 0, 0f, 0f, 0f, 0f);

        // Affectation des valeurs spécifiques selon le niveau
        switch (niveau) {
            case 1 -> {
                this.setCout(80);
                this.setProductionEnergie(0);
                this.setProductionRess(0);

                this.setEntretienEnergie(1);
                this.setEntretienRess(6); // Entretien sous-marin coûteux

                this.setImpactQualite(2.0f);
                this.setImpactPollution(0.2f);
                this.setImpactVie(5.0f);  // Danger pour les poissons

                this.setRendement(30f);   // Très bon rendement de base
                this.profondeur = 10f;
            }
            case 2 -> {
                this.setCout(200);
                this.setProductionEnergie(0);
                this.setProductionRess(0);

                this.setEntretienEnergie(3);
                this.setEntretienRess(15);

                this.setImpactQualite(4.0f);
                this.setImpactPollution(0.5f);
                this.setImpactVie(10.0f); // Fort impact sur l'écosystème du lac

                this.setRendement(80f);
                this.profondeur = 20f;
            }
            default -> {
                this.setCout(0);
                this.setProductionEnergie(0);
                this.setProductionRess(0);

                this.setEntretienEnergie(0);
                this.setEntretienRess(0);

                this.setImpactQualite(0f);
                this.setImpactPollution(0f);
                this.setImpactVie(0f);

                this.setRendement(0f);
                this.profondeur = 0f;
            }
        }
    }
    public float getProfondeur() {
        return profondeur;
    }
    public void getP(float profondeur) {
        this.profondeur = profondeur;
    }

    @Override
    public float consommerRessources(Case c) {
        // 1. Coût d'entretien nominal
        float res_csm = this.getEntretienRess();

        // 2. Facteur Qualité : L'usure due à un environnement toxique ou corrosif
        float facteurQualite = 100f / Math.max(1f, c.getQualite());
        res_csm *= facteurQualite;

        // 3. Contraintes mécaniques
        if (c.getTypeTerrain() instanceof Lac) {
            Lac lac = (Lac) c.getTypeTerrain();
            float vitesseCourant = lac.getVitesseCourant();

            // Courant extrême = contraintes énormes sur l'axe de rotation
            if (vitesseCourant > 80f) {
                res_csm *= 1.5f; // +50% de coût d'entretien (pièces endommagées)
            }
            // Calme plat = usure mécanique minimale
            else if (vitesseCourant < 10f) {
                res_csm *= 0.8f; // -20% de coût
            }
        }

        return res_csm;
    }
    @Override
    public float produireEnergie(Case c) {
        float enrg_prod = 0;

        // 1. Vérification stricte du type de terrain (Doit être un Lac/eau)
        if (c.getTypeTerrain() instanceof Lac) {
            Lac lac = (Lac) c.getTypeTerrain();
            float vitesseCourant = lac.getVitesseCourant();

            // 2. Logique mécanique (Plage de fonctionnement de l'hydrolienne)
            // L'eau étant plus dense, on peut imaginer un amorçage plus bas (ex: 10)
            // et un seuil de sécurité (ex: 80)
            if (vitesseCourant >= 10f && vitesseCourant <= 80f) {

                // 3. Facteur Qualité (Une eau boueuse/polluée encrasse la génératrice)
                float facteurQualite = Math.max(0f, c.getQualite()) / 100f;

                // 4. Calcul de la puissance brute
                // On utilise la profondeur (des pales plus grandes) et le rendement
                // Division par 100 pour garder les valeurs de jeu équilibrées
                float productionBrute = (vitesseCourant * this.profondeur * this.getRendement()) / 100f;

                enrg_prod = productionBrute * facteurQualite;

            } else if (vitesseCourant > 80f) {
                // Courant destructeur : mise en drapeau/sécurité, production coupée
                enrg_prod = 0;
            }
        }

        return enrg_prod;
    }
    @Override
    public Number[] BilanTour(Case c) {
        // 1. Application des impacts environnementaux sur la case
        // On augmente la pollution et on diminue la qualité en fonction des stats du bâtiment
        float nouvellePollution = c.getPollution() + this.getImpactPollution();
        float nouvelleQualite = c.getQualite() - this.getImpactQualite();

        // Sécurité : on "clamp" (bloque) les valeurs entre 0 et 100 pour éviter les bugs
        c.setPollution(Math.min(100f, Math.max(0f, nouvellePollution)));
        c.setQualite(Math.min(100f, Math.max(0f, nouvelleQualite)));

        // 2. Bilan économique (Ressources et Énergie)
        Number[] retours = new Number[2];
        retours[0] = - consommerRessources(c); // Dépense (Négatif)
        retours[1] = produireEnergie(c);       // Gain (Positif)

        return retours;
    }

    @Override
    public String toString() {
        return "Hydrolienne de niveau "+this.getNiveau();
    }

}
