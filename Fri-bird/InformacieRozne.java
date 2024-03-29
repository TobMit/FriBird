/**
 * Táto trieda slúži na zobrazovanie rôznych obrázkov – informácií ktoré sa zobrazujú počas hry. 
 * @author Tobiáš Mitala
 * @version 1.1
 */
public class InformacieRozne {
    private Obrazok logo;
    private Obrazok pozadieSkore;
    private Obrazok pauza;
    private Obrazok narazenie;
    private Obrazok medzernik;
    private Obrazok restart;
    public InformacieRozne() {
        this.logo = new Obrazok("obr/logo.png");
        this.logo.zmenPolohu(150, 50);
        
        this.pozadieSkore = new Obrazok("obr/PozadieSkore.png");
        this.pozadieSkore.posunVodorovne(-10);
        
        this.pauza = new Obrazok("obr/Pauza.png");
        this.pauza.zmenPolohu(150, 50);
        
        this.narazenie = new Obrazok("obr/narazenie.png");
        this.narazenie.zmenPolohu(150, 300);
        
        this.medzernik = new Obrazok("obr/medzernik.png");
        
        this.restart = new Obrazok("obr/restart.png");
        this.restart.zmenPolohu(150, 350);
    }
    
    /**
     * (Obrázok) Zobrazí. 
     */
    public void obrazkyPrehraZobraz() {
        this.pozadieSkore.zobraz();
    }
    /**
     * (Obrázok) Skryje. 
     */
    public void obrazkyPrehraSkry() {
        this.pozadieSkore.skry();
    }
    
    /**
     * (Obrázok) Zobrazí. 
     */
    public void obrazkyPauzaZobraz() {
        this.pauza.zobraz();
    }
    /**
     * (Obrázok) Skryje. 
     */
    public void obrazkyPauzaSkry() {
        this.pauza.skry();
    }
    
    /**
     * (Obrázok) Zobrazí. 
     */
    public void obrazkyNarazenieZobraz() {
        this.narazenie.zobraz();
    }
    /**
     * (Obrázok) Skryje. 
     */
    public void obrazkyNarazenieSkry() {
        this.narazenie.skry();
    }
    
    /**
     * (Obrázok) Skryje. 
     */
    public void medzernikSkry() {
        this.medzernik.skry();
    }
    /**
     * (Obrázok) Zobrazí. 
     * @param x Súradnica X stredu obrázku
     * @param y Súradnica Y stredu obrázku
     */
    public void medzernikZobraz(int x, int y) {
        this.medzernik.zmenPolohu(x, y);
        this.medzernik.zobraz();
    }
    
    /**
     * (Obrázok) Zobrazí. 
     */
    public void obrazkyLogoZobraz() {
        this.logo.zobraz();
    }
    /**
     * (Obrázok) Skryje. 
     */
    public void obrazkyLogoSkry() {
        this.logo.skry();
    }
    //------------------------Tlačidlo------------------------
    /**
     * (Obrázok) Zobrazí. <br />
     * Tlačidlo restart ma velkosť <br />
     *      -šírka 200<br />
     *      -výška 60
     */
    public void tlacidloRestartZobraz() {
        this.restart.zobraz();
    }
    /**
     * (Obrázok) Skryje. 
     */
    public void tlacidloRestartSkry() {
        this.restart.skry();
    }
    
}
