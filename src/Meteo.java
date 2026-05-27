import java.util.Random;

public class Meteo {
    private String nom;
    private float tauxVent;
    private float tauxCourant;
    private float tauxEnsoleillement;
    private float tauxVie;
    private float tauxdestruction;

    public String getNom() {
        return nom;
    }

    public Meteo(String nom, float tauxVent, float tauxCourant, float tauxEnsoleillement, float tauxVie,
            float tauxdestruction) {
        this.nom = nom;
        this.tauxVent = tauxVent;
        this.tauxCourant = tauxCourant;
        this.tauxEnsoleillement = tauxEnsoleillement;
        this.tauxVie = tauxVie;
        this.tauxdestruction = tauxdestruction;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getTauxVent() {
        return tauxVent;
    }

    public void setTauxVent(float tauxVent) {
        this.tauxVent = tauxVent;
    }

    public float getTauxCourant() {
        return tauxCourant;
    }

    public void setTauxCourant(float tauxCourant) {
        this.tauxCourant = tauxCourant;
    }

    public float getTauxEnsoleillement() {
        return tauxEnsoleillement;
    }

    public void setTauxEnsoleillement(float tauxEnsoleillement) {
        this.tauxEnsoleillement = tauxEnsoleillement;
    }

    public float getTauxVie() {
        return tauxVie;
    }

    public void setTauxVie(float tauxVie) {
        this.tauxVie = tauxVie;
    }

    public float getTauxdestruction() {
        return tauxdestruction;
    }

    public void setTauxdestruction(float tauxdestruction) {
        this.tauxdestruction = tauxdestruction;
    }

// ... (reste de ta classe Meteo) ...

    public void modificationMeteo(Case c) {
        Random rand = new Random();

        // --- 1. DIFFUSION (Analyse des vents et fronts météo voisins) ---
        int influenceTempete = 0;
        int influencePluie = 0;
        int influenceClair = 0;

        // On regarde la météo des cases autour pour "attirer" le même temps
        if (c.getVoisines() != null) {
            for (Case voisine : c.getVoisines()) {
                if (voisine != null && voisine.getMeteo() != null) {
                    String nomVoisine = voisine.getMeteo().getNom();
                    if ("Tempête".equals(nomVoisine)) influenceTempete++;
                    else if ("Averse".equals(nomVoisine)) influencePluie++;
                    else if ("Clair".equals(nomVoisine)) influenceClair++;
                }
            }
        }

        // --- 2. TRANSITION D'ÉTAT ALÉATOIRE PONDÉRÉE ---
        int tirage = rand.nextInt(100);
        String nouvelEtat = this.nom; // Par défaut, on garde le temps actuel (inertie)

        // Les voisines augmentent les chances de basculer vers leur météo (+5% par voisine)
        if (tirage < 5 + (influenceTempete * 5)) {
            nouvelEtat = "Tempête";
        } else if (tirage < 15 + (influencePluie * 5)) {
            nouvelEtat = "Averse";
        } else if (tirage < 40 + (influenceClair * 5)) {
            nouvelEtat = "Clair";
        } else if (tirage < 60) {
            nouvelEtat = "Nuageux";
        }
        // Si tirage > 60, l'état ne change pas (stabilité de la météo)

        this.nom = nouvelEtat;

        // --- 3. APPLICATION DES MODIFICATEURS DE JEU ---
        // On génère des statistiques avec un léger bruit aléatoire pour chaque état
        switch (this.nom) {
            case "Clair" -> {
                this.tauxEnsoleillement = 80f + rand.nextFloat() * 20f; // 80 à 100
                this.tauxVent = 10f + rand.nextFloat() * 20f;           // Brise légère (10-30)
                this.tauxCourant = 10f + rand.nextFloat() * 20f;
                this.tauxVie = +2.0f;                                   // Bonus écologique
                this.tauxdestruction = 0f;
            }
            case "Nuageux" -> {
                this.tauxEnsoleillement = 30f + rand.nextFloat() * 30f; // 30 à 60
                this.tauxVent = 20f + rand.nextFloat() * 30f;           // 20 à 50
                this.tauxCourant = 20f + rand.nextFloat() * 30f;
                this.tauxVie = +1.0f;
                this.tauxdestruction = 0f;
            }
            case "Averse" -> {
                this.tauxEnsoleillement = 10f + rand.nextFloat() * 20f; // 10 à 30
                this.tauxVent = 40f + rand.nextFloat() * 30f;           // 40 à 70
                this.tauxCourant = 50f + rand.nextFloat() * 30f;        // Courants forts
                this.tauxVie = +4.0f;                                   // L'eau aide beaucoup la nature
                this.tauxdestruction = 1.0f;
            }
            case "Tempête" -> {
                this.tauxEnsoleillement = 0f + rand.nextFloat() * 10f;  // 0 à 10 (Nuit noire)
                this.tauxVent = 80f + rand.nextFloat() * 20f;           // 80 à 100 (Très puissant)
                this.tauxCourant = 80f + rand.nextFloat() * 20f;        // Tempête sous-marine
                this.tauxVie = -3.0f;                                   // Abîme l'écosystème
                this.tauxdestruction = 10.0f;                           // Dégâts matériels
            }
        }

        // --- 4. IMPACT PHYSIQUE SUR LA CASE ---
        // Synchronisation avec les données que tes bâtiments utilisent pour produire !
        if (c.getTypeTerrain() instanceof Plaine) {
            Plaine p = (Plaine) c.getTypeTerrain();
            p.setEnseileillement(this.tauxEnsoleillement);
            p.setVitesseVent(this.tauxVent);
        } else if (c.getTypeTerrain() instanceof Lac) {
            Lac l = (Lac) c.getTypeTerrain();
            l.setVitesseVent(this.tauxVent);
            l.setVitesseCourant(this.tauxCourant);
        }

        // Application directe de l'impact météo sur la santé de la case
        c.setQualite(Math.min(100f, Math.max(0f, c.getQualite() + this.tauxVie)));
    }
}
