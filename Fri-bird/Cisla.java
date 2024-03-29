/**
 * Trieda načítava čísla zo súborov png.  
 * @author Tobiáš Mitala
 * @version 1.0
 */
public class Cisla {
    private Obrazok[] cislo;
    private int zobrazeneCislo;
    
    /**
     * Konštruktor načítava do poľa  adresy čísel. 
     */
    public Cisla() {
        this.cislo = new Obrazok[10];
        for (int i = 0; i < this.cislo.length; i++) {
            String adresa = "obr/Cisla/" + i + ".png";
            Obrazok hodnota = new Obrazok(adresa);
            hodnota.posunVodorovne(-100);
            hodnota.posunZvisle(-100);
            this.cislo[i] = hodnota;
        }
        
    }
    
    /**
     * Zobrazí zadané číslo na zadaných súradniciach. 
     */
    public void zobrazneCislo(int x, int y, int cislo) {
        this.cislo[this.zobrazeneCislo].skry();
        this.cislo[cislo].zmenPolohu(x, y);
        this.cislo[cislo].zobraz();
        this.zobrazeneCislo = cislo;
    }
    
    /**
     * Zobrazí
     */
    public void zobraz() {
        this.cislo[this.zobrazeneCislo].zobraz();
    }
    
    /**
     * Skryje
     */
    public void skry() {
        this.cislo[this.zobrazeneCislo].skry();
    }
}
