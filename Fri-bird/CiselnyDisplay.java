/**
 * Trieda umožňuje zobrazovať na požadovanej súradnici zadané číslo. 
 * @author Tobiáš Mitala
 * @version 1.0
 */
public class CiselnyDisplay {
    private Cisla jednotky;
    private Cisla desiadky;
    private Cisla stovky;
    
    //určuje koľko je zobrazených čísel
    private int zobrazenychCisle;
    public CiselnyDisplay() {
        this.jednotky = new Cisla();
        this.desiadky = new Cisla();
        this.stovky = new Cisla();
    }
    
    /**
     * Zobrazí číselný displej s požadovaným číslom na zadaných súradniciach.     
     */
    public void zobrazCislo(int score, int x, int y) {
        this.jednotky.skry();
        this.desiadky.skry();
        this.stovky.skry();
        if (score <= 9) {
            this.jednotky.zobrazneCislo(x, y, score);
            this.zobrazenychCisle = 1;
        } else if (score > 9 && score <= 99) {
            this.desiadky.zobrazneCislo(x - 15, y, score / 10);
            this.jednotky.zobrazneCislo(x + 15, y, score % 10);
            this.zobrazenychCisle = 2;
        } else {
            this.stovky.zobrazneCislo(x - 30, y, score / 100);
            this.desiadky.zobrazneCislo(x, y, (score / 10) % 10);
            this.jednotky.zobrazneCislo(x + 30, y, score % 10);
            this.zobrazenychCisle = 3;        
        }
    }
    
    /**
     * Skryje číselný displej.  
     */
    public void skry() {
        this.jednotky.skry();
        this.desiadky.skry();
        this.stovky.skry();
        this.zobrazenychCisle = 0;
    }
    
    /**
     * Zobrazí číselný displej s aktuálnym číslom.  
     */
    public void zobraz() {
        switch (this.zobrazenychCisle) {
            case 1:
                this.jednotky.zobraz();
                break;
            case 2:
                this.jednotky.zobraz();
                this.desiadky.zobraz();
                break;
            case 3:
                this.jednotky.zobraz();
                this.desiadky.zobraz();
                this.stovky.zobraz();
                break;
            default:
                break;
        }
    }
}
