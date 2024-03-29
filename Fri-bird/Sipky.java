/**
 * Trieda vytvára šípky. 
 * @author Tobiáš Mitala
 * @version 1.0
 */
public class Sipky {
    private Obrazok sipka;
    /**
     * Konštruktor vytvára šípku na zadaných súradniciach. 
     * @param smer mení smer šípky podľa   true - vpravo , false - vľavo 
     * 
     * Veľkosť obrázka šípky je: šírka 30, výška 50 
     */
    public Sipky(int x, int y, boolean smer) {
        if (smer) {
            this.sipka = new Obrazok("obr/sipkaP.png");
        } else {
            this.sipka = new Obrazok("obr/sipkaL.png");
        }
        
        this.sipka.posunVodorovne(-100 + x);
        this.sipka.posunZvisle(-100 + y);
    }
    
    /**
     * (Obrázok) Zobrazí. 
     */
    public void zobraz() {
        this.sipka.zobraz();
    }
    
    /**
     * (Obrázok) Skryje.  
     */
    public void skry() {
        this.sipka.skry();
    }
}
