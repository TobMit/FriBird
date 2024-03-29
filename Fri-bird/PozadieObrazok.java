/**
 * Trieda zobrazuje pozadia na základe situácie aká v hre nastala, alebo aké si 
 * používateľ vybral. PozadieObrázok je naprogramovaná na ľubovoľný počet pozadí 
 * v rozmeroch 300x600 (šírka, výška). Treba len zmeniť hodnotu POCET_POZADI na požadovaný 
 * počet. <br /> 
 * Názov pozadia vždy musí byť: Pozadie-1-poradove_cislo.png 
 * @version 2.1
 */
public class PozadieObrazok {
    private Obrazok obloha;
    private Obrazok hlina;
    private Obrazok hlinaHraBezi;
    private Obrazok hlinaPauza;
    
    private String[] adresa;
    private int aktualnePozadie = 0;
    
    private static final int POCET_POZADI = 3;
    public static final int HRACIA_PLOCHA_L_DOLNY_X = 360;
    public PozadieObrazok() {
        this.adresa = new String[this.POCET_POZADI];
        
        for (int i = 1; i <= this.POCET_POZADI; i++) {
            String adres = "obr/Pozadie-1-" + i + ".png";
            this.adresa[i - 1] = adres;
        }
        
        this.obloha = new Obrazok(this.adresa[this.aktualnePozadie]);
        this.obloha.posunVodorovne(-100);
        this.obloha.posunZvisle(-100);
        this.obloha.posunZvisle(-180);
        this.obloha.zobraz();
        
        //Pozadie dva
        this.hlina = new Obrazok("obr/Pozadie-2-home.png");
        this.hlina.posunVodorovne(-100);
        this.hlina.posunZvisle(-100);
        this.hlina.posunZvisle(400);
        this.hlina.zobraz();
        
        //Pozadie dva-hra bezi
        this.hlinaHraBezi = new Obrazok("obr/Pozadie-2-hra-bezi.png");
        this.hlinaHraBezi.posunVodorovne(-100);
        this.hlinaHraBezi.posunZvisle(-100);
        this.hlinaHraBezi.posunZvisle(400);
        
        //Pozadie dva-pauza
        this.hlinaPauza = new Obrazok("obr/Pozadie-2-pauza.png");
        this.hlinaPauza.posunVodorovne(-100);
        this.hlinaPauza.posunZvisle(-100);
        this.hlinaPauza.posunZvisle(400);
    }
    
    
    /**
     * (Obrázok) Zobrazí. 
     */
    public void zobrazHlinu() {
        this.hlina.zobraz();
    }
    /**
     * (Obrázok) Skryje. 
     */
    public void skryHlinu() {
        this.hlina.skry();
    }
    
    /**
     * (Obrázok) Zobrazí. 
     */
    public void zobrazHlinuHraBezi() {
        this.hlinaHraBezi.zobraz();
    }
    /**
     * (Obrázok) Skryje. 
     */
    public void skryHlinuHraBezi() {
        this.hlinaHraBezi.skry();
    }
    
    /**
     * (Obrázok) Zobrazí. 
     */
    public void zobrazHlinuPauza() {
        this.hlinaPauza.zobraz();
    }
    /**
     * (Obrázok) Skryje. 
     */
    public void skryHlinuPauza() {
        this.hlinaPauza.skry();
    }
    
    /**Metóda mení pozadie. 
     * Ma aj overenie aby nebolo poradie pozadia väčšie ako je skutočne načítaných pozadí.  
     * @param smer zmení smer podľa hodnoty
     * true - další, false - predchádzajúci
     */
    public void zemnPozadie(boolean smer) {
        if (smer) {
            this.aktualnePozadie++;
        } else {
            this.aktualnePozadie--;
        }
        if (this.aktualnePozadie > this.POCET_POZADI - 1) {
            this.aktualnePozadie = 0;
        } else if (this.aktualnePozadie < 0) {
            this.aktualnePozadie = this.POCET_POZADI - 1;
        }
        
        this.obloha.zmenObrazok(this.adresa[this.aktualnePozadie]);
        this.obloha.zobraz();
    }
}
